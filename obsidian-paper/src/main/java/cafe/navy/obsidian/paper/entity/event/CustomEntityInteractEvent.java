package cafe.navy.obsidian.paper.entity.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class CustomEntityInteractEvent extends Event {


    private final @NonNull GameEntity entity;
    private final @NonNull Player player;

    public CustomEntityInteractEvent(final @NonNull GameEntity entity,
                                     final @NonNull Player player) {
        this.entity = entity;
        this.player = player;
    }

    public @NonNull GameEntity entity() {
        return this.entity;
    }

    public @NonNull Player player() {
        return this.player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return null;
    }
}
