package cafe.navy.obsidian.paper.entity.player;

import cafe.navy.obsidian.core.client.GameClient;
import cafe.navy.obsidian.core.client.GamePacket;
import cafe.navy.obsidian.core.util.Position;
import cafe.navy.obsidian.paper.api.packet.ProtocolLibGamePacket;
import cafe.navy.obsidian.paper.api.packet.WrapperPlayServerEntityDestroy;
import cafe.navy.obsidian.paper.api.packet.WrapperPlayServerEntityMetadata;
import cafe.navy.obsidian.paper.api.packet.WrapperPlayServerNamedEntitySpawn;
import cafe.navy.obsidian.paper.api.packet.WrapperPlayServerPlayerInfo;
import cafe.navy.obsidian.paper.api.packet.WrapperPlayServerSpawnEntity;
import cafe.navy.obsidian.paper.entity.EntityRenderer;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.lucko.helper.Schedulers;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * {@code PlayerRenderer} renders a player entity.
 */
public class PlayerRenderer implements EntityRenderer {

    public static @NonNull PlayerRenderer of(final @NonNull PlayerOptions options) {
        return new PlayerRenderer(
                options.name(),
                options.uuid(),
                options.position(),
                options.hasSkin(),
                options.skinTexture(),
                options.skinSignature(),
                options.showName()
        );
    }

    public static @NonNull PlayerRenderer of(final @NonNull String name,
                                             final @NonNull UUID uuid,
                                             final @NonNull Position position) {
        return new PlayerRenderer(name, uuid, position, false, null, null, true);
    }

    public static @NonNull PlayerRenderer of(final @NonNull String name,
                                             final @NonNull UUID uuid,
                                             final @NonNull Position position,
                                             final boolean showName) {
        return new PlayerRenderer(name, uuid, position, false, null, null, showName);
    }

    public static @NonNull PlayerRenderer of(final @NonNull String name,
                                             final @NonNull UUID uuid,
                                             final @NonNull Position position,
                                             final @NonNull String skinTexture,
                                             final @NonNull String skinSignature,
                                             final boolean showName) {
        return new PlayerRenderer(name, uuid, position, false, skinTexture, skinSignature, showName);
    }


    private final @NonNull String name;
    private final @NonNull UUID uuid;
    private final @NonNull Position position;
    private final @NonNull CustomPlayerEntity entity;
    private final @NonNull PlayerInfoData infoData;
    private final boolean showName;
    private final int passengerId;

    private PlayerRenderer(final @NonNull String name,
                           final @NonNull UUID uuid,
                           final @NonNull Position position,
                           final boolean hasSkin,
                           final @Nullable String skinTexture,
                           final @Nullable String skinSignature,
                           final boolean showName) {
        this.name = name;
        this.uuid = uuid;
        this.position = position;
        this.showName = showName;

        final GameProfile profile = new GameProfile(uuid, name);
        if (hasSkin) {
            profile.getProperties().put("textures", new Property("textures", skinTexture, skinSignature));
        }

        this.entity = new CustomPlayerEntity(this.position, profile);

        if (this.showName) {
            this.passengerId = 0;
        } else {
            // only increment entityId if necessary
            this.passengerId = net.minecraft.world.entity.Entity.nextEntityId();
        }

        this.infoData = new PlayerInfoData(WrappedGameProfile.fromHandle(this.entity.getGameProfile()),
                0, EnumWrappers.NativeGameMode.CREATIVE, WrappedChatComponent.fromText(name));
    }

    @Override
    public void show(final @NonNull GameClient client) {
        // scoreboard/meta info
        client.sendPacket(this.getPlayerAddInfoPacket());
        client.sendPacket(this.getPlayerMetadataPacket(this.entity.getBukkitEntity()));

        // spawn player
        client.sendPacket(this.getPlayerSpawnPacket(
                this.entity.getId(), this.entity.getUUID(),
                this.position.x(), this.position.y(), this.position.z(),
                this.position.yaw(), this.position.pitch()
        ));

        // set rotation
        client.sendPacket(this.getEntityLookPacket(this.entity.getId(), this.position.yaw(), this.position.pitch()));
        client.sendPacket(this.getEntityRotatePacket(this.entity.getId(), this.position.yaw()));

        // if name should be hidden, spawn passenger to hide player nameplate
        if (!this.showName) {
            client.sendPacket(this.getPassengerSpawnPacket(
                    this.passengerId,
                    this.position.x(), this.position.y() + this.entity.getBbHeight(), this.position.z())
            );
            client.sendPacket(this.getPassengerMetadataPacket(this.passengerId));
            client.sendPacket(this.getPassengerRidePacket(this.entity.getId(), this.passengerId));
        }

        // send remove packet later to allow entity skin to load on client
        Schedulers.sync().runLater(() -> {
            client.sendPacket(this.getPlayerRemoveInfoPacket());
        }, 1);
    }

