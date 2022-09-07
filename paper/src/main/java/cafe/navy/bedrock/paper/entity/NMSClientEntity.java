package cafe.navy.bedrock.paper.entity;

import cafe.navy.bedrock.paper.exception.ClientEntityException;
import cafe.navy.bedrock.paper.util.PlayerUtil;
import cafe.navy.bedrock.paper.target.EntityTarget;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.world.entity.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;
import org.checkerframework.checker.nullness.qual.NonNull;

public class NMSClientEntity extends ClientEntity {

    private final @NonNull Entity entity;

    public NMSClientEntity(final @NonNull Entity entity) {
        super(entity.getUUID(), entity.getBukkitEntity().getLocation());
        this.entity = entity;
    }

    public NMSClientEntity(final org.bukkit.entity.@NonNull Entity entity) {
        super(entity.getUniqueId(), entity.getLocation());
        this.entity = ((CraftEntity) entity).getHandle();
    }

    public @NonNull Entity entity() {
        return this.entity;
    }

    @Override
    protected void show(final @NonNull EntityTarget target) {
        final var playerOpt = PlayerUtil.getServerPlayer(target.uuid());
        if (playerOpt.isEmpty()) {
            throw new RuntimeException();
        }

        final var player = playerOpt.get();
        final var addPacket = this.entity.getAddEntityPacket();
        final var dataPacket = new ClientboundSetEntityDataPacket(this.entity.getId(), this.entity.getEntityData(), true);

        player.connection.send(addPacket);
        player.connection.send(dataPacket);
    }

    @Override
    protected void hide(final @NonNull EntityTarget target) {
        final var playerOpt = PlayerUtil.getServerPlayer(target.uuid());
        if (playerOpt.isEmpty()) {
            throw new RuntimeException();
        }

        final var player = playerOpt.get();
        final var removePacket = new ClientboundRemoveEntitiesPacket(this.entity.getId());
        player.connection.send(removePacket);
    }

    @Override
    protected void update(@NonNull EntityTarget target) throws ClientEntityException {
        final var playerOpt = PlayerUtil.getServerPlayer(target.uuid());
        if (playerOpt.isEmpty()) {
            throw new RuntimeException();
        }

        final var player = playerOpt.get();
        final var dataPacket = new ClientboundSetEntityDataPacket(this.entity.getId(), this.entity.getEntityData(), true);
        final var tpPacket = new ClientboundTeleportEntityPacket(this.entity);
        player.connection.send(dataPacket);
        player.connection.send(tpPacket);

    }

    @Override
    protected boolean isEntityIdPresent(final int id) {
        return this.entity.getId() == id;
    }

    @Override
    public void teleport(@NonNull Location location) {
        this.entity.teleportTo(location.getX(), location.getY(), location.getZ());
        this.update();
    }
}
