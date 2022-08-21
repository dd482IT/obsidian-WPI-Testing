package cafe.navy.bedrock.paper.command;

import cloud.commandframework.CommandManager;
import cloud.commandframework.bukkit.CloudBukkitCapabilities;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Function;

/**
 * Allows one to register and unregister {@link BaseCommand}s.
 */
public class CommandRegistry {

    private final @NonNull JavaPlugin plugin;
    private final @NonNull PaperCommandManager<CommandSender> manager;

    /**
     * Constructs a {@code CommandRegistry}.
     *
     * @param plugin the plugin
     * @throws Exception if an error occurred while constructing the {@link CommandManager<CommandSender>}
     */
    public CommandRegistry(final @NonNull JavaPlugin plugin) throws Exception {
        this.plugin = plugin;
        final @NonNull Function<CommandSender, CommandSender> mapper = Function.identity();

        this.manager = new PaperCommandManager<>(
                this.plugin,
                AsynchronousCommandExecutionCoordinator.simpleCoordinator(),
                mapper,
                mapper
        );

        if (this.manager.queryCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            this.manager.registerAsynchronousCompletions();
        }

        this.manager.setSetting(CommandManager.ManagerSettings.OVERRIDE_EXISTING_COMMANDS, true);
    }

    public void addCommand(final @NonNull BaseCommand command) {
        command.registerCommands(this.manager);
    }

}
