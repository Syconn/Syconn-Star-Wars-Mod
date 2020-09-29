package mod.syconn.starwars.init;

import mod.syconn.starwars.util.Reference;
import mod.syconn.starwars.world.gen.feature.CrystalCaveStructure;
import mod.syconn.starwars.world.gen.feature.configs.CrystalCaveConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> REGISTER = new DeferredRegister<>(ForgeRegistries.FEATURES, Reference.MOD_ID);

    public static final RegistryObject<CrystalCaveStructure> CRYSTAL_CAVE = REGISTER.register("crystal_cave", () -> new CrystalCaveStructure(CrystalCaveConfig::deserialize));
}