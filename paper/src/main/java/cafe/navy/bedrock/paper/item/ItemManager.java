package cafe.navy.bedrock.paper.item;

import broccolai.corn.paper.item.PaperItemBuilder;
import cafe.navy.bedrock.paper.event.ClientEntityInteractEvent;
import cafe.navy.bedrock.paper.item.context.EntityInteractContext;
import cafe.navy.bedrock.paper.item.context.ItemPlaceContext;
import cafe.navy.bedrock.paper.player.PlayerManager;
import org.bukkit.Bukkit;
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
    private void handleItemInteract(final @NonNull PlayerInteractEvent event) {
        final var itemOpt = this.getItemFromItemStack(event.getItem());
        if (itemOpt.isEmpty()) {
            return;
        }

        final var item = itemOpt.get();
        event.setCancelled(true);

        item.type().handleItemPlace(new ItemPlaceContext(event));
    }

    @EventHandler
    private void handleItemInteractEntity(final @NonNull PlayerInteractEntityEvent event) {
        final var itemOpt = this.getItemFromItemStack(event.getPlayer().getInventory().getItemInMainHand());
        if (itemOpt.isEmpty()) {
            return;
        }

        final var item = itemOpt.get();
        event.setCancelled(true);

        item.type().handleEntityInteract(new EntityInteractContext(null, event.getRightClicked(), item, this.playerManager.getPlayer(event.getPlayer().getUniqueId()).get()));
    }

    @EventHandler
    private void handleClientEntityInteract(final @NonNull ClientEntityInteractEvent event) {
        Bukkit.broadcastMessage("hcei");
        final var itemOpt = this.getItemFromItemStack(event.player().getInventory().getItemInMainHand());
        if (itemOpt.isEmpty()) {
            Bukkit.broadcastMessage("no item --null");
            return;
        }

        final var item = itemOpt.get();

        item.type().handleEntityInteract(new EntityInteractContext(event.entity(), null, item, event.target()));
    }

}
