package cafe.navy.obsidian.paper.api.server;

import cafe.navy.obsidian.core.player.GamePlayer;
import cafe.navy.obsidian.core.server.GameWorld;
import cafe.navy.obsidian.core.server.PlayerAccessor;
import cafe.navy.obsidian.core.util.Position;
import org.bukkit.World;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class PaperGameWorld implements GameWorld {

    private final @NonNull PlayerAccessor playerAccessor;
    private final @NonNull World world;

    public PaperGameWorld(final @NonNull PlayerAccessor playerAccessor,
                          final @NonNull World world) {
        this.playerAccessor = playerAccessor;
        this.world = world;
    }

    @Override
    public @NonNull String id() {
        return this.world.getName();
    }

    @Override
    public @NonNull UUID uuid() {
        return this.world.getUID();
    }

    @Override
    public @NonNull Collection<GamePlayer> players() {
        return this.world.getPlayers()
                .stream()
                .map(p -> this.playerAccessor.player(p.getUniqueId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public @NonNull Collection<GamePlayer> playersNearby(@NonNull Position position, double radius) {
        return null;
    }
}
