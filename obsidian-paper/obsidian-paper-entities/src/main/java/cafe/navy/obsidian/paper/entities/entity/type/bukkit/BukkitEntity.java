package cafe.navy.obsidian.paper.entities.entity.type.bukkit;

import cafe.navy.obsidian.core.client.GameClient;
import cafe.navy.obsidian.core.util.Position;
import cafe.navy.obsidian.paper.entities.entity.CustomEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class BukkitEntity implements CustomEntity {

    private final @NonNull Entity entity;

    public BukkitEntity(final @NonNull Entity entity) {
        this.entity = entity;
    }

    @Override
    public void show(final @NonNull GameClient player) {
    }

    @Override
    public void hide(@NonNull GameClient viewer) {

    }

    @Override
    public boolean matchesId(int entityId) {
        return false;
    }

    @Override
    public double height() {
        return 0;
    }

    @Override
    public void lookAt(@NonNull GameClient viewer, @NonNull Position position) {

    }

    @Override
    public void rotate(@NonNull GameClient viewer, float yaw, float pitch) {

    }
}
