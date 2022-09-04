package cafe.navy.bedrock.paper.core.event;

import cafe.navy.bedrock.paper.core.entity.ClientEntity;
import cafe.navy.bedrock.paper.core.player.PlayerTarget;
import cafe.navy.bedrock.paper.core.util.InteractHand;
import com.comphenix.protocol.wrappers.EnumWrappers;
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
    private final @NonNull InteractHand hand;
    private final EnumWrappers.@NonNull EntityUseAction action;

    public ClientEntityInteractEvent(final @NonNull ClientEntity entity,
                                     final @NonNull PlayerTarget target,
                                     final @NonNull Player player,
                                     final @NonNull InteractHand hand,
                                     final EnumWrappers.@NonNull EntityUseAction action) {
        this.entity = entity;
        this.target = target;
        this.hand = hand;
        this.player = player;
        this.action = action;
    }

    public EnumWrappers.@NonNull EntityUseAction action() {
        return this.action;
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

    public @NonNull InteractHand hand() {
        return this.hand;
    }

    public static @NonNull HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
