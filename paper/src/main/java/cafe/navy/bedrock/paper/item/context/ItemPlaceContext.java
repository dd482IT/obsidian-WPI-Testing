package cafe.navy.bedrock.paper.item.context;

import org.bukkit.Location;
import org.bukkit.event.player.PlayerInteractEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ItemPlaceContext extends ItemContext {

    private final @NonNull PlayerInteractEvent event;
    private final @NonNull Location location;

    public ItemPlaceContext(final @NonNull PlayerInteractEvent event) {
        this.event = event;
        this.location = this.event.getInteractionPoint();
    }

    public @NonNull Location location() {
        return this.location;
    }

    public @NonNull PlayerInteractEvent cause() {
        return this.event;
    }

}
