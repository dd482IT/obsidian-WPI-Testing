package cafe.navy.bedrock.paper.item;

import cafe.navy.bedrock.paper.item.context.EntityInteractContext;
import cafe.navy.bedrock.paper.item.context.HotbarEquipContext;
import cafe.navy.bedrock.paper.item.context.ItemPlaceContext;
import net.minecraft.world.item.context.BlockPlaceContext;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.function.Consumer;

public class ItemTypeBuilder {

    public static @NonNull ItemTypeBuilder of(final @NonNull String id,
                                              final @NonNull ItemStack itemStack) {
        return new ItemTypeBuilder(id, itemStack);
    }

    private final @NonNull String id;
    private @Nullable Consumer<HotbarEquipContext> hotbarEquipHandler;
    private @Nullable Consumer<ItemPlaceContext> blockPlaceHandler;
    private @Nullable Consumer<EntityInteractContext> entityInteractHandler;
    private @NonNull ItemStack baseItem;

    private ItemTypeBuilder(final @NonNull String id,
                            final @NonNull ItemStack baseItem) {
        this.id = id;
        this.baseItem = baseItem;
    }

    public @NonNull ItemTypeBuilder item(final @NonNull ItemStack baseItem) {
        this.baseItem = baseItem;
        return this;
    }

    public @NonNull ItemTypeBuilder onPlace(final @Nullable Consumer<ItemPlaceContext> handler) {
        this.blockPlaceHandler = handler;
        return this;
    }

    public @NonNull ItemTypeBuilder onEntityInteract(final @Nullable Consumer<EntityInteractContext> handler) {
        this.entityInteractHandler = handler;
        return this;
    }

    public @NonNull ItemTypeBuilder onHotbarEquip(final @Nullable Consumer<HotbarEquipContext> handler) {
        this.hotbarEquipHandler = handler;
        return this;
    }

    public @NonNull ItemType build() {
        return new AbstractItemType(this.id, this.baseItem, this.blockPlaceHandler, this.hotbarEquipHandler, this.entityInteractHandler);
    }


}
