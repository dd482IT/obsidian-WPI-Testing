package cafe.navy.obsidian.paper.entities.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.checkerframework.checker.nullness.qual.NonNull;

public class TextUtil {

    private static final @NonNull GsonComponentSerializer GSON_SERIALIZER = GsonComponentSerializer.gson();
    private static final @NonNull PlainTextComponentSerializer PLAIN_SERIALIZER = PlainTextComponentSerializer.plainText();

    public static @NonNull String componentToJson(final @NonNull Component component) {
        return GSON_SERIALIZER.serialize(component);
    }

    public static @NonNull String componentToPlain(final @NonNull Component component) {
        return PLAIN_SERIALIZER.serialize(component);
    }

}
