package mod.syconn.starwars.util.handlers;

import mod.syconn.starwars.Config;
import mod.syconn.starwars.init.ModFeatures;
import mod.syconn.starwars.world.gen.feature.configs.CrystalCaveConfig;
import mod.syconn.starwars.world.gen.feature.structure.CrystalCave;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;

public class StructureHandler {

    public static void createStructures()
    {
        for (Biome biome : Biome.BIOMES){
            addCrystalCave(biome);
        }
    }

    private static void addCrystalCave(Biome biome){
        ConfiguredFeature<CrystalCaveConfig, ? extends Structure<CrystalCaveConfig>> crystalCave = ModFeatures.CRYSTAL_CAVE.get().withConfiguration(new CrystalCaveConfig(Config.COMMON.crystalCaveGenerationChance.get(), CrystalCave.CRYSTAL_CAVE));
        biome.addStructure(crystalCave);
        biome.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, ModFeatures.CRYSTAL_CAVE.get().withConfiguration(new CrystalCaveConfig(5, CrystalCave.CRYSTAL_CAVE)).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
    }
}
