package cafe.navy.obsidian.paper.entities.entity.type.nms;

import cafe.navy.obsidian.core.client.GameClient;
import cafe.navy.obsidian.core.util.Position;
import cafe.navy.obsidian.paper.entities.entity.CustomEntity;
import net.kyori.adventure.text.Component;
import net.minecraft.world.entity.Entity;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;


public class NMSCustomEntity implements CustomEntity {

    private final @NonNull List<Component> names;
    private final @NonNull Entity entity;
    private final @NonNull Position position;

    public NMSCustomEntity(final @NonNull Entity entity,
                           final @NonNull List<Component> names) {
        this.names = names;
        this.entity = entity;
        this.position = Position.of(entity.getX(), entity.getY(), entity.getZ());
    }

    @Override
    public void show(final @NonNull GameClient viewer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void hide(final @NonNull GameClient viewer) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean matchesId(final int entityId) {
        return this.entity.getId() == entityId;
    }

    @Override
    public double height() {
        return this.entity.getBbHeight();
    }

    @Override
    public void lookAt(@NonNull GameClient viewer, @NonNull Position position) {
        // todo
    }

    @Override
    public void rotate(@NonNull GameClient viewer, float yaw, float pitch) {

    }

}
