package cafe.navy.bedrock.paper.item;

import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public class Item {

    private final @NonNull ItemType type;
    private final @NonNull ItemStack stack;
    private final @NonNull ItemData data;

    public Item(final @NonNull ItemType type,
                final @NonNull ItemStack stack,
                final @NonNull ItemData data) {
        this.type = type;
        this.stack = stack;
        this.data = data;
    }

    public @NonNull ItemType type() {
        return this.type;
    }

    public @NonNull ItemStack stack() {
        return this.stack;
    }

    public @NonNull ItemData data() {
        return this.data;
    }

    public @NonNull UUID uuid() {
        return this.data.uuid();
    }

}
