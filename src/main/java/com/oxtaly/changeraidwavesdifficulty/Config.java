package com.oxtaly.changeraidwavesdifficulty;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ChangeRaidWavesDifficulty.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.IntValue RaidDifficulty = BUILDER
            .comment(" Difficulty for the raid waves to use.\n Use -1 to use the current minecraft difficulty.")
            .defineInRange("raidDifficulty", -1, -1, 3);

    static final ForgeConfigSpec SPEC = BUILDER.build();
}
