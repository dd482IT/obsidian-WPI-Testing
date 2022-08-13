package cafe.navy.bedrock.paper.message;

import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.checkerframework.checker.nullness.qual.NonNull;

public class Colours {

    public static class Tones {
        public static final @NonNull TextColor BLACK = NamedTextColor.BLACK;
        public static final @NonNull TextColor DARKER_GRAY = TextColor.color(41, 41, 41);
        public static final @NonNull TextColor DARK_GRAY = TextColor.color(64, 64, 64);
        public static final @NonNull TextColor LIGHT_GRAY = TextColor.color(120, 120, 120);
        public static final @NonNull TextColor LIGHTER_GRAY = TextColor.color(180, 180, 180);
        public static final @NonNull TextColor WHITE = NamedTextColor.WHITE;
    }

    public static class Light {
        public static final @NonNull TextColor RED = TextColor.color(218, 108, 94);
        public static final @NonNull TextColor ORANGE = TextColor.color(218, 166, 96);
        public static final @NonNull TextColor YELLOW = TextColor.color(218, 199, 101);
        public static final @NonNull TextColor GREEN = TextColor.color(149, 218, 118);
        public static final @NonNull TextColor BLUE = TextColor.color(114, 167, 218);
        public static final @NonNull TextColor PINK = TextColor.color(218, 148, 194);
        public static final @NonNull TextColor PURPLE = TextColor.color(178, 144, 218);
    }

    public static class Dark {
        public static final @NonNull TextColor RED = TextColor.color(165, 48, 46);
        public static final @NonNull TextColor ORANGE = TextColor.color(165, 98, 49);
        public static final @NonNull TextColor GOLD = TextColor.color(191, 145, 47);
        public static final @NonNull TextColor GREEN = TextColor.color(61, 126, 52);
        public static final @NonNull TextColor BLUE = TextColor.color(45, 54, 114);
        public static final @NonNull TextColor MAGENTA = TextColor.color(218, 92, 209);
        public static final @NonNull TextColor PURPLE = TextColor.color(118, 53, 171);
    }

}
