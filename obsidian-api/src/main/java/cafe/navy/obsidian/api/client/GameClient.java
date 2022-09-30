package cafe.navy.obsidian.api.client;

import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * {@code GameClient} represents an instance of a game client that is connected to the server.
 */
public interface GameClient {

    // TODO: create packet type
    void sendPacket(final @NonNull Object packet);

    void disconnect(final @NonNull Component message);

    void disconnect();

}
