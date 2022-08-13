package cafe.navy.paper.message;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
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

}
