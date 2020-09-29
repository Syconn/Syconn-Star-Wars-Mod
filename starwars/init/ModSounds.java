package mod.syconn.starwars.init;

import mod.syconn.starwars.util.Reference;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModSounds {

    private static final List<SoundEvent> SOUNDS = new ArrayList<>();

    public static final SoundEvent BLASTER_SHOOT = register("blaster.shoot");

    private static SoundEvent register(String id){
        SoundEvent sound = new SoundEvent(new ResourceLocation(Reference.MOD_ID, id));
        sound.setRegistryName(new ResourceLocation(Reference.MOD_ID, id));
        SOUNDS.add(sound);
        return sound;
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerSounds(final RegistryEvent.Register<SoundEvent> event){
        for (SoundEvent sound : SOUNDS)
            event.getRegistry().register(sound);
    }
}
