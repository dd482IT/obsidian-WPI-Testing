package cafe.navy.bedrock.paper.item;

import broccolai.corn.paper.item.PaperItemBuilder;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LightningStrike;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

public class ItemManager {

    private final @NonNull NamespacedKey ITEM_UUID = NamespacedKey.fromString("item-uuid");
    private final @NonNull Map<String, ItemType> types;
    private final @NonNull Map<UUID, Item> items;

    public ItemManager() {
        this.types = new HashMap<>();
        this.items = new HashMap<>();
    }

    public @NonNull List<ItemType> types() {
        return List.copyOf(this.types.values());
    }

    public @NonNull Optional<@NonNull ItemType> getType(final @NonNull String id) {
        return Optional.ofNullable(this.types.get(id));
    }

    public void register(final @NonNull ItemType type) {
        this.types.put(type.id(), type);
    }

    public void registerItem(final @NonNull Item item) {
        this.items.put(item.uuid(), item);
    }

    public @NonNull Item createItem(final @NonNull ItemType type) {
        final var item = type.createItem();
        this.registerItem(item);
        return item;
    }

    public @NonNull ItemStack getItemStack(final @NonNull Item item) {
        return PaperItemBuilder.of(item.stack())
                .setData(ITEM_UUID, PersistentDataType.STRING, item.uuid().toString())
                .build();
    }

}
