package cafe.navy.bedrock.paper.entity;

import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a client-side entity that can be sent or removed from a player.
 */
public interface ClientEntity {

    default double viewRadius() {
        return 20;
    }

    void show(final @NonNull Player player);

    void hide(final @NonNull Player player);

}
