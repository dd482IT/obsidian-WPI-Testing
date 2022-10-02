package cafe.navy.obsidian.paper.entities.entity.event;

import cafe.navy.obsidian.paper.entities.entity.CustomEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class CustomEntityInteractEvent extends Event {


    private final @NonNull CustomEntity entity;
    private final @NonNull Player player;

    public CustomEntityInteractEvent(final @NonNull CustomEntity entity,
                                     final @NonNull Player player) {
        this.entity = entity;
        this.player = player;
    }

    public @NonNull CustomEntity entity() {
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
