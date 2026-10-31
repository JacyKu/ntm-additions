package com.ntmadditions.command;

import com.hbm.config.WorldConfig;
import com.hbm.handler.BossSpawnHandler;
import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;
import java.util.List;

/**
 * Admin command for triggering NTM's natural meteor strike path.
 *
 * <p>This deliberately calls {@link BossSpawnHandler#strike(ServerLevel, net.minecraft.world.entity.player.Player)}
 * rather than spawning an {@code EntityMeteor} directly. That means the meteor gets NTM's normal spawn height,
 * random offset, fall speed and terrain impact, and the usual charm checks (including this addon's Curios
 * variants) still apply.
 */
public final class MeteorCommand {

    private MeteorCommand() {}

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("ntmadditions")
                .requires(source -> source.hasPermission(2))
                .then(Commands.literal("meteor")
                        .executes(ctx -> strike(ctx.getSource(),
                                List.of(ctx.getSource().getPlayerOrException())))
                        .then(Commands.argument("targets", EntityArgument.players())
                                .executes(ctx -> strike(ctx.getSource(),
                                        EntityArgument.getPlayers(ctx, "targets"))))));
    }

    private static int strike(CommandSourceStack source, Collection<ServerPlayer> targets) {
        if (!WorldConfig.enableMeteorStrikes) {
            source.sendFailure(Component.translatable("command.ntmadditions.meteor.disabled"));
            return 0;
        }

        int spawned = 0;
        for (ServerPlayer target : targets) {
            if (BossSpawnHandler.strike(target.serverLevel(), target) != null) {
                spawned++;
            }
        }

        int count = spawned;
        int total = targets.size();
        if (count == 0) {
            source.sendSuccess(() -> Component.translatable("command.ntmadditions.meteor.prevented"), true);
        } else {
            source.sendSuccess(() -> Component.translatable(
                    "command.ntmadditions.meteor.success", count, total), true);
        }
        return count;
    }
}
