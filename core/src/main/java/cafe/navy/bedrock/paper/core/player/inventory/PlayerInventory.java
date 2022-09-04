package cafe.navy.bedrock.paper.core.player.inventory;

import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Optional;

public abstract class PlayerInventory {

    public abstract int getSize();

    public abstract @NonNull Optional<@NonNull ItemStack> getItem(final int index);

}
