package cafe.navy.obsidian.core.client;

import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

/**
 * {@code GameClient} defines methods for interacting with a connected game client.
 */
public interface GameClient {

    /**
     * The {@link UUID} of the client.
     *
     * @return the client's {@link UUID}
     */
    @NonNull UUID uuid();

    /**
     * Sends a {@link GamePacket} to the client.
     *
     * @param packet the packet
     */
    void sendPacket(final @NonNull GamePacket packet);

    /**
     * Sends a {@link Component} message to the client.
     *
     * @param message the message
     */
    void sendMessage(final @NonNull Component message);

    /**
     * Disconnects the client, displaying {@code message} on their screen.
     *
     * @param message the message
     */
    void disconnect(final @Nullable Component message);

    /**
     * Disconnects the client.
     */
    default void disconnect() {
        this.disconnect(null);
    }


}
