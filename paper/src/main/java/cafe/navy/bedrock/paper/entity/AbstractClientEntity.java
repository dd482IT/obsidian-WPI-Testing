package cafe.navy.bedrock.paper.entity;

import cafe.navy.bedrock.paper.exception.ClientEntityException;
import cafe.navy.bedrock.paper.target.EntityTarget;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractClientEntity implements ClientEntity {

    private final @NonNull List<EntityTarget> viewers;

    public AbstractClientEntity() {
        this.viewers = new ArrayList<>();
    }

    public void update() {
        for (final EntityTarget viewer : this.viewers) {
            try {
                this.update(viewer);
            } catch (ClientEntityException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void add(final @NonNull EntityTarget target) throws ClientEntityException{
        this.viewers.add(target);
        this.handleAdd(target);
    }

    @Override
    public void remove(final @NonNull EntityTarget target) throws ClientEntityException{
        this.viewers.remove(target);
        this.handleRemove(target);
    }

    protected abstract void handleAdd(final @NonNull EntityTarget target) throws ClientEntityException;

    protected abstract void handleRemove(final @NonNull EntityTarget target) throws ClientEntityException;


}
