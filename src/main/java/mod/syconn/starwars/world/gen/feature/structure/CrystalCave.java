package mod.syconn.starwars.world.gen.feature.structure;

import mod.syconn.starwars.init.ModStructurePieceType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public class CrystalCave {

    public static class Piece extends StructurePiece{

        public Piece(BlockPos pos)
        {
            super(ModStructurePieceType.CRYSTAL_CAVE, 0);
            this.boundingBox = new MutableBoundingBox(pos.getX(), pos.getY(), pos.getZ(), pos.getX(), pos.getY(), pos.getZ());
        }

        public Piece(TemplateManager manager, CompoundNBT compound)
        {
            super(ModStructurePieceType.CRYSTAL_CAVE, compound);
        }

        @Override
        protected void readAdditional(CompoundNBT compound) {}

        @Override
        public boolean create(IWorld world, ChunkGenerator<?> generator, Random rand, MutableBoundingBox bounds, ChunkPos chunkPos)
        {
            return true;
        }
    }
}