package cafe.navy.obsidian.core.util;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Vector {

    private final double x;
    private final double y;
    private final double z;

    public Vector(final double x,
                  final double y,
                  final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double x() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

    public double z() {
        return this.z;
    }

    public @NonNull Vector x(final double x) {
        return new Vector(x, this.y, this.z);
    }

    public @NonNull Vector y(final double y) {
        return new Vector(this.x, y, this.z);
    }

    public @NonNull Vector z(final double z) {
        return new Vector(this.x, this.y, z);
    }

    public @NonNull Vector subtract(final @NonNull Vector vector) {
        return new Vector(this.x - vector.x, this.y - vector.y, this.z - vector.z);
    }


}
