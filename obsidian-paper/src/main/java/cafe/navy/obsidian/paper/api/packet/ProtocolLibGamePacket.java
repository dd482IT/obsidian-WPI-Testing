package cafe.navy.obsidian.paper.api.packet;

import cafe.navy.obsidian.core.client.GameClient;
import cafe.navy.obsidian.core.client.GamePacket;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.lang.reflect.InvocationTargetException;

public class ProtocolLibGamePacket implements GamePacket {

    private final @NonNull PacketContainer packet;

    public ProtocolLibGamePacket(final @NonNull PacketContainer packet) {
        this.packet = packet;
    }

    @Override
    public void send(final @NonNull GameClient client) {
        try {
            ProtocolLibrary.getProtocolManager().sendServerPacket(Bukkit.getPlayer(client.uuid()), this.packet);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
