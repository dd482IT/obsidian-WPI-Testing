package cafe.navy.bedrock.paper.target;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * {@code Target} represents something that can receive data, i.e. messages, entities, or other values.
 */
public interface Target {

    @NonNull UUID uuid();

    @NonNull String name();

}
