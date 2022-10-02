package cafe.navy.obsidian.core.client;

import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

/**
 * {@code GameClient} defines what a connected game client looks like.
 */
public interface GameClient {

    @NonNull UUID uuid();

    void sendPacket(final @NonNull GamePacket packet);

    void sendMessage(final @NonNull Component message);

    void disconnect(final @Nullable Component message);

    default void disconnect() {
        this.disconnect(null);
    }


}
