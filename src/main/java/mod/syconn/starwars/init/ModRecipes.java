package mod.syconn.starwars.init;

import mod.syconn.starwars.item.KyberCrystalItem;
import mod.syconn.starwars.util.Helpers.RecipeHelper;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ModRecipes {

    public static final List<RecipeHelper> recipes = new ArrayList<>();

    public static void RegisterRecipes(){
        RecipeHelper LIGHTSABER = register(new RecipeHelper(), ModItems.STEEL_INGOT, ModItems.KYBER_CRYSTAL, Items.STICK, ModItems.LIGHTSABER);
    }

    private static RecipeHelper register(RecipeHelper recipe, Item slot0, Item crystal, @Nullable Item slot2, Item result){
        recipes.add(recipe);
        recipe.createCrafter(slot0, crystal, slot2, result);
        return recipe;
    }
}
