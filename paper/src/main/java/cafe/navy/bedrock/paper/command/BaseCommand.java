package cafe.navy.bedrock.paper.command;

import cloud.commandframework.ArgumentDescription;
import cloud.commandframework.Command;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.meta.CommandMeta;
import cloud.commandframework.paper.PaperCommandManager;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * A {@code BaseComamnd} provides an ID, and lifecycle methods to register commands.
 */
public interface BaseCommand {

    /**
     * The base name of the command.
     *
     * @return the command name
     */
    @NonNull String name();

    /**
     * Returns the permission for this command.
     *
     * @return the permission
     */
    default @Nullable String permission() {
        return null;
    };

    /**
     * Any additional aliases for the command.
     *
     * @return the command aliases
     */
    default @NonNull String[] aliases() {
        return new String[0];
    }

    /**
     * All {@code SubCommand}s.
     *
     * @return the subcommands
     */
    default @NonNull SubCommand[] subCommands() {
        return new SubCommand[0];
    }

    /**
     * Returns the description of the command.
     *
     * @return the command description
     */
    default @NonNull String description() {
        return "/" + this.name();
    }

    /**
     * Creates a builder of the root command.
     *
     * @param commandManager the command manager
     * @return the builder
     */
    default Command.@NonNull Builder<CommandSender> createBuilder(final @NonNull PaperCommandManager<CommandSender> commandManager) {
        final String name = this.name();
        final Collection<String> aliases = new ArrayList<>();
        Collections.addAll(aliases, this.aliases());

        return commandManager
                .commandBuilder(name, aliases, ArgumentDescription.of(this.description()), CommandMeta.simple().build())
                .handler(this::handle);
    }

    default void handle(final @NonNull CommandContext<CommandSender> ctx) {
        ctx.getSender().sendMessage("You executed /" + this.name() + ".");
    }

    /**
     * Registers commands with the command manager.
     *
     * @param manager the command manager
     */
    default void registerCommands(final @NonNull PaperCommandManager<CommandSender> manager) {
        final Command.Builder<CommandSender> builder = this.createBuilder(manager);
        manager.command(builder);
        this.registerSubCommands(manager, builder);
    }

    default void registerSubCommand(final @NonNull SubCommand command,
                                    final @NonNull PaperCommandManager<CommandSender> manager,
                                    final Command.@NonNull Builder<CommandSender> builder) {
        command.registerCommand(manager, builder.literal(command.name()));
    }

    private void registerSubCommands(final @NonNull PaperCommandManager<CommandSender> manager,
                                     final Command.@NonNull Builder<CommandSender> builder) {
        final SubCommand[] commands = this.subCommands();
        if (commands != null) {
            for (final SubCommand command : commands) {
                command.registerCommand(manager, builder
                        .literal(command.name(), command.aliases()));
            }
        }
    }

}
