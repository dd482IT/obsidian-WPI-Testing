package cafe.navy.bedrock.paper.item;

import cafe.navy.bedrock.paper.item.context.ItemEvent;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class AbstractItemType implements ItemType {

    private final @NonNull String id;
    private final @NonNull ItemStack baseItem;
    private final @NonNull Map<Class<? extends ItemEvent>, List<Consumer<? extends ItemEvent>>> eventHandlers;

    public AbstractItemType(final @NonNull String id,
                            final @NonNull ItemStack baseItem,
                            final @NonNull Map<Class<? extends ItemEvent>, List<Consumer<? extends ItemEvent>>> eventHandlers
    ) {
        this.id = id;
        this.baseItem = baseItem;
        this.eventHandlers = eventHandlers;
    }

    public @NonNull String id() {
        return this.id;
    }

    @Override
    public void handleEvent(final @NonNull ItemEvent event) {
        if (!this.eventHandlers.containsKey(event.getClass())) {
            return;
        }

        for (final Consumer<? extends ItemEvent> handler : this.eventHandlers.get(event.getClass())) {
            ((Consumer<ItemEvent>) handler).accept(event);
        }
    }

    @Override
    public @NonNull Item createItem() {
        return new Item(
                this,
                this.baseItem,
                new ItemData(UUID.randomUUID(), Instant.now(), null)
        );
    }

}
