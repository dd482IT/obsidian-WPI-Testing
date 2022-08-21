package cafe.navy.bedrock.paper.item;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.time.Instant;
import java.util.UUID;

public class ItemData {

    private final @NonNull UUID uuid;
    private final @NonNull Instant creationDate;
    private final @NonNull ItemSource source;

    public ItemData(final @NonNull UUID uuid,
                    final @NonNull Instant creationDate,
                    final @NonNull ItemSource source) {
        this.uuid = uuid;
        this.creationDate = creationDate;
        this.source = source;
    }

    public @NonNull UUID uuid() {
        return this.uuid;
    }

    public @NonNull Instant creationDate() {
        return this.creationDate;
    }

    public @NonNull ItemSource source() {
        return this.source;
    }

}
