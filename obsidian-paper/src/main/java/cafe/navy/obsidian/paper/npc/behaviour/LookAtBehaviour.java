package cafe.navy.obsidian.paper.npc.behaviour;

import cafe.navy.obsidian.paper.BukkitAdapter;
import cafe.navy.obsidian.paper.npc.NPCState;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Collection;

public class LookAtBehaviour implements NPCBehaviour {

    public static @NonNull LookAtBehaviour nearest(final int distance) {
        return new LookAtBehaviour(distance);
    }

    private final int distance;

    private LookAtBehaviour(final int distance) {
        this.distance = distance;
    }

    @Override
    public void tick(final @NonNull NPCState state) {
        final Location location = BukkitAdapter.toLocation(state.position(), state.world());
        final Collection<Player> nearby = location.getNearbyPlayers(this.distance);
        final Player player = nearby.stream().findFirst().orElse(null);

        if (player == null) {
            state.players().forEach(client -> {
                state.renderer().updateRotation(client, 0, 0);
            });
            return;
        }

        final double xDiff = player.getEyeLocation().getX() - state.position().x();
        final double yDiff = player.getEyeLocation().getY() - (state.position().y() + state.renderer().visualHeight());
        final double zDiff = player.getEyeLocation().getZ() - state.position().z();

        double dXZ = Math.sqrt(xDiff * xDiff + zDiff * zDiff);
        double dY = Math.sqrt(dXZ * dXZ + yDiff * yDiff);

        double newYaw = Math.acos(xDiff / dXZ) * 180 / Math.PI;
        double newPitch = Math.acos(yDiff / dY) * 180 / Math.PI - 90;
        if (zDiff < 0.0)
            newYaw = newYaw + Math.abs(180 - newYaw) * 2;
        newYaw = (newYaw - 90);

        final float yaw = (float) newYaw;
        final float pitch = (float) newPitch;

        state.players().forEach(client -> {
            state.renderer().updateRotation(client, yaw, pitch);
        });
    }
}
