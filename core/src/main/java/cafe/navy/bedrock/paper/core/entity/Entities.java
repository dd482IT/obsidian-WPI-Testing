package cafe.navy.bedrock.paper.core.entity;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Slime;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.CraftServer;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface Entities {

    static @NonNull ClientEntity createHitbox(final @NonNull Location location,
                                              final int height) {
        final Slime entity = new Slime(
                EntityType.SLIME, ((CraftWorld) location.getWorld()).getHandle()
        );
        entity.setSize(height, true);
        entity.setInvisible(true);

        entity.teleportTo(location.getX(), location.getY(), location.getZ());
        return new NMSClientEntity(entity);
    }

}
