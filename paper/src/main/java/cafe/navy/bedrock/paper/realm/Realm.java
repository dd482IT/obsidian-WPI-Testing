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
    private final @NonNull String name;

    public Realm(final @NonNull UUID uuid,
                 final @NonNull String name) {
        this.uuid = uuid;
        this.name = name;
        this.players = new HashMap<>();
        this.entities = new HashMap<>();
    }

    public @NonNull String name() {
        return this.name;
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

    public void add(final @NonNull PlayerTarget target) {
        this.players.put(target.uuid(), target);
    }

    public void remove(final @NonNull PlayerTarget target) {
        this.players.remove(target.uuid());
    }

    public void add(final @NonNull ClientEntity entity) {
        this.entities.put(entity.uuid(), entity);
    }

    public void remove(final @NonNull ClientEntity entity) {
        this.entities.remove(entity.uuid());
    }

}
