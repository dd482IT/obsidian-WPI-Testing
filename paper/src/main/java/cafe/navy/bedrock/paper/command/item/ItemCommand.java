package cafe.navy.bedrock.paper.command.item;

import cafe.navy.bedrock.paper.Server;
import cafe.navy.bedrock.paper.command.BaseCommand;
import cloud.commandframework.paper.PaperCommandManager;
import org.bukkit.command.CommandSender;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ItemCommand implements BaseCommand {

    private final @NonNull Server server;

    public ItemCommand(final @NonNull Server server) {
        this.server = server;
    }

    @Override
    public @NonNull String name() {
        return "item";
    }

    @Override
    public void registerCommands(final @NonNull PaperCommandManager<CommandSender> manager) {
        final var builder = this.createBuilder(manager);
        this.registerSubCommand(new GiveSubCommand(this.server.items()), manager, builder);
    }


}
