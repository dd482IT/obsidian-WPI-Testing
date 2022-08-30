package cafe.navy.bedrock.paper.item;

import cafe.navy.bedrock.paper.item.context.*;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class ItemTypeBuilder {

    public static @NonNull ItemTypeBuilder of(final @NonNull String id,
                                              final @NonNull ItemStack itemStack) {
        return new ItemTypeBuilder(id, itemStack);
    }

    private final @NonNull String id;
    private final @NonNull Map<Class<? extends ItemEvent>, List<Consumer<? extends ItemEvent>>> handlers;
    private @NonNull ItemStack baseItem;

    private ItemTypeBuilder(final @NonNull String id,
                            final @NonNull ItemStack baseItem) {
        this.id = id;
        this.baseItem = baseItem;
        this.handlers = new HashMap<>();
    }

    public @NonNull ItemTypeBuilder item(final @NonNull ItemStack baseItem) {
        this.baseItem = baseItem;
        return this;
    }

    public <T extends ItemEvent> @NonNull ItemTypeBuilder on(final @NonNull Class<T> eventType,
                                       final @NonNull Consumer<T> handler) {
        if (!this.handlers.containsKey(eventType)) {
            this.handlers.put(eventType, new ArrayList<>());
        }
        this.handlers.get(eventType).add(handler);
        return this;
    }


    public @NonNull ItemType build() {
        return new AbstractItemType(
                this.id,
                this.baseItem,
                this.handlers
        );
    }


}
