package cafe.navy.bedrock.paper.core.target;

import cafe.navy.bedrock.paper.core.entity.ClientEntity;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface EntityTarget extends Target {

    void add(final @NonNull ClientEntity entity);

    void remove(final @NonNull ClientEntity entity);

    boolean viewing(final @NonNull ClientEntity entity);

    @NonNull Location location();

}
