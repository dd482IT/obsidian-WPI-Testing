package cafe.navy.bedrock.paper.core.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;
import java.util.Iterator;

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
                                                 final @Nullable Component message,
                                                 final @NonNull ComponentLike... authors) {
        final Message res = Message.create();

        res.text("> ", Colours.Tones.DARKER_GRAY);
        res.text(name.asComponent())
                .main(" version ")
                .text(version.asComponent());
        res.newline();

        res.text("> ", Colours.Tones.DARKER_GRAY);
        res.text("  ")
                .main("by")
                .text(" ");

        final Iterator<ComponentLike> it = Arrays.stream(authors).iterator();
        while (it.hasNext()) {
            final ComponentLike next = it.next();
            res.text(next.asComponent());
            if (it.hasNext()) {
                res.main(", ");
            }
        }
        res.main("!");

        if (message != null) {
            res.newline();
            res.text("> ", Colours.Tones.DARKER_GRAY)
                    .main(message);
        }

        return res;
    }

}
