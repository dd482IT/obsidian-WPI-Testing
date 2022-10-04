package cafe.navy.obsidian.paper.entity.player;

import cafe.navy.obsidian.core.util.Numbers;
import cafe.navy.obsidian.core.util.Position;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Objects;
import java.util.UUID;

/**
 * {@code PlayerOptions} contains values used by a {@link PlayerRenderer} instance.
 */
public final class PlayerOptions {

    public static @NonNull Builder builder(final @NonNull Position position) {
        return new Builder(position);
    }

    private final @NonNull Position position;
    private final @NonNull UUID uuid;
    private final @NonNull String name;
    private final boolean showName;
    private final @Nullable String skinTexture;
    private final @Nullable String skinSignature;

    /**
     * Constructs {@code PlayerOptions}.
     *
     * @param name     the name of the player (may be empty)
     * @param uuid     the {@link UUID} of the player
     * @param position the position of the player
     * @param showName if the player's name should be visible
     */
    public PlayerOptions(final @NonNull String name,
                         final @NonNull UUID uuid,
                         final @NonNull Position position,
                         final boolean showName,
                         final @Nullable String skinTexture,
                         final @Nullable String skinSignature) {
        this.position = position;
        this.name = name;
        this.uuid = uuid;
        this.showName = showName;
        this.skinTexture = skinTexture;
        this.skinSignature = skinSignature;
    }

    /**
     * Returns the skin's texture.
     *
     * @return the skin's texture
     * @throws NullPointerException if the texture is null. check {@link #hasSkin()} before calling this method
     */
    public @NonNull String skinTexture() {
        return Objects.requireNonNull(this.skinTexture);
    }

    /**
     * Returns the skin's signature.
     *
     * @return the skin's signature
     * @throws NullPointerException if the signature is null. check {@link #hasSkin()} before calling this method
     */
    public @NonNull String skinSignature() {
        return Objects.requireNonNull(this.skinSignature);
    }

    /**
     * Returns true if a skin is set.
     *
     * @return a boolean
     */
    public boolean hasSkin() {
        return this.skinTexture != null && this.skinSignature != null;
    }

    /**
     * Returns the position of the player.
     *
     * @return the player's position
     */
    public @NonNull Position position() {
        return this.position;
    }

    /**
     * Returns the name of the player.
     *
     * @return the player's name
     */
    public @NonNull String name() {
        return this.name;
    }

    /**
     * Returns true if the player's name should be visible, or false if not.
     *
     * @return a boolean
     */
    public boolean showName() {
        return this.showName;
    }

    /**
     * Returns the {@link UUID} of the player.
     *
     * @return the player's {@link UUID}
     */
    public @NonNull UUID uuid() {
        return this.uuid;
    }

    /**
     * {@code Builder} builds {@link PlayerOptions} instances.
     */
    public static final class Builder {

        private @NonNull Position position;
        private @Nullable String name;
        private @Nullable UUID uuid;
        private boolean showName;
        private @Nullable String skinTexture;
        private @Nullable String skinSignature;

        private Builder(final @NonNull Position position) {
            this.position = position;
        }

        /**
         * Removes the player's skin.
         *
         * @return this
         */
        public @NonNull Builder noSkin() {
            this.skinTexture = null;
            this.skinSignature = null;
            return this;
        }

        /**
         * Sets the player's skin.
         *
         * @param texture   the skin texture
         * @param signature the skin texture's signature
         * @return this
         */
        public @NonNull Builder skin(final @NonNull String texture,
                                     final @NonNull String signature) {
            this.skinTexture = texture;
            this.skinSignature = signature;
            return this;
        }

        /**
         * Sets the position of the entity.
         *
         * @param position the position
         * @return this
         */
        public @NonNull Builder position(final @NonNull Position position) {
            this.position = position;
            return this;
        }

        /**
         * Sets the name of the player.
         *
         * @param name the name
         * @return this
         * @apiNote if not called, a random name will be supplied to the {@link PlayerOptions}
         */
        public @NonNull Builder name(final @NonNull String name) {
            this.name = name;
            return this;
        }

        /**
         * Sets the {@link UUID} of the player.
         *
         * @param uuid the uuid
         * @return this
         * @apiNote if not called, a random {@link UUID} will be supplied to the {@link PlayerOptions}
         */
        public @NonNull Builder uuid(final @NonNull UUID uuid) {
            this.uuid = uuid;
            return this;
        }

        /**
         * Sets if the player's name should be visible.
         *
         * @param showName a boolean
         * @return this
         */
        public @NonNull Builder showName(final boolean showName) {
            this.showName = showName;
            return this;
        }

        /**
         * Builds a new {@link PlayerOptions} instance.
         *
         * @return the {@link PlayerOptions}
         * @throws RuntimeException if an expected value is null
         */
        public @NonNull PlayerOptions build() {
            return new PlayerOptions(
                    Objects.requireNonNullElse(this.name, "player-" + Numbers.between(0, 100)),
                    Objects.requireNonNullElse(this.uuid, UUID.randomUUID()),
                    this.position,
                    this.showName,
                    this.skinTexture,
                    this.skinSignature
            );
        }

    }

}
