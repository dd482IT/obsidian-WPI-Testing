package cafe.navy.obsidian.core.client;

import org.checkerframework.checker.nullness.qual.NonNull;

public interface GamePacket {

    void send(final @NonNull GameClient client);

}
