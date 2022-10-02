package cafe.navy.obsidian.paper.api.client;

import cafe.navy.obsidian.core.client.GameClient;
import cafe.navy.obsidian.core.client.GamePacket;
import cafe.navy.obsidian.core.player.GamePlayer;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

public class PaperGamePlayer implements GamePlayer {

    @Override
    public void sendPacket(final @NonNull GamePacket packet) {

    }

    @Override
    public void sendMessage(final @NonNull Component message) {

    }

    @Override
    public void disconnect(@Nullable Component message) {

    }

    @Override
    public @NonNull Component displayName() {
        return null;
    }

    @Override
    public @NonNull String name() {
        return null;
    }

    @Override
    public @NonNull UUID uuid() {
        return null;
    }

    @Override
    public @NonNull GameClient client() {
        return null;
    }
}
