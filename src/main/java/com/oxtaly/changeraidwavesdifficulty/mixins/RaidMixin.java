package com.oxtaly.changeraidwavesdifficulty.mixins;

import com.oxtaly.changeraidwavesdifficulty.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.raid.Raid;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import com.oxtaly.changeraidwavesdifficulty.ChangeRaidWavesDifficulty;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(Raid.class)
public abstract class RaidMixin {

    @Mutable @Shadow @Final private int numGroups;
    @Shadow public abstract int getNumGroups(Difficulty difficulty);


    // @Inject(at = @At("HEAD"), method = "getNumGroups", cancellable = true)
    // @Overwrite
    // public int getNumGroups(Difficulty difficulty) {
    //     ChangeRaidWavesDifficulty.LOGGER.info("Hello from mixin! Difficulty: {}", difficulty);
    //     int value = switch (difficulty) {
    //         case EASY -> 3;
    //         case NORMAL -> 5;
    //         case HARD -> 7;
    //         default -> 0;
    //     };
    //     return 7;
    //     // ci.setReturnValue(value);
    // }

    // @Inject(method = "Raid(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/entity/raid/Raid", at = @At("TAIL"))
    @Inject(method = "<init>(ILnet/minecraft/server/level/ServerLevel;Lnet/minecraft/core/BlockPos;)V", at = @At("RETURN"))
    public void modifyNumGroups(int id, ServerLevel level, BlockPos center, CallbackInfo ci) {
        this.numGroups = switch (Config.RaidDifficulty.get()) {
            case -1 -> this.getNumGroups(level.getDifficulty());
            case 0 -> this.getNumGroups(Difficulty.PEACEFUL);
            case 1 -> this.getNumGroups(Difficulty.EASY);
            case 2 -> this.getNumGroups(Difficulty.NORMAL);
            case 3 -> this.getNumGroups(Difficulty.HARD);
            default -> {
                ChangeRaidWavesDifficulty.LOGGER.error("Unexpected value: " + Config.RaidDifficulty.get());
                yield this.getNumGroups(level.getDifficulty());
            }
        };
    }
}
