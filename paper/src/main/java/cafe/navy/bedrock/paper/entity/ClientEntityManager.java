package cafe.navy.bedrock.paper.entity;

import cafe.navy.bedrock.paper.Server;
import cafe.navy.bedrock.paper.event.ClientEntityInteractEvent;
import cafe.navy.bedrock.paper.exception.ClientEntityException;
import cafe.navy.bedrock.paper.player.PlayerTarget;
import cafe.navy.bedrock.paper.target.EntityTarget;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

public class ClientEntityManager {

    private final @NonNull Server server;
    private final @NonNull Map<UUID, ClientEntity> entities;
    private final @NonNull List<EntityTarget> entityTargets;
    private @MonotonicNonNull BukkitTask renderDistanceTask;

    public ClientEntityManager(final @NonNull Server server) {
        this.server = server;
        this.entities = new HashMap<>();
        this.entityTargets = new ArrayList<>();
    }

    public void add(final @NonNull EntityTarget target) {
        this.entityTargets.add(target);
    }

    public void remove(final @NonNull EntityTarget target) {
        this.entityTargets.remove(target);
    }

    public void add(final @NonNull ClientEntity entity) {
        this.entities.put(entity.uuid(), entity);
    }

    public void remove(final @NonNull ClientEntity entity) {
        this.entities.remove(entity.uuid());
    }

    public void enable() {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(this.server.plugin(), PacketType.Play.Client.ENTITY_ACTION, PacketType.Play.Client.USE_ENTITY) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                final Player player = event.getPlayer();
                final PlayerTarget target = server.players().getPlayer(player.getUniqueId()).get();
                if (event.getPacketType() == PacketType.Play.Client.USE_ENTITY) {
                    final int id = event.getPacket().getIntegers().read(0);
                    for (final ClientEntity entity : entities.values()) {
                        if (entity.checkEntityId(id)) {
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    server.plugin().getServer().getPluginManager().callEvent(new ClientEntityInteractEvent(entity, target, player));
                                }
                            }.runTask(server.plugin());
                            break;
                        }
                    }
                }
            }
        });

        this.renderDistanceTask = this.server.registerTimedTask(() -> {
            for (final ClientEntity entity : entities.values()) {
                for (final Player player : entity.location().getNearbyPlayers(entity.viewRadius())) {
                    this.server.players().getPlayer(player.getUniqueId()).ifPresent(target -> {
                        final Location targetLoc = target.location();
                        final Location entityLoc = entity.location();
                        final double viewRadius = entity.viewRadius() * entity.viewRadius();
                        final double dist = Math.abs(targetLoc.distanceSquared(entityLoc));
                        if (dist >= viewRadius && target.viewing(entity)) {
                            try {
                                entity.remove(target);
                            } catch (ClientEntityException e) {
                                throw new RuntimeException(e);
                            }
                        } else if (dist <= viewRadius && !target.viewing(entity)) {
                            try {
                                entity.add(target);
                            } catch (ClientEntityException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });
                }
            }
        }, 20);
    }

    public void disable() {
        if (this.renderDistanceTask != null && !this.renderDistanceTask.isCancelled()) {
            this.renderDistanceTask.cancel();
        }
    }

}
