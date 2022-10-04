package cafe.navy.obsidian.paper.entity.renderer;

import cafe.navy.obsidian.core.client.GameClient;
import cafe.navy.obsidian.core.util.Position;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * {@code EntityRenderer} controls the visual rendering of a type of entity. {@code EntityRenderer} instances can
 * be used to show one entity to a player or multiple players.
 * <p>
 * {@code EntityRenderer} does not maintain state or knowledge on viewers, this responsibility must be handled
 * by another class such as {@link cafe.navy.obsidian.paper.npc.NPC}.
 */
public interface EntityRenderer {

    /**
     * Sends the entity 'spawn' packets to the client.
     *
     * @param client the client
     */
    void show(final @NonNull GameClient client);

    /**
     * Sends the entity 'despawn' packets to the client.
     *
     * @param client the client
     */
    void hide(final @NonNull GameClient client);

    /**
     * Updates the entity's location on the client.
     *
     * @param client   the client
     * @param position the new position
     */
    void updateLocation(final @NonNull GameClient client,
                        final @NonNull Position position);

    /**
     * Updates the entity's rotation on the client.
     *
     * @param client the client
     * @param yaw    the new yaw
     * @param pitch  the new pitch
     */
    void updateRotation(final @NonNull GameClient client,
                        final float yaw,
                        final float pitch);

    /**
     * Returns the 'visual height' of the rendered entity.
     *
     * @return the visual height
     */
    double visualHeight();

    /**
     * {@code Builder} defines a builder that constructs {@link EntityRenderer} instances.
     *
     * @param <T> the {@link EntityRenderer} type
     */
    interface Builder<T extends EntityRenderer> {

        @NonNull T build();

    }

}
