package mod.syconn.starwars.block;

import mod.syconn.starwars.containers.LightsaberCrafterContainer;
import mod.syconn.starwars.init.ModBlocks;
import mod.syconn.starwars.init.ModItems;
import mod.syconn.starwars.item.LightsaberItem;
import mod.syconn.starwars.util.handlers.ColorHandlers;
import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.state.*;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ILightReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraftforge.common.extensions.IForgeBlockState;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Stream;

import static mod.syconn.starwars.item.LightsaberItem.getLightsaberColor;

public class LightsaberCrafterBlock extends Block {

    private static final TranslationTextComponent CONTAINER_TITLE = new TranslationTextComponent("container.lightsaber.crafter");
    private static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    private static final EnumProperty<DyeColor> COLOR = EnumProperty.create("crystal", DyeColor.class);


    private static final VoxelShape Shape_N = Stream.of(
            Block.makeCuboidShape(0, 0, 0, 3, 13, 3),
            Block.makeCuboidShape(7, 10, 4, 13, 12, 6),
            Block.makeCuboidShape(13, 13, 12.5, 13.8, 14, 13.5),
            Block.makeCuboidShape(10.2, 13, 12.5, 11, 14, 13.5),
            Block.makeCuboidShape(11, 13, 12, 13, 14, 14),
            Block.makeCuboidShape(7, 10, 9, 9, 12, 14),
            Block.makeCuboidShape(5, 10, 4, 6, 11, 10),
            Block.makeCuboidShape(3, 13, 4, 4, 14, 10),
            Block.makeCuboidShape(2, 13, 2, 5, 15, 4),
            Block.makeCuboidShape(0, 9, 0, 16, 10, 16),
            Block.makeCuboidShape(0, 12, 0, 16, 13, 16),
            Block.makeCuboidShape(0, 0, 13, 3, 13, 16),
            Block.makeCuboidShape(13, 0, 13, 16, 13, 16),
            Block.makeCuboidShape(13, 0, 0, 16, 13, 3)
    ).reduce((v1, v2) -> {return VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR);}).get();

    public LightsaberCrafterBlock(Block.Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(FACING, Direction.NORTH).with(COLOR, DyeColor.WHITE));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Shape_N;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite()).with(COLOR, ColorHandlers.CrystalColorUtil());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING, COLOR);
        super.fillStateContainer(builder);
    }

    public static int getCrystalColor(BlockState state){
        return state.get(COLOR).getFireworkColor();
    }

    public static void setCrystalColorColor(BlockState state, DyeColor color){
        state.with(COLOR, color);
    }

    public static int getBlockColor(BlockState state, @Nullable ILightReader blockAccess, @Nullable BlockPos pos, int tintIndex){
        if (tintIndex == 0 && state != null) {
            setCrystalColorColor(state, ColorHandlers.CrystalColorUtil());
            return getCrystalColor(state);
        }

        return 0xFFFFFF;
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult result) {
        if(!worldIn.isRemote)
        {
            player.openContainer(state.getContainer(worldIn, pos));
        }
        return ActionResultType.SUCCESS;
    }

    @Nullable
    @Override
    public INamedContainerProvider getContainer(BlockState state, World world, BlockPos pos)
    {
        return new SimpleNamedContainerProvider((windowId, playerInventory, playerEntity) -> {
            return new LightsaberCrafterContainer(windowId, playerInventory, IWorldPosCallable.of(world, pos));
        }, CONTAINER_TITLE);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);

        if (player.abilities.isCreativeMode) {
            return;
        }

        ItemStack lightsaber = ModBlocks.LIGHTSABER_CRAFTER.getItem(worldIn, pos, state);
        LightsaberItem.setLightsaberColor(lightsaber.getStack(), DyeColor.GREEN.getFireworkColor());
        ItemStack itemstack = lightsaber.getStack();

        ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), itemstack);
        itementity.setDefaultPickupDelay();
        worldIn.addEntity(itementity);
    }
}