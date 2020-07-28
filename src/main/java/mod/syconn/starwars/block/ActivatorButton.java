package mod.syconn.starwars.block;

import net.minecraft.block.*;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class ActivatorButton extends AbstractButtonBlock
{
    public ActivatorButton(boolean isWooden, Properties properties) {
        super(isWooden, properties);
    }

    @Override
    protected SoundEvent getSoundEvent(boolean p_196369_1_) {
        return p_196369_1_ ? SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON : SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF;
    }

    public int getWeakPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return blockState.get(POWERED) ? 8 : 0;
    }

    public int getStrongPower(BlockState blockState, IBlockReader blockAccess, BlockPos pos, Direction side) {
        return blockState.get(POWERED) && getFacing(blockState) == side ? 8 : 0;
    }

    public int getLightValue(BlockState state) {
        return 8;
    }
}
