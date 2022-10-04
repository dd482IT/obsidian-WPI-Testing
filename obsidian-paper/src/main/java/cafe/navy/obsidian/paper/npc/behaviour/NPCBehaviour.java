package cafe.navy.obsidian.paper.npc.behaviour;

import cafe.navy.obsidian.core.player.GamePlayer;
import cafe.navy.obsidian.paper.npc.NPCState;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface NPCBehaviour {

    void tick(final @NonNull NPCState state);

}
