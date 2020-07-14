package mod.syconn.starwars;

import mod.syconn.starwars.block.LightsaberCrafterBlock;
import mod.syconn.starwars.init.*;
import mod.syconn.starwars.item.LightsaberItem;
import mod.syconn.starwars.util.handlers.ColorHandlers;
import mod.syconn.starwars.util.handlers.InputHandler;
import mod.syconn.starwars.client.gui.LightsaberScreen;
import mod.syconn.starwars.util.Reference;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Reference.MOD_ID)
public class Main
{
    public Main() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
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
        MinecraftForge.EVENT_BUS.register(new InputHandler());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ColorHandlers::registerItemColor);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ColorHandlers::registerBlockColor);

        Biome.BIOMES.forEach(biome -> {

            //for (DyeColor color : DyeColor.values()) {
                BlockState blockState = ModBlocks.KYBER_CRYSTAL.getDefaultState();
                LightsaberCrafterBlock.setCrystalColorColor(blockState, ColorHandlers.CrystalColorUtil());

                ConfiguredFeature<OreFeatureConfig, ? extends Structure<OreFeatureConfig>> crystalCave = ModFeatures.CRYSTAL_CAVE.get().withConfiguration(new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, blockState, 5));
                biome.addStructure(crystalCave);
                biome.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, crystalCave.withPlacement(Placement.COUNT_DEPTH_AVERAGE.configure(new DepthAverageConfig(2, 50, 4))));
            //}
        });
    }

    private void clientSetup(final FMLClientSetupEvent event)
    {
        ScreenManager.registerFactory(ModContainers.LIGHTSABER_CRAFTER, LightsaberScreen::new);
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
