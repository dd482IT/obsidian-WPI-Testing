package cafe.navy.obsidian.core.server;

import cafe.navy.obsidian.core.player.GamePlayer;
import cafe.navy.obsidian.core.util.Position;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.UUID;

public interface GameWorld {

    @NonNull String id();

    @NonNull UUID uuid();

    @NonNull Collection<GamePlayer> players();

    @NonNull Collection<GamePlayer> playersNearby(final @NonNull Position position,
                                                  final double radius);

}
