package cafe.navy.obsidian.paper.npc;

import cafe.navy.obsidian.core.player.GamePlayer;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;

public class SimpleNPC implements NPC {

    public static @NonNull Builder builder(final @NonNull JavaPlugin plugin,
                                           final @NonNull String id) {
        return new Builder(plugin, id);
    }

    private final @NonNull JavaPlugin plugin;
    private final @NonNull String id;
    private final @NonNull Function<GamePlayer, NPCState> stateGenerator;
    private final @NonNull Map<UUID, NPCState> states;
    private final int tickSpeed;
    private @Nullable BukkitTask tickTask;

    public SimpleNPC(final @NonNull JavaPlugin plugin,
                     final @NonNull String id,
                     final @NonNull Function<GamePlayer, NPCState> stateGenerator,
                     final int tickSpeed) {
        this.plugin = plugin;
        this.id = id;
        this.stateGenerator = stateGenerator;
        this.tickSpeed = tickSpeed;
        this.states = new ConcurrentHashMap<>();
    }

    @Override
    public @NonNull String id() {
        return this.id;
    }

    @Override
    public boolean hasViewer(final @NonNull GamePlayer player) {
        return this.states.containsKey(player.uuid());
    }

    @Override
    public void addViewer(final @NonNull GamePlayer player) {
        final NPCState state = this.stateGenerator.apply(player);
        state.add(player);
        this.states.put(player.uuid(), state);
    }

    @Override
    public void removeViewer(final @NonNull GamePlayer player) {
        if (this.states.containsKey(player.uuid())) {
            final NPCState state = this.states.get(player.uuid());
            state.remove(player);
            this.states.remove(player.uuid());
        }
    }

    @Override
    public void modifyState(final @NonNull GamePlayer player,
                            final @NonNull Consumer<NPCState> consumer) {
        if (this.states.containsKey(player.uuid())) {
            consumer.accept(this.states.get(player.uuid()));
        } else {
            throw new RuntimeException();
        }
    }

    @Override
    public void modifyStates(final @NonNull Consumer<NPCState> consumer) {
        for (final NPCState state : this.states.values()) {
            consumer.accept(state);
        }
    }

    @Override
    public void activate() {
        this.tickTask = new BukkitRunnable() {
            @Override
            public void run() {
                for (final NPCState state : states.values()) {
                    state.tick();
                }
            }
        }.runTaskTimer(this.plugin, 0, 0);
    }

    @Override
    public void deactivate() {

    }

    @Override
    public boolean isActive() {
        return this.tickTask != null;
    }

    public static class Builder {

        private final @NonNull JavaPlugin plugin;
        private final @NonNull String id;
        private @Nullable Function<GamePlayer, NPCState> stateGenerator;
        private int tickSpeed = -1;

        private Builder(final @NonNull JavaPlugin plugin,
                        final @NonNull String id) {
            this.plugin = plugin;
            this.id = id;
        }

        public @NonNull Builder defaultState(final @NonNull NPCState state) {
            this.stateGenerator = (player) -> state;
            return this;
        }

        public @NonNull Builder stateGenerator(final @NonNull Function<GamePlayer, NPCState> stateGenerator) {
            this.stateGenerator = stateGenerator;
            return this;
        }

        public @NonNull Builder tickSpeed(final int tick) {
            this.tickSpeed = tick;
            return this;
        }

        public @NonNull Builder noTick() {
            this.tickSpeed = -1;
            return this;
        }

        public @NonNull NPC build() {
            return new SimpleNPC(this.plugin, this.id, Objects.requireNonNull(this.stateGenerator), this.tickSpeed);
        }

    }
}
