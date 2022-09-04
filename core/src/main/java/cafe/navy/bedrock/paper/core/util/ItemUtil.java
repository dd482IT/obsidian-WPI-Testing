package cafe.navy.bedrock.paper.core.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ItemUtil {

    public static void decrementOrRemoveHeld(final @NonNull Player player) {
        final ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (ItemUtil.isNothing(itemStack)) {
            return;
        }

        if (itemStack.getAmount() > 1) {
            itemStack.setAmount(itemStack.getAmount() - 1);
        } else {
            player.getInventory().setItemInMainHand(null);
        }
    }

    public static boolean isNothing(final @NonNull ItemStack itemStack) {
        if (itemStack == null) {
            return true;
        }

        return itemStack.getType().isEmpty();
    }

}
