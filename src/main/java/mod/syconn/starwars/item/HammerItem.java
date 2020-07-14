package mod.syconn.starwars.item;

import mod.syconn.starwars.Main;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;

public class HammerItem extends Item {

    public HammerItem() {
        super(new Item.Properties().group(Main.StarWarsGroup.instance).defaultMaxDamage(79).setNoRepair());
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }
}
