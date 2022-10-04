package cafe.navy.obsidian.paper.api.client;

import cafe.navy.obsidian.core.client.GameClient;
import cafe.navy.obsidian.core.client.GamePacket;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

public class PaperGameClient implements GameClient {

    private final @NonNull Player player;

    public PaperGameClient(final @NonNull Player player) {
        this.player = player;
    }

    @Override
    public @NonNull UUID uuid() {
        return player.getUniqueId();
    }

    @Override
    public void sendPacket(final @NonNull GamePacket packet) {
        packet.send(this);
    }

    @Override
    public void sendMessage(final @NonNull Component message) {
        this.player.sendMessage(message);
    }

    @Override
    public void disconnect(final @Nullable Component message) {
        this.player.kick(message);
    }
}
