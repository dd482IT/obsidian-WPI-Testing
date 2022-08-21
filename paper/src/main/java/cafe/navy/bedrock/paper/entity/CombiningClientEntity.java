package cafe.navy.bedrock.paper.entity;

import cafe.navy.bedrock.paper.exception.ClientEntityException;
import cafe.navy.bedrock.paper.target.EntityTarget;
import org.bukkit.Location;
import org.bukkit.entity.LightningStrike;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.UUID;

public class CombiningClientEntity implements ClientEntity {

    private final @NonNull Location location;
    private final @NonNull List<ClientEntity> entities;

    public CombiningClientEntity(final @NonNull Location location,
                                 final @NonNull List<ClientEntity> entities) {
        this.location = location;
        this.entities = entities;
    }

    @Override
    public @NonNull UUID uuid() {
        return UUID.randomUUID();
    }

    @Override
    public @NonNull Location location() {
        return this.location;
    }

    @Override
    public void add(@NonNull EntityTarget target) throws ClientEntityException {
        this.entities.forEach(target::add);
    }

    @Override
    public void remove(@NonNull EntityTarget target) throws ClientEntityException {
        this.entities.forEach(target::remove);
    }

    @Override
    public void update(@NonNull EntityTarget target) throws ClientEntityException {
        this.entities.forEach(e -> {
            try {
                e.update(target);
            } catch (ClientEntityException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Override
    public boolean checkId(int entityId) {
        for (final ClientEntity entity : this.entities) {
            if (entity.checkId(entityId)) {
                return true;
            }
        }

        return false;
    }
}
