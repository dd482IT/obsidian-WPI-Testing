package cafe.navy.obsidian.paper.api.server;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

public class GameServerUpdateListener implements Listener {

    private final @NonNull PaperGameServer server;

    protected GameServerUpdateListener(final @NonNull PaperGameServer server) {
        this.server = server;
    }

    @EventHandler
    private void onJoin(final @NonNull AsyncPlayerPreLoginEvent event) {

    }

    @EventHandler
    private void onQuit(final @NonNull AsyncPlayerPreLoginEvent event) {

    }

}
