package mod.syconn.starwars.block;

import mod.syconn.starwars.init.ModItems;
import mod.syconn.starwars.item.LightsaberItem;
import mod.syconn.starwars.util.handlers.ColorHandlers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.GlassBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ILightReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class KyberCrystalBlock extends Block {

    private static final EnumProperty<DyeColor> COLOR = EnumProperty.create("crystal", DyeColor.class);

    private static final VoxelShape Shape = Block.makeCuboidShape(6, 0, 6, 10, 7, 10);

    public KyberCrystalBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(COLOR, ColorHandlers.CrystalColorUtil()));
    }

    @Override
    public boolean isTransparent(BlockState state) {
        return true;
    }

    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    public boolean canEntitySpawn(BlockState state, IBlockReader worldIn, BlockPos pos, EntityType<?> type) {
        return false;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return Shape;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(COLOR, ColorHandlers.CrystalColorUtil());
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(COLOR);
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
    public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBlockHarvested(worldIn, pos, state, player);

        if (player.abilities.isCreativeMode) {
            return;
        }

        ItemStack lightsaber = ModItems.KYBER_CRYSTAL.getDefaultInstance();
        LightsaberItem.setLightsaberColor(lightsaber.getStack(), state.get(COLOR).getFireworkColor());
        ItemStack itemstack = lightsaber.getStack();

        ItemEntity itementity = new ItemEntity(worldIn, (double)pos.getX(), (double)pos.getY(), (double)pos.getZ(), itemstack);
        itementity.setDefaultPickupDelay();
        worldIn.addEntity(itementity);
    }
}
