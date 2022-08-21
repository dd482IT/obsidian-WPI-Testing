package cafe.navy.bedrock.paper.command.impl;

import cafe.navy.bedrock.paper.Server;
import cafe.navy.bedrock.paper.command.BaseCommand;
import cafe.navy.bedrock.paper.message.Message;
import cafe.navy.bedrock.paper.message.Messages;
import cafe.navy.bedrock.paper.player.PlayerTarget;
import cafe.navy.bedrock.paper.target.Target;
import cloud.commandframework.paper.PaperCommandManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class BedrockCommand implements BaseCommand {

    private final @NonNull Server server;

    public BedrockCommand(final @NonNull Server server) {
        this.server = server;
    }

    @Override
    public @NonNull String name() {
        return "base";
    }

    @Override
    public void registerCommands(final @NonNull PaperCommandManager<CommandSender> manager) {
        final var builder = this.createBuilder(manager);
        final var viewing = builder
                .literal("debug")
                .literal("viewing")
                .handler(ctx -> {
                    if (!(ctx.getSender() instanceof Player player)) {
                        ctx.getSender().sendMessage(Messages.NO_PLAYER);
                        return;
                    }

                    final var targetOpt = this.server.players().getPlayer(player.getUniqueId());
                    if (targetOpt.isEmpty()) {
                        throw new RuntimeException();
                    }

                    final var target = targetOpt.get();
                    final var list = target.viewing();

                    player.sendMessage(Message.create()
                            .main("Viewing ")
                            .highlight(Integer.toString(list.size()))
                    );

                    for (final var entity : list) {
                        player.sendMessage(Message.create(entity.uuid().toString())
                                .highlight(" " + entity.getClass().getSimpleName()));
                    }
                });

        final var players = builder
                .literal("debug")
                .literal("players")
                .handler(ctx -> {
                    for (final PlayerTarget player : this.server.players().players()) {
                        ctx.getSender().sendMessage(Message.create()
                                .main(" - " + player.name()));
                    }
                });

        manager.command(builder);
        manager.command(viewing);
        manager.command(players);
    }
}