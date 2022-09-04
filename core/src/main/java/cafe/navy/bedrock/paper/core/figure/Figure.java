package cafe.navy.bedrock.paper.core.figure;

import org.bukkit.Location;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * {@code Figure} represents an "entity" in a Minecraft world.
 */
public interface Figure {

    @NonNull UUID uuid();

    @NonNull String name();

    @NonNull Location location();

    public void remove();

}
