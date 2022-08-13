package cafe.navy.paper.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Message implements ComponentLike {

    public static final @NonNull MiniMessage MINI_MESSAGE;

    static {
        final MiniMessage.Builder builder = MiniMessage.builder();
        MINI_MESSAGE = MiniMessage
                .builder()
                .build();
    }

    private final @NonNull List<MessagePart> parts;
    private final @NonNull PlaceholderSet placeholders;

    public static @NonNull Message create() {
        return new Message();
    }

    public static @NonNull Message create(final @NonNull String input) {
        return create().text(input);
    }

    public static @NonNull Message create(final @NonNull Component input) {
        return create().text(input);
    }

    private Message() {
        this.placeholders = PlaceholderSet.create();
        this.parts = new ArrayList<>();
    }

    public @NonNull Message text(final @NonNull String text) {
        this.addPart(new MiniMessageMessagePart(text, MINI_MESSAGE));
        return this;
    }

    public @NonNull Message text(final @NonNull String text,
                                 final @NonNull TextColor color) {
        return this.text(Component.text(text, color));
    }


    public @NonNull Message text(final @NonNull String text,
                                 final @NonNull TextColor color,
                                 final @NonNull TextDecoration decoration) {
        return this.text(Component.text(text, color, decoration));
    }


    public @NonNull Message text(final @NonNull String text,
                                 final @NonNull Style style) {
        return this.text(Component.text(text, style));
    }

    public @NonNull Message newline() {
        this.addPart(new ComponentMessagePart(Component.newline()));
        return this;
    }

    public @NonNull Message text(final @NonNull Component text) {
        this.addPart(new ComponentMessagePart(text));
        return this;
    }

    public @NonNull Message main(final @NonNull String text) {
        return this.text(text, Colours.Tones.LIGHT_GRAY);
    }

    public @NonNull Message link(final @NonNull String text) {
        return this.text(text, Colours.Light.BLUE, TextDecoration.UNDERLINED);
    }

    public @NonNull Message highlight(final @NonNull String text) {
        return this.text(text, Colours.Light.PINK);
    }

    public @NonNull Message success(final @NonNull String text) {
        return this.text(text, Colours.Light.GREEN);
    }

    public @NonNull Message error(final @NonNull String text) {
        return this.text(text, Colours.Light.RED);
    }

    public @NonNull Message warning(final @NonNull String text) {
        return this.text(text, Colours.Light.YELLOW);
    }

    public @NonNull Message main(final @NonNull Component text) {
        return this.text(text.color(Colours.Tones.LIGHT_GRAY));
    }

    public @NonNull Message link(final @NonNull Component text) {
        return this.text(text.color(Colours.Light.BLUE).decorate(TextDecoration.UNDERLINED));
    }

    public @NonNull Message highlight(final @NonNull Component text) {
        return this.text(text.color(Colours.Light.PINK));
    }

    public @NonNull Message success(final @NonNull Component text) {
        return this.text(text.color(Colours.Light.GREEN));
    }

    public @NonNull Message error(final @NonNull Component text) {
        return this.text(text.color(Colours.Light.RED));
    }

    public @NonNull Message warning(final @NonNull Component text) {
        return this.text(text.color(Colours.Light.YELLOW));
    }

    private void addPart(final @NonNull MessagePart part) {
        this.parts.add(part);
    }

    public @NonNull List<Message> toLines() {
        final List<Message> messages = new ArrayList<>();

        Message cur = Message.create();
        for (final MessagePart part : cur.parts) {
            if (part.asComponent().equals(Component.newline())) {
                messages.add(cur);
                cur = Message.create();
            }
            cur.addPart(part);
        }
        messages.add(cur);
        return messages;
    }

    @Override
    public @NonNull Component asComponent() {
        TextComponent.Builder builder = Component.text();

        for (final MessagePart part : this.parts) {
            builder.append(part);
        }

        return builder.build();
    }

    private abstract static class MessagePart implements ComponentLike {

    }

    private static class ComponentMessagePart extends MessagePart {

        private final @NonNull Component component;

        public ComponentMessagePart(final @NonNull Component component) {
            this.component = component;
        }

        @Override
        public @NonNull Component asComponent() {
            return this.component;
        }
    }

    private static class MiniMessageMessagePart extends MessagePart {

        private final @NonNull String raw;
        private final @NonNull MiniMessage miniMessage;

        protected MiniMessageMessagePart(final @NonNull String raw,
                                         final @NonNull MiniMessage miniMessage) {
            this.raw = raw;
            this.miniMessage = miniMessage;
        }

        @Override
        public @NonNull Component asComponent() {
            return this.miniMessage.deserialize(this.raw);
        }
    }

}
