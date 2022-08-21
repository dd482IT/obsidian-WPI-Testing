package cafe.navy.bedrock.paper.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

public class PlayerManager implements Listener {

    private final @NonNull JavaPlugin plugin;
    private final @NonNull Map<UUID, PlayerTarget> playerInstances;

    public PlayerManager(final @NonNull JavaPlugin plugin) {
        this.plugin = plugin;
        this.playerInstances = new HashMap<>();
    }

    public @NonNull Optional<PlayerTarget> getPlayer(final @NonNull UUID uuid) {
        return Optional.ofNullable(this.playerInstances.get(uuid));
    }

    public void add(final @NonNull Player player) {
        this.playerInstances.put(player.getUniqueId(), new PlayerTarget(this.plugin, player));
    }

    public void remove(final @NonNull Player player) {
        this.playerInstances.remove(player.getUniqueId());
    }

    public @NonNull List<PlayerTarget> players() {
        return List.copyOf(this.playerInstances.values());
    }

}
