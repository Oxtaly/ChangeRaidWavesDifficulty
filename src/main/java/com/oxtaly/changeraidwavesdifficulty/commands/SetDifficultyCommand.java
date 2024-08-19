package com.oxtaly.changeraidwavesdifficulty.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.oxtaly.changeraidwavesdifficulty.Config;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Difficulty;

import javax.annotation.Nullable;
import java.util.Objects;

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
                .then(Commands.literal("setdifficulty")
                        .then(Commands.literal("default").executes((ctx) -> SetDifficultyCommand.setDifficulty(ctx, null)))
                        .then(Commands.literal("peaceful").executes((ctx) -> SetDifficultyCommand.setDifficulty(ctx, Difficulty.PEACEFUL)))
                        .then(Commands.literal("easy").executes((ctx) -> SetDifficultyCommand.setDifficulty(ctx, Difficulty.EASY)))
                        .then(Commands.literal("normal").executes((ctx) -> SetDifficultyCommand.setDifficulty(ctx, Difficulty.NORMAL)))
                        .then(Commands.literal("hard").executes((ctx) -> SetDifficultyCommand.setDifficulty(ctx, Difficulty.HARD)))

                );

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
