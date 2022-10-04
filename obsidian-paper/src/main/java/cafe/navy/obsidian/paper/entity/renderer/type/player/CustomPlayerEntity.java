package cafe.navy.obsidian.paper.entity.renderer.type.player;

import cafe.navy.obsidian.core.util.Position;
import cafe.navy.obsidian.paper.MinecraftHelpers;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * {@code CustomPlayerEntity} is a subclass of the {@link Player} for easier use with Obsidian.
 */
public class CustomPlayerEntity extends Player {

    /**
     * Constructs {@code CustomPlayerEntity}.
     *
     * @param position the player position
     * @param profile  the player profile
     */
    public CustomPlayerEntity(final @NonNull Position position,
                              final @NonNull GameProfile profile) {
        super(MinecraftHelpers.firstLevel(), new BlockPos(position.x(), position.y(), position.z()), position.yaw(), profile, null);
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
