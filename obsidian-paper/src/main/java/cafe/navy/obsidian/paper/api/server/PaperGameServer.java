package cafe.navy.obsidian.paper.api.server;

import cafe.navy.obsidian.core.player.GamePlayer;
import cafe.navy.obsidian.core.server.GameServer;
import cafe.navy.obsidian.core.server.GameWorld;
import cafe.navy.obsidian.paper.api.client.PaperGamePlayer;
import me.lucko.helper.Events;
import me.lucko.helper.event.Subscription;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PaperGameServer implements GameServer {

    public static @NonNull Builder newBuilder(final @NonNull String id) {
        return new Builder(id);
    }

    private final @NonNull String id;
    private final @NonNull Map<UUID, GamePlayer> players;
    private final @NonNull Map<UUID, GameWorld> worldsByUuid;
    private final @NonNull Map<String, GameWorld> worldsById;
    private final @NonNull Set<Subscription> subscriptions;

    private PaperGameServer(final @NonNull String id,
                            final @NonNull Set<Subscription> subscriptions) {
        this.id = id;
        this.players = new ConcurrentHashMap<>();
        this.worldsByUuid = new HashMap<>();
        this.worldsById = new HashMap<>();
        this.subscriptions = new HashSet<>();
        this.subscriptions.addAll(subscriptions);
    }

    @Override
    public @NonNull String id() {
        return this.id;
    }

    @Override
    public @NonNull Optional<GamePlayer> player(final @NonNull UUID uuid) {
        return Optional.ofNullable(this.players.get(uuid));
    }

    @Override
    public @NonNull Optional<GamePlayer> player(final @NonNull String name) {
        return Optional.ofNullable(this.players.get(name));
    }

    @Override
    public @NonNull Collection<GamePlayer> players() {
        return this.players.values();
    }

    @Override
    public @NonNull Collection<GameWorld> worlds() {
        return List.copyOf(this.worldsByUuid.values());
    }

    @Override
    public @NonNull Optional<GameWorld> world(final @NonNull String id) {
        return Optional.ofNullable(this.worldsById.get(id));
    }

    @Override
    public @NonNull Optional<GameWorld> world(final @NonNull UUID uuid) {
        return Optional.ofNullable(this.worldsByUuid.get(uuid));
    }

    @Override
    public void enable() {
        final var join = Events.subscribe(PlayerJoinEvent.class).handler(e -> this.players.put(e.getPlayer().getUniqueId(), new PaperGamePlayer(e.getPlayer())));
        final var quit = Events.subscribe(PlayerQuitEvent.class).handler(e -> this.players.put(e.getPlayer().getUniqueId(), new PaperGamePlayer(e.getPlayer())));
        final var worldLoad = Events.subscribe(WorldLoadEvent.class).handler(e -> {
            final GameWorld world = new PaperGameWorld(this, e.getWorld());
            this.worldsById.put(world.id(), world);
            this.worldsByUuid.put(world.uuid(), world);
        });
        final var worldUnload = Events.subscribe(WorldUnloadEvent.class).handler(e -> {
            this.worldsById.remove(e.getWorld().getName());
            this.worldsByUuid.remove(e.getWorld().getUID());
        });

        this.subscriptions.add(join);
        this.subscriptions.add(quit);
        this.subscriptions.add(worldLoad);
        this.subscriptions.add(worldUnload);
    }

    @Override
    public void disable() {
        for (final Subscription subscription : this.subscriptions) {
            subscription.unregister();
        }

        this.subscriptions.clear();
    }

    public static class Builder implements GameServer.Builder {

        private final @NonNull String id;
        private final @NonNull Set<Subscription> subscriptions;

        public Builder(final @NonNull String id) {
            this.id = id;
            this.subscriptions = new HashSet<>();
        }

        public @NonNull Builder subscription(final @NonNull Subscription subscription) {
            this.subscriptions.add(subscription);
            return this;
        }

        @Override
        public @NonNull String id() {
            return this.id;
        }

        @Override
        public @NonNull GameServer build() {
            return new PaperGameServer(this.id, this.subscriptions);
        }
    }

}
