package mod.syconn.starwars.util.helpers;

import net.minecraft.block.TNTBlock;
import net.minecraft.command.impl.FillCommand;
import net.minecraft.command.impl.SetBlockCommand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class TriggerHelper {

    public static void createExplosion(World world, BlockPos posIn){
        world.createExplosion(null, posIn.getX(), posIn.getY(), posIn.getZ(), 3, Explosion.Mode.BREAK);
    }
}
