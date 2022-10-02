package cafe.navy.obsidian.paper.entities.entity.type.player;

import cafe.navy.obsidian.core.util.Position;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.checkerframework.checker.nullness.qual.NonNull;

public class CustomPlayer extends Player {

    public CustomPlayer(final @NonNull World world,
                        final @NonNull Position position,
                        final @NonNull GameProfile profile) {
        super(((CraftWorld) world).getHandle(), new BlockPos(position.x(), position.y(), position.z()), position.yaw(), profile, null);
        this.setCustomNameVisible(false);
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }

    @Override
    public boolean shouldShowName() {
        return false;
    }
}
