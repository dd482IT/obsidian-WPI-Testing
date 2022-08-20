package cafe.navy.bedrock.paper.realm;

import cafe.navy.bedrock.paper.Server;
import cafe.navy.bedrock.paper.entity.ClientEntity;
import cafe.navy.bedrock.paper.player.PlayerTarget;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;

public class WorldRealm extends Realm implements Listener {

    private final @NonNull Server server;
    private final @NonNull World world;
    private @MonotonicNonNull BukkitTask renderDistanceTask;

    public WorldRealm(final @NonNull Server server,
                      final @NonNull World world) {
        super(world.getUID());
        this.server = server;
        this.world = world;
    }

    @Override
    public void enable() {
        this.renderDistanceTask = this.server.registerTimedTask(() -> {
            for (final ClientEntity entity : entityList()) {
                for (final Player player : entity.location().getNearbyPlayers(entity.viewRadius())) {
                    this.server.players().getPlayer(player.getUniqueId()).ifPresent(target -> {
                        final Location targetLoc = target.location();
                        final Location entityLoc = entity.location();
                        final double viewRadius = entity.viewRadius() * entity.viewRadius();
                        final double dist = Math.abs(targetLoc.distanceSquared(entityLoc));
                        if (dist >= viewRadius && target.viewing(entity)) {
                            target.add(entity);
                        } else if (dist <= viewRadius && !target.viewing(entity)) {
                            target.remove(entity);
                        }
                    });
                }
            }
        }, 20);
    }

    @Override
    public void disable() {
        if (this.renderDistanceTask != null && !this.renderDistanceTask.isCancelled()) {
            this.renderDistanceTask.cancel();
        }
        this.renderDistanceTask = null;
    }

    @Override
    public void add(@NonNull PlayerTarget target) {

    }

    @Override
    public void remove(@NonNull PlayerTarget target) {

    }

    @Override
    public void add(@NonNull ClientEntity entity) {

    }

    @Override
    public void remove(@NonNull ClientEntity entity) {

    }
}
