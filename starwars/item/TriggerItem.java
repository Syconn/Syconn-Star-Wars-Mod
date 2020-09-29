package mod.syconn.starwars.item;

import mod.syconn.starwars.Main;
import mod.syconn.starwars.block.Bomb;
import mod.syconn.starwars.client.gui.BombMenuScreen;
import mod.syconn.starwars.util.helpers.BlockUtils;
import mod.syconn.starwars.util.helpers.TriggerHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.monster.GhastEntity;
import net.minecraft.entity.monster.GiantEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import javax.naming.CompoundName;
import java.util.ArrayList;
import java.util.List;

public class TriggerItem extends Item {

    public TriggerItem() {
        super(new Item.Properties().group(Main.StarWarsGroup.instance).maxStackSize(1));
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {

        for (int i = 0; i < 6; i++){
            int[] coords = new int[3];
            coords[0] = 0;
            coords[1] = 0;
            coords[2] = 0;

            stack.getOrCreateTag().putIntArray("bomb" + i, coords);
        }
        return null;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerIn, Hand handIn) {
        if(world.isRemote) {
            Minecraft.getInstance().displayGuiScreen(new BombMenuScreen(new TranslationTextComponent("bombs"), world, playerIn.getHeldItem(handIn)));
        }
        return ActionResult.resultPass(playerIn.getHeldItem(handIn));
    }

    @Override
    public ActionResultType onItemUseFirst(ItemStack stack, ItemUseContext ctx)
    {
        return onItemUseFirst(ctx.getPlayer(), ctx.getWorld(), ctx.getPos(), stack, ctx.getFace(), ctx.getHitVec().x, ctx.getHitVec().y, ctx.getHitVec().z);
    }

    public ActionResultType onItemUseFirst(PlayerEntity player, World world, BlockPos pos, ItemStack stack, Direction facing, double hitX, double hitY, double hitZ){
        if(!world.isRemote)
        {
            int[] coord = new int[3];
            coord[0] = pos.getX();
            coord[1] = pos.getY();
            coord[2] = pos.getZ();

            int num = getNextSlot(world, stack, coord);

            System.out.println(pos.getY());

            if(world.getBlockState(pos).getBlock() instanceof Bomb) {
                System.out.println("true");
                if (num == 6)
                    return ActionResultType.FAIL;

                int[] coords = new int[3];

                coords[0] = pos.getX();
                coords[1] = pos.getY();
                coords[2] = pos.getZ();

                stack.getTag().putIntArray("bomb" + num, coords);
                System.out.println(stack.getTag().getIntArray("bomb" + num)[1]);
            }
        }
        else if(!(BlockUtils.getBlock(world, pos) instanceof Bomb))
            Minecraft.getInstance().displayGuiScreen(new BombMenuScreen(new TranslationTextComponent("bombs"), world, stack));

        return ActionResultType.SUCCESS;
    }

    private List<int[]> getBombs(World world, ItemStack stack){
        List<int[]> BOMBS = new ArrayList<>();

        for (int i = 1; i < 6; i++){
            if (world.getBlockState(new BlockPos(stack.getOrCreateTag().getIntArray("bomb" + i)[0], stack.getOrCreateTag().getIntArray("bomb" + i)[1], stack.getOrCreateTag().getIntArray("bomb" + i)[2])).getBlock() instanceof Bomb)
                BOMBS.add(stack.getOrCreateTag().getIntArray("bomb" + i));
        }

        return BOMBS;
    }

    private int getNextSlot(World world, ItemStack stack, int[] coord){
        //
        for (int i = 0; i < 6; i++){
            int[] coords = stack.getTag().getIntArray("bomb" + i);

            if (!(BlockUtils.getBlock(world, new BlockPos(coords[0], coords[1], coords[2])) instanceof Bomb))
                stack.getTag().putIntArray("bomb" + i, resetCoords());
        }

        for (int i = 0; i < 6; i++){
            int[] coords = stack.getOrCreateTag().getIntArray("bomb" + i);
            System.out.println(coords[1]);

            if (coord[0] == coords[0] && coord[1] == coords[1] && coord[2] == coords[2])
                return 6;

            if (coords[1] == 0)
                return i;
        }
        return 6;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if(stack.getTag() == null)
            return;

        if(world != null) {

            for (int i = 0; i < 6; i++) {
                int[] coords = stack.getTag().getIntArray("bomb" + i);

                if (!(BlockUtils.getBlock(world, new BlockPos(coords[0], coords[1], coords[2])) instanceof Bomb))
                    stack.getTag().putIntArray("bomb" + i, resetCoords());
            }
        }

        for (int i = 0; i < 6; i++){
            int[] coords = stack.getTag().getIntArray("bomb" + i);

            if (coords[1] != 0)
                tooltip.add(new StringTextComponent( TextFormatting.GREEN + "Num: " + (i + 1) + " X: " + stack.getOrCreateTag().getIntArray("bomb" + i)[0] + " Y: " + stack.getOrCreateTag().getIntArray("bomb" + i)[1] + " Z: " +stack.getOrCreateTag().getIntArray("bomb" + i)[2]));
        }
    }

    private int[] resetCoords(){
        int[] coord = new int[3];
        coord[0] = 0;
        coord[1] = 0;
        coord[2] = 0;
        return coord;
    }
}