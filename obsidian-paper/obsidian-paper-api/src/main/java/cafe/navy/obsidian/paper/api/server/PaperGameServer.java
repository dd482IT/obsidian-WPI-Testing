package cafe.navy.obsidian.paper.api.server;

import cafe.navy.obsidian.core.player.GamePlayer;
import cafe.navy.obsidian.core.server.GameServer;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PaperGameServer implements GameServer {

    private final @NonNull String id;
    private final @NonNull Map<UUID, GamePlayer> players;

    public PaperGameServer(final @NonNull String id) {
        this.id = id;
        this.players = new ConcurrentHashMap<>();
    }

    @Override
    public @NonNull String id() {
        return this.id;
    }

    @Override
    public @NonNull Collection<GamePlayer> players() {
        return this.players.values();
    }

    @Override
    public @NonNull Optional<@NonNull GamePlayer> getPlayer(final @NonNull UUID uuid) {
        return Optional.ofNullable(this.players.get(uuid));
    }

    public static class Builder implements GameServer.Builder {

        private final @NonNull String id;

        public Builder(final @NonNull String id) {
            this.id = id;
        }

        @Override
        public @NonNull String id() {
            return this.id;
        }

        @Override
        public @NonNull GameServer build() {
            return new PaperGameServer(this.id);
        }
    }

}
