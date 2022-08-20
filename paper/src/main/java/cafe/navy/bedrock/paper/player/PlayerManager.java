package cafe.navy.bedrock.paper.player;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PlayerManager implements Listener {

    private final @NonNull JavaPlugin plugin;
    private final @NonNull Map<UUID, PlayerTarget> playerInstances;

    public PlayerManager(final @NonNull JavaPlugin plugin) {
        this.plugin = plugin;
        this.playerInstances = new HashMap<>();
    }

    public void enable() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
        this.plugin.getServer().getOnlinePlayers().forEach(this::init);
    }

    public void disable() {
        HandlerList.unregisterAll(this);
    }

    public @NonNull Optional<PlayerTarget> getPlayer(final @NonNull UUID uuid) {
        return Optional.ofNullable(this.playerInstances.get(uuid));
    }

    private void init(final @NonNull Player player) {
        this.playerInstances.put(player.getUniqueId(), new PlayerTarget(this.plugin, player));
    }

}
