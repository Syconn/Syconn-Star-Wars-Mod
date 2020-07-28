package mod.syconn.starwars.util.handlers;

import mod.syconn.starwars.capability.interfaces.IForceSensitive;
import mod.syconn.starwars.init.ModCapabilities;
import mod.syconn.starwars.item.LightsaberItem;
import net.java.games.input.Keyboard;
import net.minecraft.client.GameSettings;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.loading.FMLEnvironment;

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
        ClientRegistry.registerKeyBinding(KEY_ACTIVATE);
        ClientRegistry.registerKeyBinding(FORCE_ABILITY_ONE);
        ClientRegistry.registerKeyBinding(FORCE_ABILITY_TWO);
        ClientRegistry.registerKeyBinding(FORCE_ABILITY_THREE);
        ClientRegistry.registerKeyBinding(REFRESH_STAMINA);
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if(KEY_ACTIVATE.isPressed())
        {
            keyPressed_Activated = true;
        }

        if(FORCE_ABILITY_ONE.isPressed())
        {
            keyPressed_Ability_One = true;
        }

        if(FORCE_ABILITY_TWO.isPressed())
        {
            keyPressed_Ability_Two= true;
        }

        if(FORCE_ABILITY_THREE.isPressed())
        {
            keyPressed_Ability_Three = true;
        }

        if (REFRESH_STAMINA.isPressed()){
            keyPressed_Refresh_Stamina = true;
        }
    }

    @SubscribeEvent
    //@OnlyIn(Dist.CLIENT)
    public void playerTick(TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        IForceSensitive data = player.getCapability(ModCapabilities.FORCE_CAPABILITY).orElseThrow(IllegalStateException::new);
        data.tick(data);

        if (keyPressed_Activated) {
            keyPressed_Activated = false;

            ItemStack stack = event.player.getHeldItem(Hand.MAIN_HAND);

            if (stack.getTag() == null) {
                stack.setTag(new CompoundNBT());
            }
            if (stack.getItem() instanceof LightsaberItem) {
                CompoundNBT compound = stack.getTag();

                if (compound != null) {
                    if (compound.getFloat("activated") == 0.0F) {
                        compound.putFloat("activated", 1.0F);
                    }

                    else if (compound.getFloat("activated") == 1.0F) {
                        compound.putFloat("activated", 0.0F);
                    }

                    System.out.println(compound.get("activated"));
                }
            }
        }

        if (keyPressed_Ability_One)
        {
            System.out.println("I Like cow");
            if (data.canUsePower(data, player, 0)){
                data.getSlot0().getPower().getPower().usePower(player);
            }
            keyPressed_Ability_One = false;
        }

        if (keyPressed_Ability_Two)
        {
            System.out.println("I Like cow");
            if (data.canUsePower(data, player, 1)){
                data.getSlot1().getPower().getPower().usePower(player);
                System.out.println("No Cow Like");
            }
            keyPressed_Ability_Two = false;
        }

        if (keyPressed_Ability_Three)
        {
            System.out.println(data.getSlot2().getPower().getId());
//            if (data.canUsePower(data, player, 2)){
//                data.getSlot2().getPower().getPower().usePower(player);
//                System.out.println("No Cow Like");
//            }
            keyPressed_Ability_Three = false;
        }

        if (keyPressed_Refresh_Stamina)
        {
            data.setIsRecharging(true);

            keyPressed_Refresh_Stamina = false;
        }
    }
}