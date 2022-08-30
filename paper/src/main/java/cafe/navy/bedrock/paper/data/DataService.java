package cafe.navy.bedrock.paper.data;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.ConfigurationOptions;
import org.spongepowered.configurate.serialize.TypeSerializer;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

import java.util.HashMap;
import java.util.Map;

public class DataService {

    private final @NonNull Map<Class<?>, TypeSerializer<?>> serializerMap;
    private @NonNull TypeSerializerCollection collection;

    public DataService() {
        this.serializerMap = new HashMap<>();
        this.collection = TypeSerializerCollection.defaults();
    }

    public <T> void registerSerializer(final @NonNull TypeSerializer<T> serializer,
                                       final @NonNull Class<T> clazz) {
        this.serializerMap.put(clazz, serializer);
        this.collection = this.collection.childBuilder().register(clazz, serializer).build();
    }

    public @NonNull ConfigurationNode newRoot() {
        return CommentedConfigurationNode.root(ConfigurationOptions.defaults().serializers(this.collection));
    }

    public @NonNull DataHolder newHolder() {
        return new DataHolder(this);
    }
}