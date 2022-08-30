package cafe.navy.bedrock.paper.item.context;

import cafe.navy.bedrock.paper.entity.ClientEntity;
import cafe.navy.bedrock.paper.item.Item;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Optional;

public class ItemInteractEvent extends ItemEvent {

    protected final @Nullable ClientEntity clientEntity;
    protected final @Nullable Entity bukkitEntity;
    protected final @NonNull InteractType type;

    public ItemInteractEvent(final @NonNull Item item,
                             final @NonNull Player player,
                             final @NonNull InteractType type,
                             final @Nullable ClientEntity clientEntity,
                             final @Nullable Entity bukkitEntity) {
        super(item, player);
        this.type = type;
        this.clientEntity = clientEntity;
        this.bukkitEntity = bukkitEntity;
    }

    public @NonNull Optional<@NonNull ClientEntity> clientEntity() {
        return Optional.ofNullable(this.clientEntity);
    }

    public @NonNull Optional<@NonNull Entity> bukkitEntity() {
        return Optional.ofNullable(this.bukkitEntity);
    }

    public @NonNull InteractType type() {
        return this.type;
    }

    public enum InteractType {
        INTERACT,
        ATTACK
    }

}
