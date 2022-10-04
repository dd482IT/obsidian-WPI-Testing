package cafe.navy.obsidian.paper.api.client;

import cafe.navy.obsidian.core.client.GameClient;
import cafe.navy.obsidian.core.client.GamePacket;
import cafe.navy.obsidian.core.player.GamePlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

public class PaperGamePlayer implements GamePlayer {

    private final @NonNull Player player;
    private final @NonNull GameClient client;

    public PaperGamePlayer(final @NonNull Player player) {
        this.player = player;
        this.client = new PaperGameClient(this.player);
    }

    @Override
    public void sendPacket(final @NonNull GamePacket packet) {
        this.client.sendPacket(packet);
    }

    @Override
    public void sendMessage(final @NonNull Component message) {
        this.client.sendMessage(message);
    }

    @Override
    public void disconnect(@Nullable Component message) {
        this.client.disconnect(message);
    }

    @Override
    public @NonNull Component displayName() {
        return this.player.displayName();
    }

    @Override
    public @NonNull String name() {
        return this.player.getName();
    }

    @Override
    public @NonNull UUID uuid() {
        return this.player.getUniqueId();
    }

    @Override
    public @NonNull GameClient client() {
        return this.client;
    }
}
