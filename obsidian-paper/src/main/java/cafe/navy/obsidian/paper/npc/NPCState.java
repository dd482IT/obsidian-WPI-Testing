package cafe.navy.obsidian.paper.npc;

import cafe.navy.obsidian.core.player.GamePlayer;
import cafe.navy.obsidian.core.util.Position;
import cafe.navy.obsidian.paper.entity.renderer.EntityRenderer;
import cafe.navy.obsidian.paper.entity.renderer.type.hologram.HologramOptions;
import cafe.navy.obsidian.paper.entity.renderer.type.hologram.HologramRenderer;
import cafe.navy.obsidian.paper.npc.behaviour.NPCBehaviour;
import org.bukkit.World;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class NPCState {

    public static @NonNull Builder builder(final @NonNull Position position,
                                           final @NonNull World world) {
        return new Builder(position, world);
    }

    private final @NonNull EntityRenderer<?> renderer;
    private final @NonNull World world;
    private final @NonNull List<String> names;
    private final @NonNull Set<GamePlayer> players;
    private final @NonNull Set<NPCBehaviour> behaviours;
    private @NonNull Position position;
    private @Nullable HologramRenderer hologram;

    public NPCState(final @NonNull Position position,
                    final @NonNull World world,
                    final @NonNull EntityRenderer<?> renderer,
                    final @NonNull List<String> names,
                    final @NonNull Set<NPCBehaviour> behaviours) {
        this.position = position;
        this.world = world;
        this.renderer = renderer;
        this.names = names;
        this.behaviours = behaviours;
        this.players = new HashSet<>();
        this.hologram = new HologramRenderer(new HologramOptions(names, this.position.plusY(this.renderer.visualHeight()), 0.375));
    }

    public @NonNull World world() {
        return this.world;
    }

    public @NonNull Position position() {
        return this.position;
    }

    public @NonNull EntityRenderer<?> renderer() {
        return this.renderer;
    }

    public @NonNull Collection<GamePlayer> players() {
        return this.players;
    }

    public void add(final @NonNull GamePlayer player) {
        this.players.add(player);
        this.renderer.show(player);
        this.hologram.show(player);
    }

    public void remove(final @NonNull GamePlayer player) {
        this.players.remove(player);
        this.renderer.hide(player);
        this.hologram.hide(player);
    }

    public void tick() {
        for (final NPCBehaviour behaviour : this.behaviours) {
            behaviour.tick(this);
        }
    }

    public static class Builder {

        private final @NonNull World world;
        private final @NonNull Set<NPCBehaviour> behaviours;
        private final @NonNull Position position;
        private @Nullable EntityRenderer<?> renderer;
        private @Nullable List<String> names;

        private Builder(final @NonNull Position position,
                        final @NonNull World world) {
            this.behaviours = new HashSet<>();
            this.position = position;
            this.world = world;
        }

        public @NonNull Builder behaviour(final @NonNull NPCBehaviour behaviour) {
            this.behaviours.add(behaviour);
            return this;
        }

        public @NonNull Builder renderer(final @NonNull EntityRenderer<?> renderer) {
            this.renderer = renderer;
            return this;
        }

        public @NonNull Builder name(final @NonNull String name) {
            this.names = List.of(name);
            return this;
        }

        public @NonNull Builder names(final @NonNull String... names) {
            this.names = List.of(names);
            return this;
        }

        public @NonNull NPCState build() {
            Objects.requireNonNull(this.renderer);
            Objects.requireNonNull(this.names);
            return new NPCState(this.position, this.world, this.renderer, this.names, this.behaviours);
        }

    }

}
