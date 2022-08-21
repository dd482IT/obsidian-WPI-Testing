package cafe.navy.bedrock.paper.entity;

import cafe.navy.bedrock.paper.exception.ClientEntityException;
import cafe.navy.bedrock.paper.target.EntityTarget;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * Represents a client-side entity that can be sent or removed from a player.
 */
public interface ClientEntity {

    /**
     * Returns the {@link UUID} of this ClientEntity.
     *
     * @return the {@link UUID}
     */
    @NonNull UUID uuid();

    /**
     * Returns the intended view radius for this entity.
     *
     * @return the entity's view radius
     */
    default double viewRadius() {
        return 20;
    }

    /**
     * Returns the location of the entity.
     *
     * @return the entity location
     */
    @NonNull Location location();

    void add(final @NonNull EntityTarget target) throws ClientEntityException;

    void remove(final @NonNull EntityTarget target) throws ClientEntityException;

    void update(final @NonNull EntityTarget target) throws ClientEntityException;

    default void activate() {

    }

    default void deactivate() {

    }

    default boolean active() {
        return false;
    }

    boolean checkId(final int entityId);

}
