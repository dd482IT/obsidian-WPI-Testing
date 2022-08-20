package cafe.navy.bedrock.paper.player;

import cafe.navy.bedrock.paper.entity.ClientEntity;
import cafe.navy.bedrock.paper.message.Message;
import cafe.navy.bedrock.paper.realm.Realm;
import cafe.navy.bedrock.paper.target.EntityTarget;
import cafe.navy.bedrock.paper.target.MessageTarget;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerTarget implements EntityTarget, MessageTarget {

    private static final int RENDER_DISTANCE_UPDATE_TICKS = 0;

    private final @NonNull JavaPlugin plugin;
    private final @NonNull Player player;
    private final @NonNull Map<UUID, ClientEntity> entities;
    private @MonotonicNonNull BukkitTask renderDistanceTask;

    public PlayerTarget(final @NonNull JavaPlugin plugin,
                        final @NonNull Player player) {
        this.player = player;
        this.plugin = plugin;
        this.entities = new HashMap<>();
    }

    @Override
    public @NonNull UUID uuid() {
        return this.player.getUniqueId();
    }

    @Override
    public @NonNull String name() {
        return this.player.getName();
    }

    @Override
    public void add(final @NonNull ClientEntity entity) {
        this.entities.put(entity.uuid(), entity);
    }

    @Override
    public void remove(final @NonNull ClientEntity entity) {
        this.entities.remove(entity.uuid());
    }

    @Override
    public boolean viewing(final @NonNull ClientEntity entity) {
        return this.entities.containsKey(entity.uuid());
    }

    @Override
    public @NonNull Location location() {
        return this.player.getLocation();
    }

    @Override
    public void sendMessage(final @NonNull Message message) {

    }

    public void handleRealmChange(final @NonNull Realm prev,
                                  final @NonNull Realm next) {
        for (final ClientEntity entity : List.copyOf(this.entities.values())) {
            if (entity.location().getWorld().getUID().equals(prev.uuid())) {
                this.remove(entity);
            }
        }
    }

    private @NonNull ServerPlayer getServerPlayer() {
        return ((CraftPlayer) this.player).getHandle();
    }
}
