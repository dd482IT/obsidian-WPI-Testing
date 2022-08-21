package cafe.navy.bedrock.paper;

import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class BedrockPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        final Server server = new Server(this);
        server.enable();
        this.getLogger().info("Enabled server");
        this.getServer().getServicesManager().register(Server.class, server, this, ServicePriority.Highest);
        this.getLogger().info("Registered server");
    }

}
