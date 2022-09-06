package cafe.navy.bedrock.core.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.TextColor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public interface Messages {

    @NonNull Message NO_PLAYER = Message
            .create()
            .text("Hey! ")
            .text("Only players can use this command.");

    @NonNull Message BROADCAST_STRETCH = Message
            .create()
            .text("Don't forget to stretch!", Colours.Light.BLUE)
            .newline()
            .text("Your body will thank you :^)");

    static @NonNull Message createError() {
        return Message
                .create()
                .text("[")
                .error("Error")
                .text("] ");
    }

    static @NonNull Message createVersionMessage(final @NonNull ComponentLike name,
                                                 final @NonNull ComponentLike version,
                                                 final @Nullable ComponentLike message,
                                                 final @Nullable TextColor color,
                                                 final @NonNull ComponentLike... authors) {
        final Message res = Message.create();

        final Component indent = Component.text("▎ ", Objects.requireNonNullElse(color, Colours.Tones.DARKER_GRAY));

        res.text(indent);
        res.text(name.asComponent())
                .main(" version ")
                .text(version.asComponent());

        if (authors.length != 0) {
            if (authors.length != 1) {
                res.newline();
                res.text(indent);
            }

            res.main(" by ");

            final Iterator<ComponentLike> it = Arrays.stream(authors).iterator();
            while (it.hasNext()) {
                final ComponentLike next = it.next();
                res.text(next.asComponent());
                if (it.hasNext()) {
                    res.main(", ");
                }
            }
        }

        if (message != null) {
            res.newline();
            res.text(indent)
                    .main(message.asComponent());
        }

        return res;
    }

}
