package mod.syconn.starwars.util.handlers;

import mod.syconn.starwars.network.PacketHandler;
import mod.syconn.starwars.network.message.MessageTick;
import mod.syconn.starwars.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    public static final ResourceLocation PLAYER_DATA = new ResourceLocation(Reference.MOD_ID, "capability/playerdatafactory.class");

    @SubscribeEvent
    public static void Tick(TickEvent.ClientTickEvent event){
        ClientPlayerEntity player = Minecraft.getInstance().player;

        if (player != null && player.isAlive()) {
            PacketHandler.instance.sendToServer(new MessageTick());
        }
    }

//    @SubscribeEvent
//    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
//        if(event.getObject() instanceof PlayerEntity)
//        {
//            event.addCapability(PLAYER_DATA, new ForceProvider());
//        }
//    }
//
//    @SubscribeEvent
//    public void onPlayerClone(PlayerEvent.Clone event)
//    {
//        PlayerEntity player = event.getPlayer();
//        IForceSensitive jedi = player.getCapability(CapabilityForceSensetive.FORCE_CAPABILITY, null).orElseThrow(IllegalStateException::new);
//        IForceSensitive oldJedi = event.getOriginal().getCapability(CapabilityForceSensetive.FORCE_CAPABILITY, null).orElseThrow(IllegalStateException::new);
//
//        jedi.setMaxStamina(oldJedi.getMaxStamina());
}

