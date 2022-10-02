package cafe.navy.obsidian.paper.entities.entity;

import cafe.navy.obsidian.core.client.GameClient;
import cafe.navy.obsidian.core.util.Position;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface CustomEntity {

    /**
     * Shows the entity to a client.
     *
     * @param viewer the viewer
     */
    void show(final @NonNull GameClient player);

    /**
     * Hides the entity from a viewer.
     *
     * @param viewer the viewer
     */
    void hide(final @NonNull GameClient viewer);

    /**
     * If the provided entity ID matches this (or a child's) ID, this method will return true. Otherwise, false.
     *
     * @param entityId the entity id
     * @return true or false
     */
    boolean matchesId(final int entityId);

    double height();

    void lookAt(final @NonNull GameClient viewer,
                final @NonNull Position position);

    void rotate(final @NonNull GameClient viewer,
                final float yaw,
                final float pitch);


}
