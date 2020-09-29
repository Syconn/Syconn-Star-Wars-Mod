package mod.syconn.starwars.item;

import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TranslationTextComponent;

public class KyberCrystalItem extends Item {

    public KyberCrystalItem(Properties prop) {
        super(prop);
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (isInGroup(group)) {
            for (DyeColor color : DyeColor.values()) {
                ItemStack stack = new ItemStack(this);
                LightsaberItem.setLightsaberColor(stack, color.getFireworkColor());
                //stack.setDisplayName(new TranslationTextComponent("item.kyber." + color.getName()));
                items.add(stack);
            }
        }
    }
}
