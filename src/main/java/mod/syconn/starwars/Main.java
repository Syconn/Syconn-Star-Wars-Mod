package mod.syconn.starwars;

import mod.syconn.starwars.block.LightsaberCrafterBlock;
import mod.syconn.starwars.capability.ForcePowers;
import mod.syconn.starwars.capability.interfaces.IForceSensitive;
import mod.syconn.starwars.client.gui.ForcePowersOverlay;
import mod.syconn.starwars.client.gui.SidePickerScreen;
import mod.syconn.starwars.client.renderer.entity.BlasterBoltRender;
import mod.syconn.starwars.init.*;
import mod.syconn.starwars.network.PacketHandler;
import mod.syconn.starwars.util.handlers.ColorHandlers;
import mod.syconn.starwars.util.handlers.EventHandler;
import mod.syconn.starwars.util.handlers.InputHandler;
import mod.syconn.starwars.client.gui.LightsaberScreen;
import mod.syconn.starwars.util.Reference;
import mod.syconn.starwars.util.handlers.StructureHandler;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Reference.MOD_ID)
public class Main
{
    public Main() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.commonSpec);
        ModFeatures.REGISTER.register(bus);
        ModStructurePieceType.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ColorHandlers::registerItemColor);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ColorHandlers::registerBlockColor);

        MinecraftForge.EVENT_BUS.register(this);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ColorHandlers::registerItemColor);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ColorHandlers::registerBlockColor);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new ForcePowersOverlay());
        MinecraftForge.EVENT_BUS.register(new InputHandler());
        PacketHandler.register();
        ModCapabilities.register();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ColorHandlers::registerItemColor);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ColorHandlers::registerBlockColor);

        StructureHandler.createStructures();
    }

    private void clientSetup(FMLClientSetupEvent event)
    {
        ScreenManager.registerFactory(ModContainers.LIGHTSABER_CRAFTER, LightsaberScreen::new);
        ScreenManager.registerFactory(ModContainers.SIDE_PICKER, SidePickerScreen::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BLASTER_BOLT_ENTITY, BlasterBoltRender::new);
    }

    public static class StarWarsGroup extends ItemGroup {
        public static final ItemGroup instance = new StarWarsGroup(ItemGroup.GROUPS.length, "starwarstab");

        private StarWarsGroup(int index, String label) {
            super(index, label);
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.LIGHTSABER_CRAFTER);
        }
    }
}
