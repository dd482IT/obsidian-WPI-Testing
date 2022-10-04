package cafe.navy.obsidian.paper.plugin.commands;

import cafe.navy.obsidian.core.client.GameClient;
import cafe.navy.obsidian.core.util.Position;
import cafe.navy.obsidian.paper.BukkitAdapter;
import cafe.navy.obsidian.paper.api.client.PaperGameClient;
import cafe.navy.obsidian.paper.api.client.PaperGamePlayer;
import cafe.navy.obsidian.paper.entity.renderer.EntityRenderer;
import cafe.navy.obsidian.paper.entity.renderer.type.player.PlayerOptions;
import cafe.navy.obsidian.paper.entity.renderer.type.player.PlayerRenderer;
import cafe.navy.obsidian.paper.npc.NPC;
import cafe.navy.obsidian.paper.npc.NPCState;
import cafe.navy.obsidian.paper.npc.SimpleNPC;
import cafe.navy.obsidian.paper.npc.behaviour.LookAtBehaviour;
import cloud.commandframework.execution.AsynchronousCommandExecutionCoordinator;
import cloud.commandframework.paper.PaperCommandManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.function.Function;

public class ObsidianDebugCommands {

    private final @NonNull JavaPlugin plugin;

    public ObsidianDebugCommands(final @NonNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void register() {
        try {
            final var commandManager = new PaperCommandManager<>(
                    this.plugin, AsynchronousCommandExecutionCoordinator.simpleCoordinator(),
                    Function.identity(), Function.identity()
            );

            final var debugBuilder = commandManager.commandBuilder("od", "obsidiandebug");
            commandManager.command(debugBuilder
                    .literal("entity")
                    .literal("player-named")
                    .handler(ctx -> {
                        if (!(ctx.getSender() instanceof Player player)) {
                            return;
                        }

                        final GameClient client = new PaperGameClient(player);
                        final String name = player.getName();
                        final Position position = BukkitAdapter.adapt(player.getLocation());

                        final PlayerOptions options = PlayerOptions.builder(position)
                                .name(name)
                                .showName(true)
                                .build();

                        final PlayerRenderer renderer = new PlayerRenderer(options);

                        renderer.show(client);
                    }));

            commandManager.command(debugBuilder
                    .literal("npc")
                    .literal("test")
                    .literal("a")
                    .handler(ctx -> {
                        if (!(ctx.getSender() instanceof Player player)) {
                            return;
                        }

                        final Position position = BukkitAdapter.adapt(player.getLocation());
                        final EntityRenderer<?> renderer = PlayerRenderer.of(PlayerOptions.builder(position).build());

                        final NPC a = SimpleNPC.builder(this.plugin, "NPC-test")
                                .stateGenerator((viewer) -> NPCState.builder(position, player.getWorld())
                                        .behaviour(LookAtBehaviour.nearest(10))
                                        .name("hi, " + viewer.name() + "!")
                                        .renderer(renderer)
                                        .build())
                                .tickSpeed(5)
                                .build();

                        a.activate();
                        a.addViewer(new PaperGamePlayer(player));
                    }));

            commandManager.command(debugBuilder
                    .literal("npc")
                    .literal("test")
                    .literal("b")
                    .handler(ctx -> {
                        if (!(ctx.getSender() instanceof Player player)) {
                            return;
                        }

                        final Position position = BukkitAdapter.adapt(player.getLocation());
                        final EntityRenderer<?> renderer = PlayerRenderer.of(PlayerOptions.builder(position).build());

                        final NPC b = SimpleNPC.builder(this.plugin, "NPC-test")
                                .defaultState(NPCState.builder(position, player.getWorld())
                                        .behaviour(LookAtBehaviour.nearest(10))
                                        .names("BBBBBBBRUH MOMENTO!", "", "line2")
                                        .renderer(renderer)
                                        .build())
                                .tickSpeed(5)
                                .build();

                        b.activate();
                        b.addViewer(new PaperGamePlayer(player));
                    }));


            commandManager.command(debugBuilder
                    .literal("entity")
                    .literal("player-nameless")
                    .handler(ctx -> {
                        if (!(ctx.getSender() instanceof Player player)) {
                            return;
                        }

                        final PlayerRenderer renderer = new PlayerRenderer(PlayerOptions
                                .builder(BukkitAdapter.adapt(player.getLocation()))
                                .showName(true)
                                .build());

                        renderer.show(new PaperGameClient(player));
                    }));

            commandManager.command(debugBuilder
                    .literal("entity")
                    .literal("start-spam")
                    .handler(ctx -> {
                        if (!(ctx.getSender() instanceof Player player)) {
                            return;
                        }

                        final GameClient client = new PaperGameClient(player);

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                final PlayerRenderer renderer = new PlayerRenderer(PlayerOptions
                                        .builder(BukkitAdapter.adapt(player.getLocation()))
                                        .uuid(player.getUniqueId())
                                        .build());

                                renderer.show(client);
                            }
                        }.runTaskTimer(this.plugin, 0, 1);
                    }));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

}
