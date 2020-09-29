package mod.syconn.starwars;

import mod.syconn.starwars.capability.ForcePowers;
import mod.syconn.starwars.client.gui.ForcePowersOverlay;
import mod.syconn.starwars.client.gui.SidePickerScreen;
import mod.syconn.starwars.client.renderer.entity.BlasterBoltRender;
import mod.syconn.starwars.client.renderer.entity.StormTrooperRender;
import mod.syconn.starwars.init.*;
import mod.syconn.starwars.network.PacketHandler;
import mod.syconn.starwars.util.handlers.ColorHandlers;
import mod.syconn.starwars.util.handlers.InputHandler;
import mod.syconn.starwars.client.gui.LightsaberScreen;
import mod.syconn.starwars.util.Reference;
import mod.syconn.starwars.util.handlers.StructureHandler;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
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
        MinecraftForge.EVENT_BUS.register(this);
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.clientSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.commonSpec);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.serverSpec);
        ModFeatures.REGISTER.register(bus);
        ModStructurePieceType.init();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ColorHandlers::registerItemColor);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ColorHandlers::registerBlockColor);

        FMLJavaModLoadingContext.get().getModEventBus().addListener(ColorHandlers::registerItemColor);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ColorHandlers::registerBlockColor);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        PacketHandler.register();
        MinecraftForge.EVENT_BUS.register(new ForcePowersOverlay());
        MinecraftForge.EVENT_BUS.register(new InputHandler());
        ForcePowers.register();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ColorHandlers::registerItemColor);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ColorHandlers::registerBlockColor);

        StructureHandler.createStructures();
    }

    private void clientSetup(FMLClientSetupEvent event)
    {
        ScreenManager.registerFactory(ModContainers.LIGHTSABER_CRAFTER, LightsaberScreen::new);
        ScreenManager.registerFactory(ModContainers.SIDE_PICKER, SidePickerScreen::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.BLASTER_BOLT_ENTITY, BlasterBoltRender::new);
        RenderingRegistry.registerEntityRenderingHandler(ModEntities.STORMTROPER, StormTrooperRender::new);
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
