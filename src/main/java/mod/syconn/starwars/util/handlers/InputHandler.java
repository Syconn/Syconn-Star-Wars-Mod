package mod.syconn.starwars.util.handlers;

import mod.syconn.starwars.item.LightsaberItem;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Hand;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class InputHandler {

    private final KeyBinding KEY_ACTIVATE = new KeyBinding("key.swm.activate", 263, "key.swm.starwars");
    private boolean keyPressed_Activated = false;

    public InputHandler(){
        ClientRegistry.registerKeyBinding(KEY_ACTIVATE);
    }


    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        if(KEY_ACTIVATE.isPressed())
        {
            keyPressed_Activated = true;
        }
    }

    @SubscribeEvent
    public void playerTick(TickEvent.PlayerTickEvent event) {

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
    }
}