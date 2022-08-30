package cafe.navy.bedrock.paper.item;

import broccolai.corn.paper.item.PaperItemBuilder;
import cafe.navy.bedrock.paper.event.ClientEntityInteractEvent;
import cafe.navy.bedrock.paper.item.context.ItemInteractEvent;
import cafe.navy.bedrock.paper.player.PlayerManager;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.*;

public class ItemManager implements Listener {

    private final @NonNull JavaPlugin plugin;
    private final @NonNull NamespacedKey ITEM_UUID = NamespacedKey.fromString("item-uuid");
    private final @NonNull PlayerManager playerManager;
    private final @NonNull Map<String, ItemType> types;
    private final @NonNull Map<UUID, Item> items;

    public ItemManager(final @NonNull JavaPlugin plugin,
                       final @NonNull PlayerManager playerManager) {
        this.plugin = plugin;
        this.types = new HashMap<>();
        this.items = new HashMap<>();
        this.playerManager = playerManager;
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

    public @NonNull Optional<@NonNull Item> getItem(final @NonNull UUID uuid) {
        return Optional.ofNullable(this.items.get(uuid));
    }

    public @NonNull Optional<@NonNull Item> getItemFromItemStack(final @NonNull ItemStack stack) {
        if (stack == null) {
            return Optional.empty();
        }

        if (stack.getType().isAir() || stack.getType().isEmpty()) {
            return Optional.empty();
        }

        if (!stack.hasItemMeta()) {
            return Optional.empty();
        }

        final var meta = stack.getItemMeta();
        if (meta.getPersistentDataContainer().has(ITEM_UUID)) {
            final UUID uuid = UUID.fromString(meta.getPersistentDataContainer().get(ITEM_UUID, PersistentDataType.STRING));
            return this.getItem(uuid);
        }

        return Optional.empty();
    }

    public void enable() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    public void disable() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    private void handleItemInteractEntity(final @NonNull PlayerInteractEntityEvent event) {
        final Optional<Item> itemOpt = this.getItemFromItemStack(event.getPlayer().getInventory().getItemInMainHand());
        if (itemOpt.isEmpty()) {
            return;
        }
        event.setCancelled(true);

        final Item item = itemOpt.get();
        final ItemInteractEvent itemEvent = new ItemInteractEvent(
                item,
                event.getPlayer(),
                ItemInteractEvent.InteractType.INTERACT,
                null,
                event.getRightClicked()
        );
        item.type().handleEvent(itemEvent);
    }

    @EventHandler
    private void handleClientEntityInteract(final @NonNull ClientEntityInteractEvent event) {
        final Optional<Item> itemOpt = this.getItemFromItemStack(event.player().getInventory().getItemInMainHand());
        if (itemOpt.isEmpty()) {
            return;
        }

        final Item item = itemOpt.get();
        final EnumWrappers.EntityUseAction action = event.action();


        final ItemInteractEvent itemEvent = new ItemInteractEvent(
                item,
                event.player(),
                action == EnumWrappers.EntityUseAction.ATTACK ? ItemInteractEvent.InteractType.ATTACK : ItemInteractEvent.InteractType.INTERACT,
                event.entity(),
                null
        );

        item.type().handleEvent(itemEvent);
    }

    @EventHandler
    private void handleItemInteract(final @NonNull PlayerInteractEvent event) {
        final Optional<Item> itemOpt = this.getItemFromItemStack(event.getPlayer().getInventory().getItemInMainHand());
        if (itemOpt.isEmpty()) {
            return;
        }

        final Item item = itemOpt.get();

        final ItemInteractEvent itemEvent = new ItemInteractEvent(
                item,
                event.getPlayer(),
                ItemInteractEvent.InteractType.INTERACT,
                null,
                null
        );

        item.type().handleEvent(itemEvent);
    }

}
