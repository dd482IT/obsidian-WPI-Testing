package cafe.navy.obsidian.paper.entities.util;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * {@code TaskExecutor} executes tasks synchronously or asynchronously.
 */
public class TaskExecutor {

    private final @NonNull JavaPlugin plugin;

    public TaskExecutor(final @NonNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public @NonNull BukkitTask repeat(final int delay, final @NonNull Runnable runnable) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskTimer(this.plugin, 0, delay);
    }

    public @NonNull BukkitTask once(final @NonNull Runnable runnable) {
        return this.once(runnable, 0);
    }

    public @NonNull BukkitTask once(final @NonNull Runnable runnable, final int delay) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskLater(this.plugin, delay);
    }

    public @NonNull BukkitTask repeatAsync(final int delay, final @NonNull Runnable runnable) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskTimerAsynchronously(this.plugin, 0, delay);
    }

    public @NonNull BukkitTask onceAsync(final @NonNull Runnable runnable) {
        return this.once(runnable, 0);
    }

    public @NonNull BukkitTask onceAsync(final @NonNull Runnable runnable, final int delay) {
        return new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTaskLaterAsynchronously(this.plugin, delay);
    }

}
