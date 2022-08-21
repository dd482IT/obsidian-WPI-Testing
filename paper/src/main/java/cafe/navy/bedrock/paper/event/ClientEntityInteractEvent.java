package cafe.navy.bedrock.paper.event;

import cafe.navy.bedrock.paper.entity.ClientEntity;
import cafe.navy.bedrock.paper.entity.ClientEntityManager;
import cafe.navy.bedrock.paper.player.PlayerTarget;
import cafe.navy.bedrock.paper.target.EntityTarget;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public class ClientEntityInteractEvent extends Event {

    private static final @NonNull HandlerList HANDLER_LIST = new HandlerList();

    private final @NonNull ClientEntity entity;
    private final @NonNull PlayerTarget target;
    private final @NonNull Player player;

    public ClientEntityInteractEvent(final @NonNull ClientEntity entity,
                                     final @NonNull PlayerTarget target,
                                     final @NonNull Player player) {
        this.entity = entity;
        this.target = target;
        this.player = player;
    }

    public @NonNull Player player() {
        return this.player;
    }

    public @NonNull ClientEntity entity() {
        return this.entity;
    }

    public @NonNull PlayerTarget target() {
        return this.target;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static @NonNull HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