    @Override
    public void hide(final @NonNull GameClient client) {
        final WrapperPlayServerEntityDestroy packet = new WrapperPlayServerEntityDestroy();
        if (this.showName) {
            packet.setEntityIds(new int[]{this.entity.getId()});
        } else {
            packet.setEntityIds(new int[]{this.entity.getId(), this.passengerId});
        }
        client.sendPacket(new ProtocolLibGamePacket(packet.getHandle()));
    }

    @Override
    public void updateLocation(final @NonNull GameClient client,
                               final @NonNull Position position) {

    }

    @Override
    public void updateRotation(final @NonNull GameClient client,
                               final float yaw, final float pitch) {
        client.sendPacket(this.getEntityLookPacket(this.entity.getId(), yaw, pitch));
        client.sendPacket(this.getEntityRotatePacket(this.entity.getId(), yaw));
    }

    @Override
    public double visualHeight() {
        return this.entity.getBbHeight();
    }

    private @NonNull GamePacket getPlayerAddInfoPacket() {
        final WrapperPlayServerPlayerInfo infoPacket = new WrapperPlayServerPlayerInfo();
        infoPacket.setAction(EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        infoPacket.setData(List.of(this.infoData));
        return new ProtocolLibGamePacket(infoPacket.getHandle());
    }

    private @NonNull GamePacket getPlayerRemoveInfoPacket() {
        final WrapperPlayServerPlayerInfo info = new WrapperPlayServerPlayerInfo();
        info.setAction(EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
        info.setData(List.of(this.infoData));
        return new ProtocolLibGamePacket(info.getHandle());
    }

    private @NonNull GamePacket getPlayerMetadataPacket(final @NonNull Entity entity) {
        final WrapperPlayServerEntityMetadata metadataPacket = new WrapperPlayServerEntityMetadata();
        metadataPacket.setEntityID(entity.getEntityId());
        metadataPacket.setMetadata(WrappedDataWatcher.getEntityWatcher(entity).getWatchableObjects());
        return new ProtocolLibGamePacket(metadataPacket.getHandle());
    }

    private @NonNull GamePacket getPassengerSpawnPacket(final int passengerId,
                                                        final double x,
                                                        final double y,
                                                        final double z) {
        final WrapperPlayServerSpawnEntity spawn = new WrapperPlayServerSpawnEntity();
        spawn.setUniqueId(UUID.randomUUID());
        spawn.setEntityID(passengerId);
        spawn.setX(x);
        spawn.setY(y);
        spawn.setZ(z);
        spawn.setType(EntityType.ARMOR_STAND);
        return new ProtocolLibGamePacket(spawn.getHandle());
    }

    private @NonNull GamePacket getPassengerMetadataPacket(final int passengerId) {
        final WrappedDataWatcher wrappedDataWatcher = new WrappedDataWatcher();
        wrappedDataWatcher.setObject(0, WrappedDataWatcher.Registry.get(Byte.class), (byte) 0x20); // invisible
        wrappedDataWatcher.setObject(15, WrappedDataWatcher.Registry.get(Byte.class), (byte) 0x10); // marker


        final PacketContainer container = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        container.getIntegers().write(0, passengerId);
        container.getWatchableCollectionModifier().write(0, wrappedDataWatcher.getWatchableObjects());
        return new ProtocolLibGamePacket(container);
    }

    private @NonNull GamePacket getPassengerRidePacket(final int entityId,
                                                       final int passengerId) {
        final PacketContainer container = new PacketContainer(PacketType.Play.Server.MOUNT);
        container.getIntegers().write(0, entityId);
        container.getIntegerArrays().write(0, new int[]{passengerId});
        return new ProtocolLibGamePacket(container);
    }

    private @NonNull GamePacket getPlayerSpawnPacket(final int entityId,
                                                     final @NonNull UUID uuid,
                                                     final double x,
                                                     final double y,
                                                     final double z,
                                                     final float yaw,
                                                     final float pitch) {
        final WrapperPlayServerNamedEntitySpawn spawn = new WrapperPlayServerNamedEntitySpawn();
        spawn.setEntityID(entityId);
        spawn.setPlayerUUID(uuid);
        spawn.setX(x);
        spawn.setY(y);
        spawn.setZ(z);
        spawn.setYaw(yaw);
        spawn.setPitch(pitch);
        return new ProtocolLibGamePacket(spawn.getHandle());
    }

    private @NonNull GamePacket getEntityLookPacket(final int entityId,
                                                    final float yaw,
                                                    final float pitch) {
        final PacketContainer container = new PacketContainer(PacketType.Play.Server.ENTITY_LOOK);
        container.getIntegers().write(0, entityId);
        container.getBytes().write(0, (byte) (yaw * 256.0F / 360.0F));
        container.getBytes().write(1, (byte) pitch);
        container.getBooleans().write(0, true);
        return new ProtocolLibGamePacket(container);
    }

    private @NonNull GamePacket getEntityRotatePacket(final int entityId,
                                                      final float yaw) {
        final PacketContainer container = new PacketContainer(PacketType.Play.Server.ENTITY_HEAD_ROTATION);
        container.getIntegers().write(0, entityId);
        container.getBytes().write(0, (byte) (yaw * 256.0F / 360.0F));
        return new ProtocolLibGamePacket(container);
    }


}
