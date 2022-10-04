package cafe.navy.obsidian.paper.npc;

import cafe.navy.obsidian.core.player.GamePlayer;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Consumer;

public interface NPC {

    @NonNull String id();

    boolean hasViewer(final @NonNull GamePlayer player);

    void addViewer(final @NonNull GamePlayer player);

    void removeViewer(final @NonNull GamePlayer player);

    void modifyState(final @NonNull GamePlayer player,
                     final @NonNull Consumer<NPCState> consumer);

    void modifyStates(final @NonNull Consumer<NPCState> consumer);

    void activate();

    void deactivate();

    boolean isActive();

}
