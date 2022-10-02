package cafe.navy.obsidian.paper.entities.entity.type.player;

import cafe.navy.obsidian.core.client.GameClient;
import cafe.navy.obsidian.core.util.Position;
import cafe.navy.obsidian.paper.api.packet.ProtocolLibGamePacket;
import cafe.navy.obsidian.paper.api.packet.WrapperPlayServerEntityDestroy;
import cafe.navy.obsidian.paper.api.packet.WrapperPlayServerEntityMetadata;
import cafe.navy.obsidian.paper.api.packet.WrapperPlayServerNamedEntitySpawn;
import cafe.navy.obsidian.paper.api.packet.WrapperPlayServerPlayerInfo;
import cafe.navy.obsidian.paper.api.packet.WrapperPlayServerSpawnEntity;
import cafe.navy.obsidian.paper.entities.entity.CustomEntity;
import cafe.navy.obsidian.paper.entities.util.GameProfileBuilder;
import cafe.navy.obsidian.paper.entities.util.MinecraftHelpers;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.mojang.authlib.GameProfile;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ArmorStand;
import org.bukkit.entity.EntityType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.UUID;

public class PlayerEntity implements CustomEntity {

    private final @NonNull UUID uuid;
    private final @NonNull GameProfile profile;
    private final @NonNull Entity mainEntity;
    private final @NonNull ArmorStand passengerEntity;
    private @NonNull Position position;
    private @NonNull PlayerInfoData playerInfo;

    public PlayerEntity(final @NonNull Position position) {
        this.position = position;
        this.uuid = UUID.randomUUID();
        this.profile = GameProfileBuilder.getProfile(this.uuid, "name", "http://textures.minecraft.net/texture/330f0c127a4d5bc56df9b8121a18864be23e6f4e5ea7f9a41bef37fa8adcf90d");
        this.mainEntity = new CustomPlayer(MinecraftHelpers.firstWorld(), this.position, this.profile);
        this.passengerEntity = new ArmorStand(MinecraftHelpers.firstLevel(), this.position.x(), this.position.y(), this.position.z());
        this.passengerEntity.setMarker(true);
    }

