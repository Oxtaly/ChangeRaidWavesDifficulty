package com.oxtaly.changeraidwavesdifficulty;

import com.mojang.logging.LogUtils;
import com.oxtaly.changeraidwavesdifficulty.commands.SetDifficultyCommand;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ChangeRaidWavesDifficulty.MODID)
public class ChangeRaidWavesDifficulty
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "changeraidwavesdifficulty";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public ChangeRaidWavesDifficulty() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SPEC);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("[changeraidwavesdifficulty] Mod loading!");
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        SetDifficultyCommand.register(event.getDispatcher());
        LOGGER.info("[changeraidwavesdifficulty] Registered commands!");
    }
}
