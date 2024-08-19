package com.oxtaly.changeraidwavesdifficulty.commands;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import com.oxtaly.changeraidwavesdifficulty.ChangeRaidWavesDifficulty;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.oxtaly.changeraidwavesdifficulty.Config;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.raid.Raid;
import net.minecraft.world.entity.raid.Raider;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SetDifficultyCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        LiteralArgumentBuilder<CommandSourceStack> literalArgumentBuilder = Commands
                .literal("raidwaves")
                .requires(ctx -> {
                    try { return ctx.getPlayerOrException().hasPermissions(2); }
                    catch (CommandSyntaxException e)
                    {
                        e.printStackTrace();
                        return false;
                    }
                })
                // Debug commands!
                // .then(Commands.literal("stopraid")
                //         .executes(css -> {
                //             ServerPlayer player = css.getSource().getPlayerOrException();
                //             ServerLevel level = player.serverLevel();
                //             Raid raid = level.getRaidAt(player.blockPosition());
                //
                //             if(raid == null) {
                //                 css.getSource().sendFailure(Component.literal("You must be near a Raid to use this command!"));
                //                 return 0;
                //             }
                //
                //             List<Raider> aliveRaiders = raid.getAllRaiders().stream().filter(LivingEntity::isAlive).toList();
                //             aliveRaiders.forEach(LivingEntity::kill);
                //             raid.stop();
                //
                //             css.getSource().sendSuccess(() -> Component.literal("Stopped nearby raid!"), true);
                //             return 1;
                //         })
                // )
                // .then(Commands.literal("nextwave")
                //         .executes(css -> {
                //             ServerPlayer player = css.getSource().getPlayerOrException();
                //             ServerLevel level = player.serverLevel();
                //             Raid raid = level.getRaidAt(player.blockPosition());
                //
                //             if(raid == null) {
                //                 css.getSource().sendFailure(Component.literal("You must be near a Raid to use this command!"));
                //                 return 0;
                //             }
                //
                //             List<Raider> aliveRaiders = raid.getAllRaiders().stream().filter(LivingEntity::isAlive).toList();
                //             aliveRaiders.forEach(LivingEntity::kill);
                //
                //             css.getSource().sendSuccess(() -> Component.literal("Cleared current raid wave!"), true);
                //             return 1;
                //         })
                // )
                .then(Commands.literal("setdifficulty")
                        .then(Commands.literal("default").executes((ctx) -> SetDifficultyCommand.setDifficulty(ctx, null)))
                        .then(Commands.literal("peaceful").executes((ctx) -> SetDifficultyCommand.setDifficulty(ctx, Difficulty.PEACEFUL)))
                        .then(Commands.literal("easy").executes((ctx) -> SetDifficultyCommand.setDifficulty(ctx, Difficulty.EASY)))
                        .then(Commands.literal("normal").executes((ctx) -> SetDifficultyCommand.setDifficulty(ctx, Difficulty.NORMAL)))
                        .then(Commands.literal("hard").executes((ctx) -> SetDifficultyCommand.setDifficulty(ctx, Difficulty.HARD)))

                );
                // .then(Commands.literal("getdifficulty")
                //         .executes((ctx) -> {
                //             int configdifficulty = Config.RaidDifficulty.get();
                //             ChangeRaidWavesDifficulty.LOGGER.info("Hello from config difficulty: {}", configdifficulty);
                //             ctx.getSource().sendSuccess(() -> Component.literal("Hello from config difficulty: ").append(Integer.toString(configdifficulty)), true);
                //             return 1;
                //         })
                //
                // );
                // .executes(css -> {
                //     ChangeRaidWavesDifficulty.LOGGER.info("Hello from command!");
                //     css.getSource().sendSuccess(() -> Component.literal("Hello from command!"), true);
                //     return 1;
                // });

        dispatcher.register(literalArgumentBuilder);
    }

    public static int setDifficulty(CommandContext<CommandSourceStack> ctx, @Nullable Difficulty difficulty) {

        int configDifficulty;
        Component displayDifficulty;
        if(Objects.isNull(difficulty)) {
            configDifficulty = -1;
            displayDifficulty = Component.literal("default");
        }
        else {
            configDifficulty = switch (difficulty) {
                case PEACEFUL -> 0;
                case EASY -> 1;
                case NORMAL -> 2;
                case HARD -> 3;
                default -> -1;
            };
            displayDifficulty = difficulty.getDisplayName();
        }

        Config.RaidDifficulty.set(configDifficulty);
        Config.RaidDifficulty.save();

        ctx.getSource().sendSuccess(() -> Component.literal("Set raid waves difficulty to ").append(displayDifficulty).append("!"), true);
        return 1;
    }
}
