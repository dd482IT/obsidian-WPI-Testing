package cafe.navy.bedrock.paper.realm;

import cafe.navy.bedrock.paper.Server;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.checkerframework.checker.nullness.qual.NonNull;

public class WorldRealm extends Realm implements Listener {

    private final @NonNull Server server;
    private final @NonNull World world;

    public WorldRealm(final @NonNull Server server,
                      final @NonNull World world) {
        super(server, world.getUID(), world.getName());
        this.server = server;
        this.world = world;
    }

    @Override
    protected void onEnable() {
        for (final Player player : this.world.getPlayers()) {
            this.add(this.server.players().getPlayer(player.getUniqueId()).get());
        }

    }

    @Override
    protected void onDisable() {
    }
}
