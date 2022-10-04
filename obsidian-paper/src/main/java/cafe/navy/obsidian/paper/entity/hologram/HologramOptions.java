package cafe.navy.obsidian.paper.entity.hologram;

import cafe.navy.obsidian.core.util.Position;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * {@code HologramOptions} contains values used by a {@link HologramRenderer} instance.
 */
public final class HologramOptions {

    public static @NonNull Builder builder(final @NonNull Position position) {
        return new Builder(position);
    }

    private final @NonNull List<String> name;
    private final @NonNull Position position;
    private final double lineSpacing;

    private HologramOptions(final @NonNull List<String> name,
                            final @NonNull Position position,
                            final double lineSpacing) {
        this.name = name;
        this.position = position;
        this.lineSpacing = lineSpacing;
    }

    public @NonNull List<String> names() {
        return this.name;
    }

    public double lineSpacing() {
        return this.lineSpacing;
    }

    public @NonNull Position position() {
        return this.position;
    }

    public static final class Builder {

        private final @NonNull List<String> name;
        private final @NonNull Position position;
        private double lineSpacing = 0.275;

        private Builder(final @NonNull Position position) {
            this.position = position;
            this.name = new ArrayList<>();
        }

        public @NonNull Builder addLine(final @NonNull String text) {
            this.name.add(text);
            return this;
        }

        public @NonNull Builder addLines(final @NonNull List<String> text) {
            this.name.addAll(text);
            return this;
        }

        public @NonNull Builder addLines(final @NonNull String... text) {
            this.name.addAll(Arrays.asList(text));
            return this;
        }

        public @NonNull Builder lineSpacing(final double lineSpacing) {
            this.lineSpacing = lineSpacing;
            return this;
        }

        public @NonNull HologramOptions build() {
            return new HologramOptions(this.name, this.position, this.lineSpacing);
        }

    }

}
