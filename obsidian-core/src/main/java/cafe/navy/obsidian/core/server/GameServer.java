package cafe.navy.obsidian.core.server;

import cafe.navy.obsidian.core.player.GamePlayer;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface GameServer extends PlayerAccessor {

    @NonNull String id();

    @NonNull Collection<GameWorld> worlds();

    @NonNull Optional<GameWorld> world(final @NonNull String name);

    @NonNull Optional<GameWorld> world(final @NonNull UUID uuid);

    void enable();

    void disable();

    interface Builder {

        @NonNull String id();

        @NonNull GameServer build();

    }

}
