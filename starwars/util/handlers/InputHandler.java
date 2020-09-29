package mod.syconn.starwars.util.handlers;

import mod.syconn.starwars.capability.ForcePowers;
import mod.syconn.starwars.item.LightsaberItem;
import mod.syconn.starwars.network.PacketHandler;
import mod.syconn.starwars.network.message.MessageChangeLightsaberState;
import mod.syconn.starwars.network.message.MessageForcePower;
import mod.syconn.starwars.network.message.MessageRefresh;
import mod.syconn.starwars.network.message.MessageTick;
import mod.syconn.starwars.util.enums.PowersEnum;
import mod.syconn.starwars.util.powers.ForcePower;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class InputHandler {

    //TODO To get keyBinds go to InputMappings

    private final KeyBinding KEY_ACTIVATE = new KeyBinding("key.swm.activate", 264, "key.swm.starwars");
    private boolean keyPressed_Activated = false;
    private final KeyBinding FORCE_ABILITY_ONE = new KeyBinding("key.ability.one", 263, "key.swm.starwars");
    private boolean keyPressed_Ability_One = false;
    private final KeyBinding FORCE_ABILITY_TWO = new KeyBinding("key.ability.two", 265, "key.swm.starwars");
    private boolean keyPressed_Ability_Two = false;
    private final KeyBinding FORCE_ABILITY_THREE = new KeyBinding("key.ability.three", 262, "key.swm.starwars");
    private boolean keyPressed_Ability_Three = false;
    private final KeyBinding REFRESH_STAMINA = new KeyBinding("key.refresh.stamina", 82, "key.swm.starwars");
    private boolean keyPressed_Refresh_Stamina = false;

    public InputHandler(){
        //ClientRegistry.registerKeyBinding(KEY_ACTIVATE);
        ClientRegistry.registerKeyBinding(FORCE_ABILITY_ONE);
        ClientRegistry.registerKeyBinding(FORCE_ABILITY_TWO);
        ClientRegistry.registerKeyBinding(FORCE_ABILITY_THREE);
        ClientRegistry.registerKeyBinding(REFRESH_STAMINA);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {

        if(FORCE_ABILITY_ONE.isPressed())
        {
            PacketHandler.instance.sendToServer(new MessageForcePower(PowersEnum.PUSH.getId()));
        }

        if(FORCE_ABILITY_TWO.isPressed())
        {
            PacketHandler.instance.sendToServer(new MessageForcePower(PowersEnum.EPICENTER.getId()));
        }

        if(FORCE_ABILITY_THREE.isPressed())
        {
            PacketHandler.instance.sendToServer(new MessageForcePower(PowersEnum.HEAL.getId()));
        }

        if (REFRESH_STAMINA.isPressed()){
            keyPressed_Refresh_Stamina = true;
        }
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;

        if (keyPressed_Activated) {
            keyPressed_Activated = false;
            PacketHandler.instance.sendToServer(new MessageChangeLightsaberState());
        }

        if (keyPressed_Refresh_Stamina) {
            keyPressed_Refresh_Stamina = false;
            PacketHandler.instance.sendToServer(new MessageRefresh());
        }

        if (keyPressed_Ability_Three) {
            keyPressed_Ability_Three = false;

            PacketHandler.instance.sendToServer(new MessageForcePower(PowersEnum.HEAL.getId()));
        }

        //PacketHandler.instance.sendToServer(new MessageTick());
    }


//        player.getCapability(ForcePowers.CAPABILITY_FORCE_SENSITIVE).ifPresent(data -> {
//            data.tick();
//
//            if (keyPressed_Refresh_Stamina){
//                data.refreshStamina(true);
//                keyPressed_Refresh_Stamina = false;
//            }
//
//            if (keyPressed_Ability_One){
//                keyPressed_Ability_One = false;
//                if (data.canUsePower(player, PowersEnum.PUSH))
//                    PowersEnum.PUSH.getPower().usePower(player);
//            }
//
//            if (keyPressed_Ability_Two){
//                keyPressed_Ability_Two = false;
//                if (data.canUsePower(player, PowersEnum.EPICENTER))
//                    PowersEnum.EPICENTER.getPower().usePower(player);
//            }
//
//            if (keyPressed_Ability_Three){
//                keyPressed_Ability_Three = false;
//                if (data.canUsePower(player, PowersEnum.HEAL))
//                    PowersEnum.HEAL.getPower().usePower(player);
//            }
//        })
}