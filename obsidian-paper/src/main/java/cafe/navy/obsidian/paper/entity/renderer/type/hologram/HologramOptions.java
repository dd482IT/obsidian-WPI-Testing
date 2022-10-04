package cafe.navy.obsidian.paper.entity.renderer.type.hologram;

import cafe.navy.obsidian.core.util.Position;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class HologramOptions {

    private final @NonNull List<String> name;
    private final @NonNull Position position;
    private final double lineSpacing;

    public HologramOptions(final @NonNull List<String> name,
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

}
