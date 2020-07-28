package mod.syconn.starwars.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import mod.syconn.starwars.init.ModFeatures;
import mod.syconn.starwars.world.gen.feature.configs.CrystalCaveConfig;
import mod.syconn.starwars.world.gen.feature.structure.CrystalCave;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Function;

public class CrystalCaveStructure extends Structure<CrystalCaveConfig> {

    public CrystalCaveStructure(Function<Dynamic<?>, ? extends CrystalCaveConfig> configFactoryIn) {
        super(configFactoryIn);
    }

    @Override
    public boolean canBeGenerated(BiomeManager manager, ChunkGenerator<?> generator, Random rand, int chunkX, int chunkZ, Biome biome)
    {
        if(generator.hasStructure(biome, this))
        {
            ((SharedSeedRandom) rand).setLargeFeatureSeedWithSalt(generator.getSeed(), chunkX, chunkZ, 0xF00D);
            CrystalCaveConfig config = generator.getStructureConfig(biome, this);
            return config != null && rand.nextInt(config.chance) == 0;
        }
        return false;
    }

    @Nullable
    @Override
    public BlockPos findNearest(World worldIn, ChunkGenerator<? extends GenerationSettings> chunkGenerator, BlockPos pos, int radius, boolean p_211405_5_)
    {
        return super.findNearest(worldIn, chunkGenerator, pos, radius, p_211405_5_);
    }

    @Override
    public IStartFactory getStartFactory()
    {
        return CrystalCaveStart::new;
    }

    @Override
    public String getStructureName()
    {
        return this.getRegistryName().toString();
    }

    @Override
    public int getSize()
    {
        return 1;
    }

    public static class CrystalCaveStart extends StructureStart
    {
        public CrystalCaveStart(Structure<?> structure, int chunkX, int chunkZ, MutableBoundingBox bounds, int references, long seed)
        {
            super(structure, chunkX, chunkZ, bounds, references, seed);
        }

        @Override
        public void init(ChunkGenerator<?> generator, TemplateManager manager, int chunkX, int chunkZ, Biome biome)
        {
            int posX = chunkX << 4;
            int posZ = chunkZ << 4;
            int height1 = generator.func_222532_b(posX + 3, posZ + 3, Heightmap.Type.OCEAN_FLOOR_WG);
            int height2 = generator.func_222532_b(posX + 13, posZ + 3, Heightmap.Type.OCEAN_FLOOR_WG);
            int height3 = generator.func_222532_b(posX + 3, posZ + 13, Heightmap.Type.OCEAN_FLOOR_WG);
            int height4 = generator.func_222532_b(posX + 13, posZ + 13, Heightmap.Type.OCEAN_FLOOR_WG);
            if(height1 == height2 && height1 == height3 && height1 == height4)
            {
                BlockPos pos = new BlockPos(posX + 3, 10, posZ + 3);
                Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
                this.components.add(new CrystalCave.Piece(manager, pos, rotation));
                this.recalculateStructureSize();
            }
        }

        @Override
        public BlockPos getPos()
        {
            return new BlockPos((this.getChunkPosX() << 4) + 9, 0, (this.getChunkPosZ() << 4) + 9);
        }
    }
}
