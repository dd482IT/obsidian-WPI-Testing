package cafe.navy.bedrock.paper.entity;

import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a client-side entity that can be sent or removed from a player.
 */
public interface ClientEntity {

    /**
     * Returns the intended view radius for this entity.
     *
     * @return the entity's view radius
     */
    default double viewRadius() {
        return 20;
    }

    /**
     * Shows the entity to a player.
     *
     * @param player the player
     */
    void show(final @NonNull Player player);

    /**
     * Hides the entity from a player.
     *
     * @param player the player
     */
    void hide(final @NonNull Player player);

}
