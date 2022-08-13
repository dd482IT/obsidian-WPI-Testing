package cafe.navy.bedrock.paper.hologram;

import cafe.navy.bedrock.paper.entity.ClientEntity;
import cafe.navy.bedrock.paper.message.Message;
import io.papermc.paper.adventure.PaperAdventure;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.network.protocol.game.ClientboundSetEntityDataPacket;
import net.minecraft.network.protocol.game.ClientboundTeleportEntityPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.level.Level;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Hologram} is a {@link ClientEntity} that shows lines of text, like a "hologram".
 */
public class Hologram implements ClientEntity {

    private final @NonNull List<Message> lines;
    private final @NonNull List<ArmorStand> stands;
    private final double lineHeight = 0.5;

    /**
     * Constructs {@code Hologram}.
     *
     * @param location the hologram's location
     * @param lines the hologram's lines
     */
    public Hologram(final @NonNull Location location,
                    final @NonNull List<Message> lines) {
        this.lines = lines;
        this.stands = new ArrayList<>();

        final Level level = ((CraftWorld) location.getWorld()).getHandle().getLevel();
        for (int i = 0; i < this.lines.size(); i++) {
            final ArmorStand stand = new ArmorStand(
                    level,
                    location.getX(),
                    location.getY() + (i * this.lineHeight),
                    location.getZ()
            );
            stand.setCustomNameVisible(true);
            stand.setCustomName(PaperAdventure.asVanilla(this.lines.get(i).asComponent()));
            stand.setInvisible(true);
            stand.setNoGravity(true);
            stand.setMarker(true);
            this.stands.add(stand);
        }
    }

    public void show(final @NonNull Player player) {
        final ServerPlayer sPlayer = ((CraftPlayer) player).getHandle();
        for (final ArmorStand stand : this.stands) {
            sPlayer.connection.send(new ClientboundAddEntityPacket(stand));
            sPlayer.connection.send(new ClientboundSetEntityDataPacket(stand.getId(), stand.getEntityData(), true));
        }
    }

    public void hide(final @NonNull Player player) {
        final ServerPlayer sPlayer = ((CraftPlayer) player).getHandle();
        for (final ArmorStand stand : this.stands) {
            sPlayer.connection.send(new ClientboundRemoveEntitiesPacket(stand.getId()));
        }
    }

    /**
     * Moves the hologram for a player.
     *
     * @param player the player
     * @param location the location
     */
    public void moveTo(final @NonNull Player player,
                       final @NonNull Location location) {
        final ServerPlayer sPlayer = ((CraftPlayer) player).getHandle();

        for (int i = 0; i < this.lines.size(); i++) {
            final ArmorStand stand = this.stands.get(i);
            stand.moveTo(location.getX(), location.getY(), location.getZ());
            sPlayer.connection.send(new ClientboundTeleportEntityPacket(stand));
        }
    }
}
