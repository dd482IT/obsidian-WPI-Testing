package cafe.navy.bedrock.paper.item;

import cafe.navy.bedrock.paper.item.context.EntityInteractContext;
import cafe.navy.bedrock.paper.item.context.HotbarEquipContext;
import cafe.navy.bedrock.paper.item.context.ItemPlaceContext;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.time.Instant;
import java.util.UUID;
import java.util.function.Consumer;

public class AbstractItemType implements ItemType {

    private final @NonNull String id;
    private final @NonNull ItemStack baseItem;
    private final @Nullable Consumer<ItemPlaceContext> placeHandler;
    private final @Nullable Consumer<HotbarEquipContext> hotbarEquipHandler;
    private final @Nullable Consumer<EntityInteractContext> entityInteractHandler;

    public AbstractItemType(final @NonNull String id,
                            final @NonNull ItemStack baseItem,
                            final @Nullable Consumer<ItemPlaceContext> placeHandler,
                            final @Nullable Consumer<HotbarEquipContext> hotbarEquipHandler,
                            final @Nullable Consumer<EntityInteractContext> entityInteractHandler) {
        this.id = id;
        this.baseItem = baseItem;
        this.placeHandler = placeHandler;
        this.hotbarEquipHandler = hotbarEquipHandler;
        this.entityInteractHandler = entityInteractHandler;
    }

    @Override
    public void handleItemPlace(final @NonNull ItemPlaceContext context) {
        if (this.placeHandler != null) {
            this.placeHandler.accept(context);
        }
    }
    @Override
    public void handleEntityInteract(final @NonNull EntityInteractContext context) {
        if (this.entityInteractHandler != null) {
            this.entityInteractHandler.accept(context);
        }
    }


    @Override
    public void handleHotbarEquip(final @NonNull HotbarEquipContext context) {
        if (this.hotbarEquipHandler != null) {
            this.hotbarEquipHandler.accept(context);
        }
    }

    public @NonNull String id() {
        return this.id;
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
