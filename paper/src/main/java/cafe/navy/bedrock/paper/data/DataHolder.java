package cafe.navy.bedrock.paper.data;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.ConfigurationNode;

public class DataHolder {

    private final @NonNull DataService service;
    private final @NonNull ConfigurationNode node;

    public DataHolder(final @NonNull DataService service) {
        this.service = service;
        this.node = this.service.newRoot();
    }

    public @NonNull ConfigurationNode root() {
        return this.node;
    }

}
