package cafe.navy.obsidian.core.server;

import cafe.navy.obsidian.core.player.GamePlayer;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface GameServer {

    @NonNull String id();

    @NonNull Collection<GamePlayer> players();

    @NonNull Optional<@NonNull GamePlayer> getPlayer(final @NonNull UUID uuid);

    interface Builder {

        @NonNull String id();

        @NonNull GameServer build();

    }

}
