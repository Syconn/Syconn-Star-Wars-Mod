package mod.syconn.starwars.util.handlers;

import mod.syconn.starwars.init.ModRecipeSerializers;
import mod.syconn.starwars.util.Reference;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryHandler {

    @SubscribeEvent
    public static void registerRecipeSerializers(final RegistryEvent.Register<IRecipeSerializer<?>> event) {
        event.getRegistry().registerAll
                (
                    ModRecipeSerializers.LIGHTSABER_CRAFTING_RECIPE
                );
    }
}
