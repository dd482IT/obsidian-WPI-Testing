package cafe.navy.bedrock.paper.core.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code Message} is a builder-style class allowing one to create declarative messages using placeholders,
 * {@link Component}s, and {@link String}s.
 * <p>
 * All text methods apply placeholders from the {@link PlaceholderSet}, with parsed tags from
 * {@link Message#MINI_MESSAGE}.
 */
public class Message implements ComponentLike {

    public static final @NonNull MiniMessage MINI_MESSAGE;

    static {
        final MiniMessage.Builder builder = MiniMessage.builder();
        MINI_MESSAGE = builder
                .build();
    }

    private final @NonNull List<MessagePart> parts;
    private final @NonNull PlaceholderSet placeholders;

    /**
     * Constructs an empty {@link Message} instance.
     *
     * @return a {@link Message}
     */
    public static @NonNull Message create() {
        return new Message();
    }

    /**
     * Constructs a {@code Message} instance with {@code input} for content.
     *
     * @param input the input
     * @return a {@link Message}
     */
    public static @NonNull Message create(final @NonNull String input) {
        return create().text(input);
    }

    /**
     * Constructs a {@code Message} instance with {@code input} for content.
     *
     * @param input the input
     * @return a {@link Message}
     */
    public static @NonNull Message create(final @NonNull Component input) {
        return create().text(input);
    }

    /**
     * Appends a {@link String} to the message.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message text(final @NonNull String text) {
        this.addPart(new MiniMessageMessagePart(text, MINI_MESSAGE, this.placeholders));
        return this;
    }

    /**
     * Appends a {@link String} to the message with the specified {@link TextColor}.
     *
     * @param text  the text content to append
     * @param color the text color
     * @return this
     */
    public @NonNull Message text(final @NonNull String text,
                                 final @NonNull TextColor color) {
        return this.text(Component.text(text, color));
    }

    /**
     * Appends a {@link String} to the message with the specified {@link TextColor} and {@link TextDecoration}.
     *
     * @param text        the text content to append
     * @param color       the text color
     * @param decorations the text decorations
     * @return this
     */
    public @NonNull Message text(final @NonNull String text,
                                 final @NonNull TextColor color,
                                 final @NonNull TextDecoration... decorations) {
        return this.text(Component.text(text, color, decorations));
    }


    /**
     * Appends a {@link String} to the message with the specified {@link Style}.
     *
     * @param text  the text content to append
     * @param style the text style
     * @return this
     */
    public @NonNull Message text(final @NonNull String text,
                                 final @NonNull Style style) {
        return this.text(Component.text(text, style));
    }

    /**
     * Appends a {@link String} to the message with a gradient colour LERPed between {@code a} and {@code b}.
     *
     * @param text        the text
     * @param a           the first colour
     * @param b           the second coloour
     * @param decorations the decoration set
     * @return this
     */
    public @NonNull Message gradient(final @NonNull String text,
                                     final @NonNull TextColor a,
                                     final @NonNull TextColor b,
                                     final @NonNull TextDecoration... decorations) {
        return this.text(MINI_MESSAGE.deserialize(
                "<gradient:" + a.asHexString() + ":" + b.asHexString() + ">" +
                        text +
                        "</gradient:" + a.asHexString() + ":" + b.asHexString() + ">"
        ).decorate(decorations));
    }

    /**
     * Appends a newline to the message.
     *
     * @return this
     */
    public @NonNull Message newline() {
        this.addPart(new ComponentMessagePart(Component.newline(), this.placeholders));
        return this;
    }


    /**
     * Appends a text {@link Component}.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message text(final @NonNull Component text) {
        this.addPart(new ComponentMessagePart(text, this.placeholders));
        return this;
    }

    /**
     * Appends a text {@link Component}.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message text(final @NonNull ComponentLike text) {
        this.addPart(new ComponentMessagePart(text.asComponent(), this.placeholders));
        return this;
    }

    /**
     * Appends text with the main style.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message main(final @NonNull String text,
                                 final @NonNull TextDecoration... decorations) {
        return this.text(text, Colours.Tones.LIGHTER_GRAY, decorations);
    }


    /**
     * Appends text with the link style.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message link(final @NonNull String text) {
        return this.link(text, null);
    }

    /**
     * Appends text with the link style.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message copy(final @NonNull String text) {
        return this.copy(text, null);
    }

    /**
     * Appends text with the link style.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message copy(final @NonNull String text,
                                 final @Nullable String copy) {
        Component component = Component.text(text, Colours.Light.PURPLE, TextDecoration.UNDERLINED);
        if (copy != null) {
            component = component.clickEvent(ClickEvent.copyToClipboard(copy));
            component = component.hoverEvent(HoverEvent.showText(Message.create("Click to copy ").copy(copy).main(".")));
        }
        return this.text(component);
    }

    /**
     * Appends text with the link style.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message link(final @NonNull String text,
                                 final @Nullable String url) {
        Component component = Component.text(text, Colours.Light.BLUE, TextDecoration.UNDERLINED);
        if (url != null) {
            component = component.hoverEvent(HoverEvent.showText(Message.create("Click to open ").link(url).main(".")));
            component = component.clickEvent(ClickEvent.openUrl(url));
        }

        return this.text(component);
    }

    /**
     * Appends text with the link style.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message link(final @NonNull Component text,
                                 final @Nullable String url) {
        Component component = text.colorIfAbsent(Colours.Light.BLUE);
        if (component.decoration(TextDecoration.ITALIC) == TextDecoration.State.NOT_SET) {
            component = component.decoration(TextDecoration.ITALIC, TextDecoration.State.TRUE);
        }

        if (url != null) {
            component = component.hoverEvent(HoverEvent.showText(Message.create().main("Click to open ").link(url).main(".")));
            component = component.clickEvent(ClickEvent.openUrl(url));
        }

        return this.text(component);
    }

    /**
     * Appends text with the command style.
     *
     * @param text    the text content to append
     * @param command the command to suggest
     * @return this
     */
    public @NonNull Message command(final @NonNull String text,
                                    final @Nullable String command) {
        Component component = Component.text(text, Colours.Light.BLUE, TextDecoration.UNDERLINED);
        if (command != null) {
            component = component.hoverEvent(HoverEvent.showText(Message.create("Click to run ").command(command).main(".")));
            component = component.clickEvent(ClickEvent.suggestCommand(command));
        }

        return this.text(component);
    }

    /**
     * Appends text with the command style.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message command(final @NonNull String text) {
        return this.command(text, null);
    }

    /**
     * Appends text with the highlight style.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message highlight(final @NonNull String text) {
        return this.text(text, Colours.Light.PINK);
    }

    /**
     * Appends text with the success style.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message success(final @NonNull String text) {
        return this.text(text, Colours.Light.GREEN);
    }

    /**
     * Appends text with the error style.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message error(final @NonNull String text) {
        return this.text(text, Colours.Light.RED);
    }

    /**
     * Appends text with the warning style.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message warning(final @NonNull String text) {
        return this.text(text, Colours.Light.YELLOW);
    }

    /**
     * Appends text with the main style.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message main(final @NonNull Component text) {
        return this.text(text.color(Colours.Tones.LIGHT_GRAY));
    }

    /**
     * Appends text with the link style.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message link(final @NonNull Component text) {
        return this.text(text.color(Colours.Light.BLUE).decorate(TextDecoration.UNDERLINED));
    }

    /**
     * Appends text with the highlight style.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message highlight(final @NonNull Component text) {
        return this.text(text.color(Colours.Light.PINK));
    }

    /**
     * Appends text with the success style.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message success(final @NonNull Component text) {
        return this.text(text.color(Colours.Light.GREEN));
    }

    /**
     * Appends text with the error style.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message error(final @NonNull Component text) {
        return this.text(text.color(Colours.Light.RED));
    }

    /**
     * Appends text with the warning style.
     *
     * @param text the text content to append
     * @return this
     */
    public @NonNull Message warning(final @NonNull Component text) {
        return this.text(text.color(Colours.Light.YELLOW));
    }

    /**
     * Splits the message content on newlines and returns a new list of messages.
     *
     * @return the message list
     */
    public @NonNull List<@NonNull Message> toLines() {
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
        return List.copyOf(messages);
    }

    private Message() {
        this.placeholders = PlaceholderSet.create();
        this.parts = new ArrayList<>();
    }

    @Override
    public @NonNull Component asComponent() {
        TextComponent.Builder builder = Component.text();

        for (final MessagePart part : this.parts) {
            builder.append(part);
        }

        return builder.build();
    }

    private void addPart(final @NonNull MessagePart part) {
        this.parts.add(part);
    }

    private abstract static class MessagePart implements ComponentLike {

    }

    private static class ComponentMessagePart extends MessagePart {

        private final @NonNull Component component;
        private final @NonNull PlaceholderSet placeholderSet;

        public ComponentMessagePart(final @NonNull Component component,
                                    final @NonNull PlaceholderSet placeholderSet) {
            this.component = component;
            this.placeholderSet = placeholderSet;
        }

        @Override
        public @NonNull Component asComponent() {
            return this.component;
        }
    }

    private static class MiniMessageMessagePart extends MessagePart {

        private final @NonNull String raw;
        private final @NonNull MiniMessage miniMessage;
        private final @NonNull PlaceholderSet placeholderSet;

        protected MiniMessageMessagePart(final @NonNull String raw,
                                         final @NonNull MiniMessage miniMessage,
                                         final @NonNull PlaceholderSet placeholderSet) {
            this.raw = raw;
            this.miniMessage = miniMessage;
            this.placeholderSet = placeholderSet;
        }

        @Override
        public @NonNull Component asComponent() {
            return this.miniMessage.deserialize(this.raw, this.placeholderSet.asResolver());
        }
    }

}
