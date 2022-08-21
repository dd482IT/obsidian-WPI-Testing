package cafe.navy.bedrock.paper.item.context;

import cafe.navy.bedrock.paper.entity.ClientEntity;
import cafe.navy.bedrock.paper.item.Item;
import cafe.navy.bedrock.paper.player.PlayerTarget;
import org.bukkit.entity.Entity;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class EntityInteractContext extends ItemContext {

    private final @Nullable Entity bukkitEntity;
    private final @Nullable ClientEntity clientEntity;
    private final @Nullable Item item;
    private final @Nullable PlayerTarget target;

    public EntityInteractContext(final @Nullable ClientEntity clientEntity,
                                 final @Nullable Entity bukkitEntity,
                                 final @Nullable Item item,
                                 final @Nullable PlayerTarget target) {
        this.clientEntity = clientEntity;
        this.bukkitEntity = bukkitEntity;
        this.item = item;
        this.target = target;
    }

    public @Nullable ClientEntity clientEntity() {
        return this.clientEntity;
    }

    public @Nullable PlayerTarget target() {
        return this.target;
    }


}
