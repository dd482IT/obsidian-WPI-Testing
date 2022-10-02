package cafe.navy.obsidian.paper.entities.entity.type.hologram;

import cafe.navy.obsidian.core.util.Position;
import net.kyori.adventure.text.Component;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

public class HologramBuilder {

    private static final double DEFAULT_LINE_SPACING = 1.275;

    public static @NonNull HologramBuilder create(final @NonNull Position position) {
        return new HologramBuilder(position);
    }

    private final @NonNull Position position;
    private final @NonNull List<Component> lines;
    private double lineSpacing;

    private HologramBuilder(final @NonNull Position position) {
        this.position = position;
        this.lines = new ArrayList<>();
        this.lineSpacing = DEFAULT_LINE_SPACING;
    }

    public @NonNull HologramBuilder line(final @NonNull Component text) {
        this.lines.add(text);
        return this;
    }

    public @NonNull HologramBuilder spacing(final double lineSpacing) {
        this.lineSpacing = lineSpacing;
        return this;
    }

    public @NonNull HologramEntity build() {
        return new HologramEntity(this.lines, this.lineSpacing, this.position);
    }

}
