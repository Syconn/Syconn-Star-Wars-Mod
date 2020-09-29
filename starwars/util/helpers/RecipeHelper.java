package mod.syconn.starwars.util.helpers;

import mod.syconn.starwars.init.ModItems;
import mod.syconn.starwars.item.LightsaberItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;

public class RecipeHelper {

    private Item item0;
    private Item item1;
    private Item item2;

    private Item resultItem;

    public void createCrafter(Item slot0, Item slot1, @Nullable Item slot2, Item result){
        this.item0 = slot0;
        this.item1 = slot1;
        this.item2 = slot2;
        resultItem = result;
    }

    public boolean matches(IInventory inv){
        //System.out.println(inv.getStackInSlot(0).getItem() + " " + item0 + " " + inv.getStackInSlot(1).getItem() + " " + item1 + " " + inv.getStackInSlot(2).getItem() + " " + item2);

        return inv.getStackInSlot(0).getItem() == item0 && inv.getStackInSlot(1).getItem() == item1 && inv.getStackInSlot(2).getItem() == item2;
    }

    public ItemStack createLightsaber(ItemStack crystal){
        ItemStack lightsaber = resultItem.getDefaultInstance();
        LightsaberItem.setLightsaberColor(lightsaber, LightsaberItem.getLightsaberColor(crystal));
        return lightsaber;
    }
}
