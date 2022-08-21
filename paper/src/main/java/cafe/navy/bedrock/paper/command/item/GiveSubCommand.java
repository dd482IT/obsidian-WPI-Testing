package cafe.navy.bedrock.paper.command.item;

import cafe.navy.bedrock.paper.command.SubCommand;
import cafe.navy.bedrock.paper.item.Item;
import cafe.navy.bedrock.paper.item.ItemManager;
import cafe.navy.bedrock.paper.item.ItemType;
import cafe.navy.bedrock.paper.message.Message;
import cloud.commandframework.Command;
import cloud.commandframework.CommandManager;
import cloud.commandframework.arguments.standard.StringArgument;
import cloud.commandframework.bukkit.parsers.PlayerArgument;
import cloud.commandframework.context.CommandContext;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

public class GiveSubCommand implements SubCommand {

    private final @NonNull ItemManager manager;
    private final @NonNull StringArgument<CommandSender> typeArg;

    public GiveSubCommand(final @NonNull ItemManager manager) {
        this.manager = manager;
        this.typeArg = StringArgument
                .<CommandSender>newBuilder("type")
                .withSuggestionsProvider((ctx, in) -> {
                    final List<String> ids = new ArrayList<>();
                    for (final ItemType type : this.manager.types()) {
                        ids.add(type.id());
                    }
                    return ids;
                })
                .build();
    }

    @Override
    public @NonNull String name() {
        return "give";
    }

    @Override
    public @Nullable String[] aliases() {
        return new String[]{};
    }

    @Override
    public void registerCommand(@NonNull CommandManager<CommandSender> manager, Command.@NonNull Builder<CommandSender> builder) {
        final var list = builder
                .literal("list")
                .handler(this::handleList);

        final var give = builder
                .literal("item")
                .argument(this.typeArg.copy())
                .argument(PlayerArgument.optional("player"))
                .handler(this::handleGive);

        manager.command(list);
        manager.command(give);
    }

    private void handleList(final @NonNull CommandContext<CommandSender> ctx) {
        for (final var type : this.manager.types()) {
            ctx.getSender().sendMessage(Message.create()
                    .highlight(type.id()));
        }
    }

    private void handleGive(final @NonNull CommandContext<CommandSender> ctx) {
        final var typeOpt = this.manager.getType(ctx.get("type"));
        if (typeOpt.isEmpty()) {
            ctx.getSender().sendMessage(Message.create("No type!"));
            return;
        }
        final var type = typeOpt.get();

        Player player = null;
        if (ctx.contains("player")) {
            player = ctx.get("player");
        } else if (ctx.getSender() instanceof Player) {
            player = (Player) ctx.getSender();
        }

        if (player == null) {
            ctx.getSender().sendMessage(Message.create("No target!"));
            return;
        }

        final Item item = this.manager.createItem(type);
        player.getInventory().addItem(this.manager.getItemStack(item));
    }
}
