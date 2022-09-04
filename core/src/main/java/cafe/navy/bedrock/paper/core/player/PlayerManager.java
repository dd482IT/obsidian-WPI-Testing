package cafe.navy.bedrock.paper.core.player;

import cafe.navy.bedrock.paper.core.data.DataService;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

public class PlayerManager implements Listener {

    private final @NonNull JavaPlugin plugin;
    private final @NonNull DataService dataService;
    private final @NonNull Map<UUID, PlayerTarget> playerInstances;

    public PlayerManager(final @NonNull DataService dataService,
                         final @NonNull JavaPlugin plugin) {
        this.dataService = dataService;
        this.plugin = plugin;
        this.playerInstances = new HashMap<>();
    }

    public @NonNull Optional<PlayerTarget> getPlayer(final @NonNull UUID uuid) {
        return Optional.ofNullable(this.playerInstances.get(uuid));
    }

    public void add(final @NonNull Player player) {
        this.playerInstances.put(player.getUniqueId(), new PlayerTarget(this.plugin, this.dataService.newHolder(), player));
    }

    public void remove(final @NonNull Player player) {
        this.playerInstances.remove(player.getUniqueId());
    }

    public @NonNull List<PlayerTarget> players() {
        return List.copyOf(this.playerInstances.values());
    }

}
