package cafe.navy.bedrock.paper.realm;

import cafe.navy.bedrock.paper.Server;
import cafe.navy.bedrock.paper.entity.ClientEntity;
import cafe.navy.bedrock.paper.entity.ClientEntityManager;
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

    private final @NonNull Server server;
    private final @NonNull Map<UUID, PlayerTarget> players;
    private final @NonNull Map<UUID, ClientEntity> entities;
    private final @NonNull UUID uuid;
    private final @NonNull String name;
    private final @NonNull ClientEntityManager entityManager;

    public Realm(final @NonNull Server server,
                 final @NonNull UUID uuid,
                 final @NonNull String name) {
        this.server = server;
        this.uuid = uuid;
        this.name = name;
        this.players = new HashMap<>();
        this.entities = new HashMap<>();
        this.entityManager = new ClientEntityManager(this.server);
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

    public void enable() {
        this.entityManager.enable();
        this.onEnable();
    }

    public void disable() {
        this.entityManager.disable();
        this.onDisable();
    }

    protected abstract void onEnable();

    protected abstract void onDisable();

    public void add(final @NonNull PlayerTarget target) {
        this.players.put(target.uuid(), target);
        this.entityManager.add(target);
    }

    public void remove(final @NonNull PlayerTarget target) {
        this.players.remove(target.uuid());
        this.entityManager.add(target);
    }

    public void add(final @NonNull ClientEntity entity) {
        this.entities.put(entity.uuid(), entity);
        this.entityManager.add(entity);
    }

    public void remove(final @NonNull ClientEntity entity) {
        this.entities.remove(entity.uuid());
        this.entityManager.remove(entity);
    }

}
