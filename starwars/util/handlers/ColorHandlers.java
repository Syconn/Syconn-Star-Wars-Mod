package mod.syconn.starwars.util.handlers;

import mod.syconn.starwars.block.KyberCrystalBlock;
import mod.syconn.starwars.block.LightsaberCrafterBlock;
import mod.syconn.starwars.init.ModBlocks;
import mod.syconn.starwars.item.LightsaberItem;
import mod.syconn.starwars.init.ModItems;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ColorHandlerEvent;

import java.util.Random;

public class ColorHandlers {

    private static final String NBT_COLOR = "LightsaberColor";

    public static void registerItemColor(ColorHandlerEvent.Item event){
        event.getItemColors().register(LightsaberItem::getItemColor, ModItems.LIGHTSABER, ModItems.KYBER_CRYSTAL);
    }

    public static void registerBlockColor(final ColorHandlerEvent.Block event)
    {
        event.getBlockColors().register(LightsaberCrafterBlock::getBlockColor, ModBlocks.LIGHTSABER_CRAFTER, ModBlocks.KYBER_CRYSTAL);
    }

    public static DyeColor CrystalColorUtil(){
        Random random = new Random();
        int i = random.nextInt(16);

        return DyeColor.byId(i);
    }

    public static int getItemColor(ItemStack stack){
        return stack.getOrCreateTag().getInt(NBT_COLOR);
    }

    public static void setItemColor(ItemStack stack, int color){
        stack.getOrCreateTag().putInt(NBT_COLOR, color);
    }

    public static void setItemColor(ItemStack stack, ItemStack stacksColor){
        stack.getOrCreateTag().putInt(NBT_COLOR, getItemColor(stacksColor));
    }
}