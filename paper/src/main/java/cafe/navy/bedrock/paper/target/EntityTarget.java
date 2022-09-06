package cafe.navy.bedrock.paper.target;

import cafe.navy.bedrock.paper.entity.ClientEntity;
import cafe.navy.bedrock.core.target.Target;
import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface EntityTarget extends Target {

    void add(final @NonNull ClientEntity entity);

    void remove(final @NonNull ClientEntity entity);

    boolean viewing(final @NonNull ClientEntity entity);

    @NonNull Location location();

}
