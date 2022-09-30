package cafe.navy.obsidian.api.server;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * {@code Server} is an interface that can modify certain properties of a live server.
 */
public interface Server {

    @NonNull String id();

}
