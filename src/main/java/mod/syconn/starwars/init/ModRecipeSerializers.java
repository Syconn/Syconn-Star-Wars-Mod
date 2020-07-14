package mod.syconn.starwars.init;

import mod.syconn.starwars.crafting.LightsaberRecipeShapless;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;

public interface ModRecipeSerializers<T extends IRecipe<?>> extends IRecipeSerializer<T> {

    IRecipeSerializer<LightsaberRecipeShapless> LIGHTSABER_CRAFTING_RECIPE = register("lightsaber", new LightsaberRecipeShapless.Serializer());

    static <S extends IRecipeSerializer<T>, T extends IRecipe<?>> S register(String key, S recipeSerializer) {
        return IRecipeSerializer.register(key, recipeSerializer);
    }
}
