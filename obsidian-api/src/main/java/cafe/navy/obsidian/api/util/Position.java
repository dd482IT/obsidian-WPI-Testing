package cafe.navy.obsidian.api.util;

import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * {@code Position} is an immutable data structure containing 3-dimensional coordinates, along with yaw/pitch information.
 * <p>
 * Setter methods (such as {@link #x(double)}, {@link #pitch(float)}) return new instances of {@link Position}.
 */
public class Position {

    public static @NonNull Position of(final double x,
                                       final double y,
                                       final double z,
                                       final float yaw,
                                       final float pitch) {
        return new Position(x, y, z, yaw, pitch);
    }

    public static @NonNull Position of(final double x,
                                       final double y,
                                       final double z) {
        return of(x, y, z, 0, 0);
    }

    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    /**
     * Constructs {@code Position}.
     *
     * @param x     the x coordinate
     * @param y     the y coordinate
     * @param z     the z coordinate
     * @param yaw   the yaw value
     * @param pitch the pitch value
     */
    public Position(final double x,
                    final double y,
                    final double z,
                    final float yaw,
                    final float pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
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

    public float yaw() {
        return this.yaw;
    }

    public float pitch() {
        return this.pitch;
    }

    public @NonNull Position x(final double x) {
        return new Position(x, this.y, this.z, this.yaw, this.pitch);
    }

    public @NonNull Position y(final double y) {
        return new Position(this.x, y, this.z, this.yaw, this.pitch);
    }

    public @NonNull Position z(final double z) {
        return new Position(this.x, this.y, z, this.yaw, this.pitch);
    }

    public @NonNull Position yaw(final float yaw) {
        return new Position(this.x, this.y, this.z, yaw, this.pitch);
    }

    public @NonNull Position pitch(final float pitch) {
        return new Position(this.x, this.y, this.z, this.yaw, pitch);
    }

    public @NonNull Position plusX(final double delta) {
        return new Position(this.x + delta, this.y, this.z, this.yaw, this.pitch);
    }

    public @NonNull Position plusY(final double delta) {
        return new Position(this.x, this.y + delta, this.z, this.yaw, this.pitch);
    }

    public @NonNull Position plusZ(final double delta) {
        return new Position(this.x, this.y + delta, this.z, this.yaw, this.pitch);
    }

    public boolean hasChangedBlock(final @NonNull Position position) {
        final double deltaX = Math.abs(position.x - this.x);
        final double deltaY = Math.abs(position.y - this.y);
        final double deltaZ = Math.abs(position.z - this.z);

        return deltaX >= 1 || deltaY >= 1 || deltaZ >= 1;
    }

    public boolean hasChangedRot(final @NonNull Position position) {
        return position.yaw != this.yaw || position.pitch != this.pitch;
    }

    public @NonNull Vector toVector() {
        double rotX = this.yaw();
        double rotY = this.pitch();
        double xz = Math.cos(Math.toRadians(rotY));

        final double vecY = -Math.sin(Math.toRadians(rotY));
        final double vecX = -xz * Math.sin(Math.toRadians(rotX));
        final double vecZ = xz * Math.cos(Math.toRadians(rotX));

        return new Vector(vecX, vecY, vecZ);
    }

    public double distanceSquared(final double ox,
                                  final double oy,
                                  final double oz) {
        return Math.pow(this.x - ox, 2) + Math.pow(this.y - oy, 2) + Math.pow(this.z - oz, 2);
    }

    public double distanceSquared(final @NonNull Position o) {
        return this.distanceSquared(o.x(), o.y(), o.z());
    }

    @Override
    public @NonNull String toString() {
        return String.format("Position{xyz=%f,%f,%f, yaw=%f, pitch=%f}", this.x, this.y, this.z, this.yaw, this.pitch);
    }

}
