package mod.syconn.starwars.util.handlers;

import mod.syconn.starwars.init.ModRecipes;
import mod.syconn.starwars.item.KyberCrystalItem;
import mod.syconn.starwars.util.Helpers.RecipeHelper;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class CraftingHandler {

    public static ItemStack Craft(IInventory inventory){
        ModRecipes.RegisterRecipes();

        for (int i = 0; i < ModRecipes.recipes.size(); i++){
            RecipeHelper recipe = ModRecipes.recipes.get(i);
            System.out.println("Checking Recipes");

            if (recipe.matches(inventory)) {
                System.out.println("Matches");
                return recipe.createLightsaber((KyberCrystalItem) inventory.getStackInSlot(1).getItem());
            }
        }

        return ItemStack.EMPTY;
    }
}
