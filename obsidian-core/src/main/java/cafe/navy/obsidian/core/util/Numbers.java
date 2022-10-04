package cafe.navy.obsidian.core.util;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * {@code Numbers} provides math/number-related utility methods.
 */
public class Numbers {

    private static final @NonNull Random RANDOM = new Random();

    /**
     * Chooses a random value in {@code list}.
     *
     * @param list the list
     * @param <T>  the value's type
     * @return the value
     */
    public static <T> @NonNull T choice(final @NonNull List<T> list) {
        return list.get(between(0, list.size()));
    }

    /**
     * Returns a random number between {@code a} and {@code b}.
     *
     * @param a the min
     * @param b the max
     * @return the number
     */
    public static int between(final int a,
                              final int b) {
        if (a == b) {
            return a;
        }

        return RANDOM.nextInt(b - a) + a;
    }

    /**
     * Returns a random number between {@code a} and {@code b}.
     *
     * @param a the min
     * @param b the max
     * @return the number
     */
    public static double between(final double a,
                              final double b) {
        if (a == b) {
            return a;
        }

        return RANDOM.nextDouble(b - a) + a;
    }

    /**
     * Chooses a random value in {@code set}.
     *
     * @param set the set
     * @param <T> the value's type
     * @return the value
     */
    public static <T> @NonNull T choice(final @NonNull Set<T> set) {
        return choice(List.copyOf(set));
    }

    /**
     * Converts a Minecraft inventory slot value to grid point coordinates.
     *
     * @param slot the slot
     * @return an array with two numbers containing the grid point coordinates.
     */
    public static int[] slotToGridPoint(final int slot) {
        return new int[]{slot % 9, slot / 9};
    }

    private Numbers() {
        // private constructor as this is a utility class
    }

}
