package cafe.navy.obsidian.core.player;

import cafe.navy.obsidian.core.client.GameClient;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public interface GamePlayer extends GameClient {

    /**
     * Returns the player's display name.
     *
     * @return the display name
     */
    @NonNull Component displayName();

    /**
     * Returns the player's username.
     *
     * @return the username
     */
    @NonNull String name();

    /**
     * Returns the {@link UUID} of this player.
     *
     * @return the player's UUID
     */
    @NonNull UUID uuid();

    /**
     * Returns the player's {@link GameClient}.
     *
     * @return the player's game client
     */
    @NonNull GameClient client();

}
