package cafe.navy.bedrock.paper.entity;

import cafe.navy.bedrock.paper.exception.ClientEntityException;
import cafe.navy.bedrock.paper.target.EntityTarget;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public class PaperClientEntity implements ClientEntity {

    private final @NonNull Entity entity;

    public PaperClientEntity(final @NonNull Entity entity) {
        this.entity = entity;
    }

    @Override
    public @NonNull UUID uuid() {
        return this.entity.getUniqueId();
    }

    @Override
    public double renderDistance() {
        return ClientEntity.super.renderDistance();
    }

    @Override
    public @NonNull Location location() {
        return this.entity.getLocation();
    }

    @Override
    public void add(final @NonNull EntityTarget target) throws ClientEntityException {
        final UUID uuid = target.uuid();
        final Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            throw new ClientEntityException("");
        }

        final ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        final net.minecraft.world.entity.Entity serverEntity = ((CraftEntity) this.entity).getHandle();
        serverPlayer.connection.send(serverEntity.getAddEntityPacket());
        serverPlayer.connection.send(new ClientboundSetEntityDataPacket(serverEntity.getId(), serverEntity.getEntityData(), true));
    }

    @Override
    public void remove(final @NonNull EntityTarget target) throws ClientEntityException {

        final UUID uuid = target.uuid();
        final Player player = Bukkit.getPlayer(uuid);
        if (player == null) {
            throw new ClientEntityException("");
        }

        final ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        serverPlayer.connection.send(new ClientboundRemoveEntitiesPacket(this.entity.getEntityId()));
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
}
