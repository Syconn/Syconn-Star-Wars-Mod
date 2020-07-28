package mod.syconn.starwars.util.handlers;

import mod.syconn.starwars.capability.ForcePowers;
import mod.syconn.starwars.capability.interfaces.IForceSensitive;
import mod.syconn.starwars.client.gui.SidePickerScreen;
import mod.syconn.starwars.init.ModCapabilities;
import mod.syconn.starwars.init.ModContainers;
import mod.syconn.starwars.util.Reference;
import mod.syconn.starwars.util.enums.ForceSideEnum;
import net.minecraft.block.EnderChestBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MinecraftGame;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ChestContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.NetworkHooks;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    public static final ResourceLocation PLAYER_DATA = new ResourceLocation(Reference.MOD_ID, "capability/playerdatafactory.class");

    @SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if(event.getObject() instanceof PlayerEntity)
        {
            event.addCapability(PLAYER_DATA, new ForcePowers());
        }
    }

    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent event) {
        IForceSensitive data = event.player.getCapability(ModCapabilities.FORCE_CAPABILITY).orElseThrow(IllegalStateException::new);
        data.tick(data);
    }

//    @SubscribeEvent
//    public static void OnJoinedWorld(EntityJoinWorldEvent event){
//        if (event.getEntity() instanceof PlayerEntity) {
//            PlayerEntity player = (PlayerEntity) event.getEntity();
//            IForceSensitive data = player.getCapability(ModCapabilities.FORCE_CAPABILITY).orElseThrow(IllegalStateException::new);
//
//            if(!player.getEntityWorld().isRemote)
//            {
//                if (data.getSide() == ForceSideEnum.BOTH)
//                {
//                    ScreenManager.openScreen(ModContainers.SIDE_PICKER, Minecraft.getInstance(), 64, new TranslationTextComponent("Side Picker"));
//                }
//            }
//        }
//    }
}
