package mod.syconn.starwars.block;

import mod.syconn.starwars.init.ModBlocks;
import mod.syconn.starwars.item.LightsaberItem;
import mod.syconn.starwars.tileentity.TileEntityBomb;
import mod.syconn.starwars.util.handlers.ColorHandlers;
import mod.syconn.starwars.util.helpers.TriggerHelper;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.stream.Stream;

public class Bomb extends Block {

    public static final BooleanProperty FLASH = BooleanProperty.create("flash");

    public static final VoxelShape SHAPE = Stream.of(
            Block.makeCuboidShape(8, 2, 5, 10, 3, 8),
            Block.makeCuboidShape(7, 0, 4, 14, 1, 10),
            Block.makeCuboidShape(7, 1, 5, 14, 2, 9),
            Block.makeCuboidShape(9, 1, 3, 12, 2, 5),
            Block.makeCuboidShape(10, 2, 4, 11, 3, 10),
            Block.makeCuboidShape(9, 1, 9, 12, 2, 11),
            Block.makeCuboidShape(10, 3, 5, 11, 4, 9),
            Block.makeCuboidShape(8, 0, 10, 13, 1, 11),
            Block.makeCuboidShape(8, 0, 3, 13, 1, 4)
            ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    public Bomb() {
        super(Block.Properties.create(Material.IRON));
        this.setDefaultState(stateContainer.getBaseState().with(FLASH, true));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    public static void StartExplosion(PlayerEntity player, BlockState state, World world, BlockPos pos){
        if (state.get(FLASH)) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof TileEntityBomb) {
                ((TileEntityBomb) te).start(player);
            }
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FLASH, true);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FLASH);
        super.fillStateContainer(builder);
    }


    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);

        if (player.abilities.isCreativeMode) {
            return;
        };

        TriggerHelper.createExplosion(worldIn, pos);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new TileEntityBomb();
    }
}
