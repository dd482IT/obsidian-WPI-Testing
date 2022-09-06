package cafe.navy.bedrock.core.util;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

public class NumberUtil {

    /**
     * Minecraft's gravitational constant. A player's Y velocity will be this value iff the player has no motion
     * in the Y direction - i.e. standing, walking forward, but not jumping or falling.
     */
    public static final double MINECRAFT_GRAVITATIONAL_CONSTANT = -0.0784000015258789;

    private static final @NonNull Random RANDOM = new Random();

    public static double between(final double min,
                                 final double max) {
        return RANDOM.nextDouble() * (max - min) + min;
    }

    public static float between(final float min,
                                final float max) {
        return RANDOM.nextFloat() * (max - min) + min;
    }

    public static int between(final int min,
                              final int max) {
        return RANDOM.nextInt(max - min) + min;
    }

    public static long between(final long min,
                              final long max) {
        return RANDOM.nextLong(max - min) + min;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static <T> T choice(final @NonNull T... t) {
        int length = t.length;
        if (length == 1) {
            return t[0];
        } else if (length == 0) {
            throw new RuntimeException();
        }
        return t[between(0, length)];
    }

    public static <T> T choice(final @NonNull List<T> list) {
        final int length = list.size();
        if (length == 1) {
            return list.get(0);
        } else if (length == 0) {
            throw new RuntimeException();
        }
        return list.get(between(0, length));
    }

    public static @NonNull String wordSuffix(final int number) {
        final String numberString = Integer.toString(number);
        final int length = numberString.length();
        final char finalChar = numberString.toCharArray()[length - 1];
        final int finalNumber = Integer.parseInt("" + finalChar);
        if (finalNumber == 1) {
            return "st";
        } else if (finalNumber == 2) {
            return "nd";
        } else if (finalNumber == 3) {
            return "rd";
        } else {
            return "th";
        }
    }

    public static float map(final float min,
                            final float max,
                            final float newMin,
                            final float newMax,
                            final float num) {
        return (num - min) / (max - min) * (newMax - newMin) + newMin;
    }

}
