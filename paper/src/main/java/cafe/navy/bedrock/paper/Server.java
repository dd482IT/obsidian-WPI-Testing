package cafe.navy.bedrock.paper;

import cafe.navy.bedrock.paper.player.PlayerManager;
import cafe.navy.bedrock.paper.realm.Realm;
import cafe.navy.bedrock.paper.realm.WorldRealm;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * {@code Server} maintains instances of all the important classes for a server utilizing bedrock's utilities.
 * <p>
 * Plugins utilizing most, if not all the bedrock feature set may find this class useful.
 */
public class Server {

    private final @NonNull JavaPlugin plugin;
    private final @NonNull PlayerManager playerManager;
    private final @NonNull Map<UUID, Realm> realms;
    private boolean enabled = false;

    /**
     * Constructs {@code Server}.
     *
     * @param plugin the plugin that instantiated this server
     */
    public Server(final @NonNull JavaPlugin plugin) {
        this.plugin = plugin;
        this.playerManager = new PlayerManager(this.plugin);
        this.realms = new HashMap<>();
    }

    /**
     * Returns whether or not the server has been enabled.
     *
     * @return true or false
     */
    public boolean enabled() {
        return this.enabled;
    }

    /**
     * Returns the {@link PlayerManager}.
     *
     * @return the player manager
     */
    public @NonNull PlayerManager players() {
        return this.playerManager;
    }

    /**
     * Returns the list of registered {@link Realm}s.
     *
     * @return the realms list
     */
    public @NonNull List<Realm> realms() {
        return List.copyOf(this.realms.values());
    }

    /**
     * Enables {@code Server}.
     */
    public void enable() {
        this.playerManager.enable();
        for (final World world : Bukkit.getWorlds()) {
            this.registerRealm(new WorldRealm(this, world));
        }

        this.enabled = true;
    }

    /**
     * Disables {@code Server}.
     */
    public void disable() {
        this.playerManager.disable();
        this.realms.clear();
        this.enabled = false;
    }

    /**
     * {@link BukkitRunnable#runTaskTimer} alias
     * @param runnable the runnable
     * @param time time delay
     * @return the task
     */
    public @NonNull BukkitTask registerTimedTask(final @MonotonicNonNull Runnable runnable,
                                                 final int time) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskTimer(this.plugin, 0, time);
    }

    /**
     * {@link BukkitRunnable#runTaskLater} alias
     * @param runnable the runnable
     * @param time time delay
     * @return the task
     */
    public @NonNull BukkitTask registerDelayedTask(final @MonotonicNonNull Runnable runnable,
                                                   final int time) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskLater(this.plugin, time);
    }

    private void registerRealm(final @NonNull Realm realm) {
        realm.enable();
        this.realms.put(realm.uuid(), realm);
    }
}
