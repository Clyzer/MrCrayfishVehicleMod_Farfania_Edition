package com.mrcrayfish.vehicle;

import com.mrcrayfish.vehicle.client.ClientHandler;
import com.mrcrayfish.vehicle.common.CommonEvents;
import com.mrcrayfish.vehicle.common.FluidNetworkHandler;
import com.mrcrayfish.vehicle.common.ItemLookup;
import com.mrcrayfish.vehicle.common.entity.HeldVehicleDataHandler;
import com.mrcrayfish.vehicle.datagen.LootTableGen;
import com.mrcrayfish.vehicle.datagen.RecipeGen;
import com.mrcrayfish.vehicle.datagen.VehiclePropertiesGen;
import com.mrcrayfish.vehicle.entity.VehicleProperties;
import com.mrcrayfish.vehicle.init.*;
import com.mrcrayfish.vehicle.network.PacketHandler;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Author: MrCrayfish
 */
@Mod(Reference.MOD_ID)
public class VehicleMod
{
    public static final Logger LOGGER = LogManager.getLogger(Reference.MOD_ID);
    public static final ItemGroup CREATIVE_TAB = new ItemGroup(Reference.MOD_ID)
    {
        @Override
        public ItemStack makeIcon()
        {
            return new ItemStack(ModItems.IRON_SMALL_ENGINE.get());
        }
    };

    public VehicleMod()
    {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBlocks.REGISTER.register(eventBus);
        ModItems.REGISTER.register(eventBus);
        ModEntities.REGISTER.register(eventBus);
        ModTileEntities.REGISTER.register(eventBus);
        ModContainers.REGISTER.register(eventBus);
        ModSounds.REGISTER.register(eventBus);
        ModRecipeSerializers.REGISTER.register(eventBus);
        ModFluids.REGISTER.register(eventBus);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, VehicleConfig.serverSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, VehicleConfig.clientSpec);
        eventBus.addListener(this::onCommonSetup);
        eventBus.addListener(this::onClientSetup);
        eventBus.addListener(this::onGatherData);
        MinecraftForge.EVENT_BUS.register(new CommonEvents());
        MinecraftForge.EVENT_BUS.register(FluidNetworkHandler.instance());
    }

    private void onCommonSetup(FMLCommonSetupEvent event)
    {
        VehicleProperties.loadProperties();
        PacketHandler.register();
        HeldVehicleDataHandler.register();
        ItemLookup.init();
        ModDataKeys.register();
        ModLootFunctions.init();
    }

    private void onClientSetup(FMLClientSetupEvent event)
    {
        ClientHandler.setup();
    }

    private void onGatherData(GatherDataEvent event)
    {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(new LootTableGen(generator));
        generator.addProvider(new RecipeGen(generator));
        generator.addProvider(new VehiclePropertiesGen(generator));
    }
}
