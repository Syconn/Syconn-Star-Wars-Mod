package mod.syconn.starwars.util.helpers;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

public class BlockUtils {

    public static Block getBlock(IWorldReader world, BlockPos pos){
        return world.getBlockState(pos).getBlock();
    }

    public static Block getBlock(World world, BlockPos pos){
        return world.getBlockState(pos).getBlock();
    }
}
