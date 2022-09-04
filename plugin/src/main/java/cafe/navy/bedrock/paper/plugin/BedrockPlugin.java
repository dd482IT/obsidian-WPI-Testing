package cafe.navy.bedrock.paper.plugin;

import cafe.navy.bedrock.paper.core.Server;
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
