package cafe.navy.bedrock.paper.player;

import net.minecraft.network.protocol.game.ServerPacketListener;
import net.minecraft.server.level.ServerPlayer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Optional;
import java.util.UUID;

public class PlayerUtil {

    public static @NonNull Optional<@NonNull Player> getPlayer(final @NonNull UUID uuid) {
        return Optional.ofNullable(Bukkit.getPlayer(uuid));
    }

    public static @NonNull Optional<@NonNull ServerPlayer> getServerPlayer(final @NonNull UUID uuid) {
        final var bp = Bukkit.getPlayer(uuid);
        if (bp == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(((CraftPlayer) bp).getHandle());
    }

}
