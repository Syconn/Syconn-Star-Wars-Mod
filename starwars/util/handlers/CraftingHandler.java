package mod.syconn.starwars.util.handlers;

import mod.syconn.starwars.init.ModRecipes;
import mod.syconn.starwars.util.helpers.RecipeHelper;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class CraftingHandler {

    public static boolean CanCraft(IInventory inventory){
        ModRecipes.RegisterRecipes();

        for (int i = 0; i < ModRecipes.recipes.size(); i++){
            RecipeHelper recipe = ModRecipes.recipes.get(i);

            if (recipe.matches(inventory)) {
                return true;
            }
        }

        return false;
    }

    public static ItemStack getOutput(IInventory inventory){
        ModRecipes.RegisterRecipes();

        for (int i = 0; i < ModRecipes.recipes.size(); i++){
            RecipeHelper recipe = ModRecipes.recipes.get(i);

            if (recipe.matches(inventory)) {
                return recipe.createLightsaber(inventory.getStackInSlot(1));
            }
        }

        return ItemStack.EMPTY;
    }
}
