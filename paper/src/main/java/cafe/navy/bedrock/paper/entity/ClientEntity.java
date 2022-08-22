package cafe.navy.bedrock.paper.entity;

import cafe.navy.bedrock.paper.exception.ClientEntityException;
import cafe.navy.bedrock.paper.message.Message;
import cafe.navy.bedrock.paper.player.PlayerUtil;
import cafe.navy.bedrock.paper.target.EntityTarget;
import net.minecraft.world.entity.ambient.Bat;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;

/**
 * Represents a client-side entity that can be sent or removed from a player.
 */
public abstract class ClientEntity {

    protected final @NonNull UUID uuid;
    private final double viewRadius;
    private final @NonNull List<EntityTarget> viewers;
    private final @NonNull List<ClientEntity> children;
    private @NonNull Location location;
    private @Nullable ClientEntity parent;
    private boolean active;

    public ClientEntity(final @NonNull UUID uuid,
                        final @NonNull Location location) {
        this.uuid = uuid;
        this.location = location.clone();
        this.viewRadius = 30;
        this.viewers = new ArrayList<>();
        this.children = new ArrayList<>();
        this.parent = null;
        this.active = false;
    }


    /**
     * Returns the {@link UUID} of this ClientEntity.
     *
     * @return the {@link UUID}
     */
    public @NonNull UUID uuid() {
        return this.uuid;
    }

    /**
     * Returns the intended view radius for this entity.
     *
     * @return the entity's view radius
     */
    public double viewRadius() {
        return this.viewRadius;
    }

    /**
     * Returns the location of the entity.
     *
     * @return the entity location
     */
    public @NonNull Location location() {
        return this.location.clone();
    }

    public void add(final @NonNull EntityTarget target) throws ClientEntityException {
        this.show(target);
        target.add(this);
        this.viewers.add(target);
//        this.sendViewingDebugMessage(PlayerUtil.getPlayer(target.uuid()).get());
        this.children().forEach(child -> {
            try {
                child.add(target);
            } catch (ClientEntityException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void remove(final @NonNull EntityTarget target) throws ClientEntityException {
        this.hide(target);
        target.remove(this);
        this.viewers.remove(target);
        this.children().forEach(child -> {
            try {
                child.remove(target);
            } catch (ClientEntityException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void remove() {
        for (final var viewer : List.copyOf(this.viewers)) {
            try {
                this.remove(viewer);
            } catch (ClientEntityException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public @NonNull List<ClientEntity> children() {
        return List.copyOf(this.children);
    }

    public @NonNull Optional<@NonNull ClientEntity> parent() {
        return Optional.ofNullable(this.parent);
    }

    public void addChild(final @NonNull ClientEntity entity) throws ClientEntityException{
        this.children.add(entity);
        for (EntityTarget viewer : this.viewers) {
            entity.add(viewer);
        }
        entity.setParent(this);
    }

    public void removeChild(final @NonNull ClientEntity entity) throws ClientEntityException{
        this.children.remove(entity);
        for (EntityTarget viewer : this.viewers) {
            entity.remove(viewer);
        }
        entity.setParent(null);
    }

    public void activate() {
        // TODO impl
        this.active = true;
    }

    public void deactivate() {
        // TODO impl
        this.active = false;
    }

    public boolean active() {
        return this.active;
    }

    public @NonNull Optional<@NonNull ClientEntity> getParent() {
        return Optional.ofNullable(this.parent);
    }

    public @NonNull ClientEntity getTop() {
        return Objects.requireNonNullElse(this.parent, this);
    }

    protected void update() {
        for (EntityTarget viewer : this.viewers) {
            try {
                update(viewer);
            } catch (ClientEntityException e) {
                throw new RuntimeException(e);
            }
        }

        for (ClientEntity child : this.children) {
            child.update();
        }
    }

    protected abstract void show(final @NonNull EntityTarget target) throws ClientEntityException;

    protected abstract void hide(final @NonNull EntityTarget target) throws ClientEntityException;

    protected abstract void update(final @NonNull EntityTarget target) throws ClientEntityException;

    protected abstract boolean isEntityIdPresent(final int id);

    private void setParent(final @Nullable ClientEntity entity) {
        this.parent = entity;
    }

    public boolean checkEntityId(final int id) {
        for (final ClientEntity entity : this.children()) {
            if (entity.checkEntityId(id)) {
                return true;
            }
        }

        return this.isEntityIdPresent(id);
    }

    private void sendViewingDebugMessage(final @NonNull Player player) {
        final String indent = " ".repeat(this.getLayer());
        player.sendMessage(Message.create(indent)
                .main("Added to ")
                .highlight(this.getClass().getSimpleName())
                .main(": ")
                .highlight(this.uuid.toString()));
    }

    private int getLayer() {
        int layer = 0;
        if (this.parent == null) {
            return layer;
        }

        layer =+ this.parent.getLayer();
        return layer;
    }

    public @NonNull List<EntityTarget> viewers() {
        return List.copyOf(this.viewers);
    }

    protected void setLocation(final @NonNull Location location) {
        this.location = location;
    }

    public abstract void teleport(final @NonNull Location location);

}
