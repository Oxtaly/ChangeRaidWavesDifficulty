package com.oxtaly.changeraidwavesdifficulty;

import com.mojang.logging.LogUtils;
import com.oxtaly.changeraidwavesdifficulty.commands.SetDifficultyCommand;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
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
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        // modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SPEC);
    }

    // private void commonSetup(final FMLCommonSetupEvent event)
    // {
    //     // Some common setup code
    //     LOGGER.info("HELLO FROM COMMON SETUP");
    //     LOGGER.info("Raid Difficulty in config: {}", Config.RaidDifficulty.get());
    // }

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

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    // @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    // public static class ClientModEvents
    // {
    //     @SubscribeEvent
    //     public static void onClientSetup(FMLClientSetupEvent event)
    //     {
    //         // Some client setup code
    //         LOGGER.info("HELLO FROM CLIENT SETUP");
    //         LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
    //     }
    // }
}