    @Override
    public void show(final @NonNull GameClient viewer) {
        viewer.sendPacket(new ProtocolLibGamePacket(this.getPlayerInfoPacket()));
        viewer.sendPacket(new ProtocolLibGamePacket(this.getPlayerMetadataPacket()));
        viewer.sendPacket(new ProtocolLibGamePacket(this.getPlayerSpawnPacket()));
        viewer.sendPacket(new ProtocolLibGamePacket(this.getPassengerSpawnPacket()));
        viewer.sendPacket(new ProtocolLibGamePacket(this.getPassengerMetadataPacket()));
        viewer.sendPacket(new ProtocolLibGamePacket(this.getPassengerRidePacket()));


        final WrapperPlayServerPlayerInfo info = new WrapperPlayServerPlayerInfo();
        info.setAction(EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
        info.setData(List.of(this.playerInfo));
        viewer.sendPacket(new ProtocolLibGamePacket(info.getHandle()));
    }

    @Override
    public void hide(final @NonNull GameClient viewer) {
        final WrapperPlayServerEntityDestroy packet = new WrapperPlayServerEntityDestroy();
        packet.setEntityIds(new int[]{this.mainEntity.getId(), this.passengerEntity.getId()});
        viewer.sendPacket(new ProtocolLibGamePacket(packet.getHandle()));
    }

    @Override
    public boolean matchesId(final int entityId) {
        return this.mainEntity.getId() == entityId;
    }

    @Override
    public double height() {
        return this.mainEntity.getBbHeight();
    }

    @Override
    public void lookAt(final @NonNull GameClient viewer,
                       final @NonNull Position position) {
        viewer.sendPacket(new ProtocolLibGamePacket(this.getLookAtPacket(position)));
    }

    @Override
    public void rotate(final @NonNull GameClient
                               viewer,
                       final float yaw, float pitch) {
        viewer.sendPacket(new ProtocolLibGamePacket(this.getEntityLookPacket(yaw, pitch)));
        viewer.sendPacket(new ProtocolLibGamePacket(this.getEntityRotatePacket(yaw)));
    }

    private @NonNull PacketContainer getPlayerInfoPacket() {
        final WrapperPlayServerPlayerInfo infoPacket = new WrapperPlayServerPlayerInfo();
        infoPacket.setAction(EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        this.playerInfo = new PlayerInfoData(
                WrappedGameProfile.fromHandle(this.profile),
                0, EnumWrappers.NativeGameMode.CREATIVE, WrappedChatComponent.fromHandle(this.mainEntity.getDisplayName())
        );
        infoPacket.setData(List.of(this.playerInfo));
        return infoPacket.getHandle();
    }

    private @NonNull PacketContainer getPlayerMetadataPacket() {
        final WrapperPlayServerEntityMetadata metadataPacket = new WrapperPlayServerEntityMetadata();
        metadataPacket.setEntityID(this.mainEntity.getId());
        metadataPacket.setMetadata(WrappedDataWatcher.getEntityWatcher(this.mainEntity.getBukkitEntity()).getWatchableObjects());
        return metadataPacket.getHandle();
    }

    private @NonNull PacketContainer getPassengerSpawnPacket() {
        final WrapperPlayServerSpawnEntity spawn = new WrapperPlayServerSpawnEntity();
        spawn.setUniqueId(UUID.randomUUID());
        spawn.setEntityID(this.passengerEntity.getId());
        spawn.setX(this.position.x());
        spawn.setY(this.position.y());
        spawn.setZ(this.position.z());
        spawn.setType(EntityType.ARMOR_STAND);
        return spawn.getHandle();
    }

    private @NonNull PacketContainer getPassengerMetadataPacket() {
        final WrappedDataWatcher wrappedDataWatcher = new WrappedDataWatcher();
        wrappedDataWatcher.setObject(0, WrappedDataWatcher.Registry.get(Byte.class), (byte) 0x20); // invisible
        wrappedDataWatcher.setObject(15, WrappedDataWatcher.Registry.get(Byte.class), (byte) 0x10); // marker


        final PacketContainer container = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        container.getIntegers().write(0, this.passengerEntity.getId()); // write entity ID
        container.getWatchableCollectionModifier().write(0, wrappedDataWatcher.getWatchableObjects());
        return container;
    }

    private @NonNull PacketContainer getPassengerRidePacket() {
        final PacketContainer container = new PacketContainer(PacketType.Play.Server.MOUNT);
        container.getIntegers().write(0, this.mainEntity.getId());
        container.getIntegerArrays().write(0, new int[]{this.passengerEntity.getId()});
        return container;
    }

    private @NonNull PacketContainer getPlayerSpawnPacket() {
        final WrapperPlayServerNamedEntitySpawn spawn = new WrapperPlayServerNamedEntitySpawn();
        spawn.setEntityID(this.mainEntity.getId());
        spawn.setPlayerUUID(this.uuid);
        spawn.setX(this.position.x());
        spawn.setY(this.position.y());
        spawn.setZ(this.position.z());
        spawn.setYaw(this.position.yaw());
        spawn.setPitch(this.position.pitch());
        return spawn.getHandle();
    }

    private @NonNull PacketContainer getLookAtPacket(final @NonNull Position position) {
        final PacketContainer lookAt = new PacketContainer(PacketType.Play.Server.LOOK_AT);
        lookAt.getIntegers().write(0, 1);
        lookAt.getDoubles().write(0, position.x());
        lookAt.getDoubles().write(1, position.y());
        lookAt.getDoubles().write(2, position.z());
        return lookAt;
    }

    private @NonNull PacketContainer getEntityLookPacket(final float yaw,
                                                         final float pitch) {
        final PacketContainer container = new PacketContainer(PacketType.Play.Server.ENTITY_LOOK);
        container.getIntegers().write(0, this.mainEntity.getId());
        container.getBytes().write(0, (byte) (yaw * 256.0F / 360.0F));
        container.getBytes().write(1, (byte) pitch);
        container.getBooleans().write(0, true);
        return container;
    }

    private @NonNull PacketContainer getEntityRotatePacket(final float yaw) {
        final PacketContainer container = new PacketContainer(PacketType.Play.Server.ENTITY_HEAD_ROTATION);
        container.getIntegers().write(0, this.mainEntity.getId());
        container.getBytes().write(0, (byte) (yaw * 256.0F / 360.0F));
        return container;
    }
}
