package cafe.navy.bedrock.paper.item.context;

import cafe.navy.bedrock.paper.item.Item;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class ItemEvent {

    protected final @NonNull Item item;
    protected final @NonNull Player player;

    public ItemEvent(final @NonNull Item item,
                     final @NonNull Player player) {
        this.item = item;
        this.player = player;
    }

    public @NonNull Item item() {
        return this.item;
    }

    public @NonNull Player player() {
        return this.player;
    }

    public @NonNull String getName() {
        return this.getClass().getSimpleName();
    }

}
