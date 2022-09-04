package cafe.navy.bedrock.paper.core.message;

import net.kyori.adventure.text.minimessage.tag.Tag;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlaceholderSet {

    public static @NonNull PlaceholderSet create() {
        return new PlaceholderSet(new HashMap<>());
    }

    private final @NonNull Map<String, String> placeholders;

    private PlaceholderSet(final @NonNull Map<String, String> map) {
        this.placeholders = map;
    }

    public @NonNull PlaceholderSet with(final @NonNull String key,
                                        final @NonNull String value) {
        this.placeholders.put(key, value);
        return this;
    }

    public @NonNull String apply(@NonNull String input) {
        if (this.placeholders.isEmpty()) {
            return input;
        }

        for (final var placeholder : this.placeholders.entrySet()) {
            input = input.replace(placeholder.getKey(), placeholder.getValue());
        }

        return input;
    }

    public @NonNull TagResolver asResolver() {
        final List<TagResolver> resolvers = new ArrayList<>();
        for (final var entry : this.placeholders.entrySet()) {
            resolvers.add(TagResolver.resolver(entry.getKey(), Tag.preProcessParsed(entry.getValue())));
        }
        return TagResolver.resolver(resolvers);
    }

}
