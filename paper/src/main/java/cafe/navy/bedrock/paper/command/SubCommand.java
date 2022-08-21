package cafe.navy.bedrock.paper.command;

import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A {@code SubCommand} represents a
 */
public interface SubCommand {

    @NonNull String name();

    @Nullable String[] aliases();

    void registerCommand(final @NonNull CommandManager<CommandSender> manager,
                         final Command.@NonNull Builder<CommandSender> builder);

}
