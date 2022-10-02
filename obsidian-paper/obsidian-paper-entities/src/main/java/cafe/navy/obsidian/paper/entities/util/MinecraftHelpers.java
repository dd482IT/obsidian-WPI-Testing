package cafe.navy.obsidian.paper.entities.util;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.checkerframework.checker.nullness.qual.NonNull;

public class MinecraftHelpers {

    public static @NonNull World firstWorld() {
        return Bukkit.getWorlds().get(0);
    }

    public static @NonNull Level firstLevel() {
        return ((CraftWorld) firstWorld()).getHandle();
    }

    public static int[] generateIds(final int amount) {
        final int[] arr = new int[amount];
        for (int i = 0; i < amount; i++) {
            arr[i] = Entity.nextEntityId();
        }
        return arr;
    }

    public static @NonNull Component debugData(final @NonNull WrappedDataWatcher data) {
        final TextComponent.Builder builder = Component.text();
        builder.append(Component.text("--- Entity data ---"));
        builder.append(Component.newline());
        for (final var item : data.asMap().entrySet()) {
            builder.append(Component.text(item.getKey().intValue() + ", " + item.getValue().getValue().toString()));
            builder.append(Component.newline());
        }
        return builder.build();
    }

}
