package cafe.navy.obsidian.paper.plugin;

import cafe.navy.obsidian.paper.plugin.commands.ObsidianDebugCommands;
import me.lucko.helper.plugin.ExtendedJavaPlugin;
import me.lucko.helper.plugin.HelperPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class ObsidianPlugin extends ExtendedJavaPlugin {

    @Override
    public void enable() {
        new ObsidianDebugCommands(this).register();
    }

}
