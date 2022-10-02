package cafe.navy.obsidian.paper.api.packet;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import it.unimi.dsi.fastutil.ints.IntImmutableList;

import java.util.List;

public class WrapperPlayServerEntityDestroy extends AbstractPacket {
    public static final PacketType TYPE = PacketType.Play.Server.ENTITY_DESTROY;

    public WrapperPlayServerEntityDestroy() {
        super(new PacketContainer(TYPE), TYPE);
        handle.getModifier().writeDefaults();
    }

    public WrapperPlayServerEntityDestroy(PacketContainer packet) {
        super(packet, TYPE);
    }

    /**
     * Retrieve Count.
     * <p>
     * Notes: length of following array
     *
     * @return The current Count
     */
    public int getCount() {
        return handle.getIntLists().read(0).size();
    }

    /**
     * Retrieve Entity IDs.
     * <p>
     * Notes: the list of entities of destroy
     *
     * @return The current Entity IDs
     */
    public List<Integer> getEntityIDs() {
        return handle.getIntLists().read(0);
    }

    /**
     * Set Entity IDs.
     *
     * @param value - new value.
     */
    public void setEntityIds(int[] value) {
        handle.getIntLists().write(0, new IntImmutableList(value));
    }

}