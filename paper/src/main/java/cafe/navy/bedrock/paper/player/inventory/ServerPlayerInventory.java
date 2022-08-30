package cafe.navy.bedrock.paper.player.inventory;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Optional;

public class ServerPlayerInventory extends PlayerInventory {

    private final @NonNull Player player;

    public ServerPlayerInventory(final @NonNull Player player) {
        this.player = player;
        this.player.getInventory().getSize();
    }

    @Override
    public int getSize() {
        return this.player.getInventory().getSize();
    }

    @Override
    public @NonNull Optional<@NonNull ItemStack> getItem(int index) {
        return Optional.ofNullable(this.player.getInventory().getItem(index));
    }
}
