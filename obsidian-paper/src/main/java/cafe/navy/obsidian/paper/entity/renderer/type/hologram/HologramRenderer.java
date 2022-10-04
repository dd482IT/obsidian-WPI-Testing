package cafe.navy.obsidian.paper.entity.renderer.type.hologram;

import cafe.navy.obsidian.core.client.GameClient;
import cafe.navy.obsidian.core.client.GamePacket;
import cafe.navy.obsidian.core.util.Position;
import cafe.navy.obsidian.paper.api.packet.ProtocolLibGamePacket;
import cafe.navy.obsidian.paper.api.packet.WrapperPlayServerEntityDestroy;
import cafe.navy.obsidian.paper.api.packet.WrapperPlayServerEntityTeleport;
import cafe.navy.obsidian.paper.api.packet.WrapperPlayServerSpawnEntity;
import cafe.navy.obsidian.paper.entity.renderer.EntityRenderer;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import io.papermc.paper.adventure.AdventureComponent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.minecraft.world.entity.Entity;
import org.bukkit.entity.EntityType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class HologramRenderer implements EntityRenderer {

    private final @NonNull List<String> names;
    private final @NonNull Position position;
    private final double lineSpacing;

    private final @NonNull List<Integer> entityIds;

    public HologramRenderer(final @NonNull HologramOptions options) {
        this.names = options.names();
        this.position = options.position();
        this.lineSpacing = options.lineSpacing();
        this.entityIds = new ArrayList<>();
        for (int i = 0; i < this.names.size(); i++) {
            this.entityIds.add(Entity.nextEntityId());
        }
    }

    @Override
    public void show(final @NonNull GameClient client) {
        for (int i = 0; i < this.names.size(); i++) {
            final double delta = i * this.lineSpacing;
            final Position linePos = this.position.plusY(delta);

            final int entityId = this.entityIds.get(i);
            final Component line = MiniMessage.miniMessage().deserialize(this.names.get(i));

            if (line.equals(Component.empty())) {
                continue;
            }

            client.sendPacket(this.constructSpawnPacket(entityId, linePos));
            client.sendPacket(this.constructMetadataPacket(entityId, line));
            client.sendPacket(this.constructTeleportPacket(entityId, linePos));
        }
    }

    @Override
    public void hide(final @NonNull GameClient client) {
        for (int i = 0; i < this.names.size(); i++) {
            final int id = this.entityIds.get(i);
            client.sendPacket(this.constructDestroyPacket(id));
        }
    }

    @Override
    public void updateLocation(final @NonNull GameClient client,
                               final @NonNull Position position) {

    }

    @Override
    public void updateRotation(final @NonNull GameClient client, final float yaw, final float pitch) {
        // it's a hologram, we don't need to do anything here
    }

    @Override
    public double visualHeight() {
        return 0;
    }

    private @NonNull GamePacket constructDestroyPacket(final int entityId) {
        final WrapperPlayServerEntityDestroy packet = new WrapperPlayServerEntityDestroy();
        packet.setEntityIds(new int[]{entityId});
        return new ProtocolLibGamePacket(packet.getHandle());
    }

    private @NonNull GamePacket constructMetadataPacket(final int entityId,
                                                        final @NonNull Component line) {
        final PacketContainer container = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        container.getIntegers().write(0, entityId); // write entity ID
        container.getWatchableCollectionModifier().write(0, this.getData(line).getWatchableObjects());
        return new ProtocolLibGamePacket(container);
    }

    private @NonNull GamePacket constructSpawnPacket(final int entityId,
                                                     final @NonNull Position position) {
        final WrapperPlayServerSpawnEntity packet = new WrapperPlayServerSpawnEntity();
        packet.setEntityID(entityId);
        packet.setUniqueId(UUID.randomUUID());
        packet.setType(EntityType.ARMOR_STAND);
        packet.setX(position.x());
        packet.setY(position.y());
        packet.setZ(position.z());
        return new ProtocolLibGamePacket(packet.getHandle());
    }

    private @NonNull GamePacket constructTeleportPacket(final int entityId,
                                                        final @NonNull Position position) {
        final WrapperPlayServerEntityTeleport packet = new WrapperPlayServerEntityTeleport();
        packet.setEntityID(entityId);
        packet.setX(position.x());
        packet.setY(position.y());
        packet.setZ(position.z());
        packet.setPitch(position.pitch());
        packet.setYaw(position.yaw());
        packet.setOnGround(false);
        return new ProtocolLibGamePacket(packet.getHandle());
    }

    private @NonNull WrappedDataWatcher getData(final @NonNull Component name) {
        final net.minecraft.network.chat.Component nmsName = new AdventureComponent(name);
        final WrappedDataWatcher wrappedDataWatcher = new WrappedDataWatcher();
        wrappedDataWatcher.setObject(0, WrappedDataWatcher.Registry.get(Byte.class), (byte) 0x20); // invisible
        wrappedDataWatcher.setObject(2, WrappedDataWatcher.Registry.getChatComponentSerializer(true), Optional.of(nmsName)); // name
        wrappedDataWatcher.setObject(3, WrappedDataWatcher.Registry.get(Boolean.class), Boolean.TRUE); // name visible
        wrappedDataWatcher.setObject(15, WrappedDataWatcher.Registry.get(Byte.class), (byte) 0x10); // marker
        return wrappedDataWatcher;
    }

}
