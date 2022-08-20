package cafe.navy.bedrock.paper.realm;

import cafe.navy.bedrock.paper.entity.ClientEntity;
import cafe.navy.bedrock.paper.player.PlayerTarget;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * {@code Realm} represents a distinct physical area that contains a set of players and entities.
 * <p>
 * A {@code Realm} could implement any type of physical area:
 * <ul>
 *     <li>a world on a Minecraft Server</li>
 *     <li>a pocket dimension visible to only one player</li>
 *     <li>a temporary lobby for a game or event</li>
 *     <li>an arena map</li>
 * </ul>
 */
public abstract class Realm {

    private final @NonNull Map<UUID, PlayerTarget> players;
    private final @NonNull Map<UUID, ClientEntity> entities;
    private final @NonNull UUID uuid;

    public Realm(final @NonNull UUID uuid) {
        this.uuid = uuid;
        this.players = new HashMap<>();
        this.entities = new HashMap<>();
    }

    public @NonNull UUID uuid() {
        return this.uuid;
    }

    public @NonNull List<ClientEntity> entityList() {
        return List.copyOf(this.entities.values());
    }

    public @NonNull List<PlayerTarget> playerList() {
        return List.copyOf(this.players.values());
    }

    public abstract void enable();

    public abstract void disable();

    public abstract void add(final @NonNull PlayerTarget target);

    public abstract void remove(final @NonNull PlayerTarget target);

    public abstract void add(final @NonNull ClientEntity entity);

    public abstract void remove(final @NonNull ClientEntity entity);

}
