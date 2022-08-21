package cafe.navy.bedrock.paper.entity;

import cafe.navy.bedrock.paper.exception.ClientEntityException;
import cafe.navy.bedrock.paper.target.EntityTarget;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public class NMSClientEntity implements ClientEntity {

    private final @NonNull Entity entity;

    public NMSClientEntity(final @NonNull Entity entity) {
        this.entity = entity;
    }

    @Override
    public @NonNull UUID uuid() {
        return this.entity.getUUID();
    }

    @Override
    public @NonNull Location location() {
        return new Location(
                Bukkit.getWorld(this.entity.getOriginWorld()),
                this.entity.getX(),
                this.entity.getY(),
                this.entity.getZ()
        );
    }

    @Override
    public void add(final @NonNull EntityTarget target) throws ClientEntityException {
        final UUID uuid = target.uuid();
        final Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            throw new ClientEntityException("");
        }

        final ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        serverPlayer.connection.send(this.entity.getAddEntityPacket());
        serverPlayer.connection.send(new ClientboundSetEntityDataPacket(this.entity.getId(), this.entity.getEntityData(), true));
    }

    @Override
    public void remove(final @NonNull EntityTarget target) throws ClientEntityException {

        final UUID uuid = target.uuid();
        final Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            throw new ClientEntityException("");
        }

        final ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        serverPlayer.connection.send(new ClientboundRemoveEntitiesPacket(this.entity.getId()));
    }

    @Override
    public void update(@NonNull EntityTarget target) throws ClientEntityException {

    }

    @Override
    public void activate() {
        ClientEntity.super.activate();
    }

    @Override
    public void deactivate() {
        ClientEntity.super.deactivate();
    }

    @Override
    public boolean active() {
        return ClientEntity.super.active();
    }

    @Override
    public boolean checkId(int entityId) {
        return entityId == this.entity.getId();
    }
}
