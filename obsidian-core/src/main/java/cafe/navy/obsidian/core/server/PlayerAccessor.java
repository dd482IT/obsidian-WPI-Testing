package cafe.navy.obsidian.core.server;

import cafe.navy.obsidian.core.player.GamePlayer;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface PlayerAccessor {

    @NonNull Optional<GamePlayer> player(final @NonNull UUID uuid);

    @NonNull Optional<GamePlayer> player(final @NonNull String name);

    @NonNull Collection<GamePlayer> players();

}
