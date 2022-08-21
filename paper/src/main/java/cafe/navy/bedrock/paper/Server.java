package cafe.navy.bedrock.paper;

import cafe.navy.bedrock.paper.command.CommandRegistry;
import cafe.navy.bedrock.paper.command.bedrock.BedrockCommand;
import cafe.navy.bedrock.paper.item.ItemManager;
import cafe.navy.bedrock.paper.player.PlayerManager;
import cafe.navy.bedrock.paper.realm.Realm;
import cafe.navy.bedrock.paper.realm.WorldRealm;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
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
public class Server implements Listener {

    private final @NonNull JavaPlugin plugin;
    private final @NonNull PlayerManager playerManager;
    private final @NonNull CommandRegistry commandRegistry;
    private final @NonNull ItemManager itemManager;
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
        this.itemManager = new ItemManager(this.plugin, this.playerManager);
        try {
            this.commandRegistry = new CommandRegistry(this.plugin);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.realms = new HashMap<>();
    }

    /**
     * Returns whether the server has been enabled.
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
     * Returns the {@link CommandRegistry}.
     *
     * @return the command registry
     */
    public @NonNull CommandRegistry commands() {
        return this.commandRegistry;
    }

    /**
     * Returns the {@link ItemManager}.
     *
     * @return the item manager
     */
    public @NonNull ItemManager items() {
        return this.itemManager;
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
     * Returns the plugin who owns this server.
     *
     * @return the plugin
     */
    public @NonNull JavaPlugin plugin() {
        return this.plugin;
    }

    /**
     * Enables {@code Server}.
     */
    public void enable() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);

        for (final Player player : Bukkit.getOnlinePlayers()) {
            this.playerManager.add(player);
        }

        for (final World world : Bukkit.getWorlds()) {
            this.registerRealm(new WorldRealm(this, world));
        }


        this.itemManager.enable();
        this.commandRegistry.addCommand(new BedrockCommand(this));
        this.enabled = true;
    }

    /**
     * Disables {@code Server}.
     */
    public void disable() {
        HandlerList.unregisterAll(this);
        this.realms.clear();
        this.itemManager.disable();
        this.enabled = false;
    }

    /**
     * {@link BukkitRunnable#runTaskTimer} alias
     *
     * @param runnable the runnable
     * @param time     time delay
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
     *
     * @param runnable the runnable
     * @param time     time delay
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

    @EventHandler
    private void onJoin(final @NonNull PlayerJoinEvent event) {
        this.playerManager.add(event.getPlayer());
        this.realms.get(event.getPlayer().getWorld().getUID()).add(this.playerManager.getPlayer(event.getPlayer().getUniqueId()).get());
    }

    @EventHandler
    private void onQuit(final @NonNull PlayerQuitEvent event) {
        this.realms.get(event.getPlayer().getWorld().getUID()).remove(this.playerManager.getPlayer(event.getPlayer().getUniqueId()).get());
        this.playerManager.remove(event.getPlayer());
    }

    @EventHandler
    private void changeWorld(final @NonNull PlayerChangedWorldEvent event) {
        final World from = event.getFrom();
        final World to = event.getPlayer().getWorld();

        this.realms.get(to.getUID()).add(this.playerManager.getPlayer(event.getPlayer().getUniqueId()).get());
        this.realms.get(from.getUID()).remove(this.playerManager.getPlayer(event.getPlayer().getUniqueId()).get());

    }
}
