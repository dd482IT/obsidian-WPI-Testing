package cafe.navy.obsidian.paper.entities.entity.type.hologram;

import cafe.navy.obsidian.core.client.GameClient;
import cafe.navy.obsidian.core.util.Position;
import cafe.navy.obsidian.paper.api.packet.ProtocolLibGamePacket;
import cafe.navy.obsidian.paper.api.packet.WrapperPlayServerEntityDestroy;
import cafe.navy.obsidian.paper.api.packet.WrapperPlayServerEntityTeleport;
import cafe.navy.obsidian.paper.api.packet.WrapperPlayServerSpawnEntity;
import cafe.navy.obsidian.paper.entities.entity.CustomEntity;
import cafe.navy.obsidian.paper.entities.util.MinecraftHelpers;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import io.papermc.paper.adventure.AdventureComponent;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.EntityType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class HologramEntity implements CustomEntity {

    private final @NonNull List<Component> lines;
    private final double lineSpacing;
    private final int[] entityIds;
    private final @NonNull Position position;

    public HologramEntity(final @NonNull List<Component> lines,
                          final double lineSpacing,
                          final @NonNull Position position) {
        this.lines = lines;
        this.position = position;
        this.lineSpacing = lineSpacing;
        this.entityIds = MinecraftHelpers.generateIds(lines.size());
    }


    @Override
    public void show(final @NonNull GameClient viewer) {
        for (int i = 0; i < this.lines.size(); i++) {
            final double delta = i * this.lineSpacing;
            final Position linePos = this.position.plusY(delta);

            final int entityId = this.entityIds[i];
            final Component line = this.lines.get(i);

            if (line.equals(Component.empty())) {
                continue;
            }

            viewer.sendPacket(new ProtocolLibGamePacket(this.constructSpawnPacket(entityId, linePos)));
            viewer.sendPacket(new ProtocolLibGamePacket(this.constructMetadataPacket(entityId, line)));
            viewer.sendPacket(new ProtocolLibGamePacket(this.constructTeleportPacket(entityId, linePos)));
        }
    }

    @Override
    public void hide(final @NonNull GameClient viewer) {
        for (int i = 0; i < this.lines.size(); i++) {
            final int id = this.entityIds[i];
            viewer.sendPacket(new ProtocolLibGamePacket(this.constructDestroyPacket(id)));
        }
    }


    @Override
    public boolean matchesId(int entityId) {
        for (final int id : this.entityIds) {
            if (id == entityId) {
                return true;
            }
        }

        return false;
    }

    @Override
    public double height() {
        return 0;
    }

    @Override
    public void lookAt(final @NonNull GameClient viewer,
                       final @NonNull Position position) {
        return; // hologram; no need to do anything
    }

    @Override
    public void rotate(@NonNull GameClient viewer, float yaw, float pitch) {
        return; // hologram; no need to do anything
    }

    private @NonNull PacketContainer constructDestroyPacket(final int entityId) {
        final WrapperPlayServerEntityDestroy packet = new WrapperPlayServerEntityDestroy();
        packet.setEntityIds(new int[]{entityId});
        return packet.getHandle();
    }

    private @NonNull PacketContainer constructMetadataPacket(final int entityId,
                                                             final @NonNull Component line) {
        final PacketContainer container = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
        container.getIntegers().write(0, entityId); // write entity ID
        container.getWatchableCollectionModifier().write(0, this.getData(line).getWatchableObjects());
        return container;
    }

    private @NonNull PacketContainer constructSpawnPacket(final int entityId,
                                                          final @NonNull Position position) {
        final WrapperPlayServerSpawnEntity packet = new WrapperPlayServerSpawnEntity();
        packet.setEntityID(entityId);
        packet.setUniqueId(UUID.randomUUID());
        packet.setType(EntityType.ARMOR_STAND);
        packet.setX(position.x());
        packet.setY(position.y());
        packet.setZ(position.z());
        return packet.getHandle();
    }

    private @NonNull PacketContainer constructTeleportPacket(final int entityId,
                                                             final @NonNull Position position) {
        final WrapperPlayServerEntityTeleport packet = new WrapperPlayServerEntityTeleport();
        packet.setEntityID(entityId);
        packet.setX(position.x());
        packet.setY(position.y());
        packet.setZ(position.z());
        packet.setPitch(position.pitch());
        packet.setYaw(position.yaw());
        packet.setOnGround(false);
        return packet.getHandle();
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
