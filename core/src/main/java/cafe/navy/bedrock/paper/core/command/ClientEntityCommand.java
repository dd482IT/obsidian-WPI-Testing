package cafe.navy.bedrock.paper.core.command;

import cafe.navy.bedrock.paper.core.Server;
import cafe.navy.bedrock.paper.core.entity.ClientEntity;
import cafe.navy.bedrock.paper.core.message.Message;
import cloud.commandframework.context.CommandContext;
import cloud.commandframework.paper.PaperCommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public class ClientEntityCommand implements BaseCommand {

    private final @NonNull Server server;

    public ClientEntityCommand(final @NonNull Server server) {
        this.server = server;
    }

    @Override
    public @NonNull String name() {
        return "entities";
    }

    @Override
    public @NonNull String[] aliases() {
        return new String[]{
                "cliententity",
                "ce"
        };
    }

    @Override
    public void registerCommands(final @NonNull PaperCommandManager<CommandSender> manager) {
        final var builder = this.createBuilder(manager);

        final var list = builder
                .literal("list")
                .handler(this::handleList);

        manager.command(list);
    }

    private void handleList(final @NonNull CommandContext<CommandSender> ctx) {
        if (!(ctx.getSender() instanceof Player player)) {
            return;
        }

        final UUID worldUuid = player.getWorld().getUID();
        final var realmOpt = this.server.getRealm(worldUuid);
        if (realmOpt.isEmpty()) {
            return;
        }

        for (final ClientEntity entity : realmOpt.get().entityList()) {
            player.sendMessage(Message.create()
                    .main("- ")
                    .highlight(entity.getClass().getSimpleName())
                    .main(": ")
                    .link(entity.uuid().toString()));
        }
    }


}
