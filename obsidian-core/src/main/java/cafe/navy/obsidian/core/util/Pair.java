package cafe.navy.obsidian.core.util;

import org.checkerframework.checker.nullness.qual.NonNull;

public class Pair<A, B> {

    private final @NonNull A a;
    private final @NonNull B b;

    public Pair(final @NonNull A a,
                final @NonNull B b) {
        this.a = a;
        this.b = b;
    }

    public @NonNull A a() {
        return this.a;
    }

    public @NonNull B b() {
        return this.b;
    }

}
