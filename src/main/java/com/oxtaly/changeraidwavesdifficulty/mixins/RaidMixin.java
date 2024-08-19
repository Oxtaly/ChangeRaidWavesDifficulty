package com.oxtaly.changeraidwavesdifficulty.mixins;

import com.oxtaly.changeraidwavesdifficulty.ChangeRaidWavesDifficulty;
import com.oxtaly.changeraidwavesdifficulty.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.minecraft.world.entity.raid.Raid;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Raid.class)
public abstract class RaidMixin {
    @Mutable @Shadow @Final private int numGroups;
    @Shadow public abstract int getNumGroups(Difficulty difficulty);

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
