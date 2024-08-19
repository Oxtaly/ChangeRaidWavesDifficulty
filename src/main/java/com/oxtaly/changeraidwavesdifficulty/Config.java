package com.oxtaly.changeraidwavesdifficulty;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

// An oxtaly config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = ChangeRaidWavesDifficulty.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.IntValue RaidDifficulty = BUILDER
            .comment(" Difficulty for the raid waves to use.\n Use -1 to use the current minecraft difficulty.")
            .defineInRange("raidDifficulty", -1, -1, 3);

    static final ForgeConfigSpec SPEC = BUILDER.build();
    // public static int raidDifficulty;

    // @SubscribeEvent
    // static void onLoad(final ModConfigEvent event) {
    //     raidDifficulty = RaidDifficulty.get();
    // }
}
