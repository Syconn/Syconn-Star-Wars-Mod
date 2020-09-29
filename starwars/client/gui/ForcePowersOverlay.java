package mod.syconn.starwars.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.syconn.starwars.capability.ForcePowers;
import mod.syconn.starwars.network.PacketHandler;
import mod.syconn.starwars.network.message.MessageGetStamina;
import mod.syconn.starwars.network.message.MessageTick;
import mod.syconn.starwars.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@OnlyIn(value = Dist.CLIENT)
public class ForcePowersOverlay {

    private Minecraft minecraft;
    private static final ResourceLocation tex = new ResourceLocation(Reference.MOD_ID, "textures/gui/mod_widgets.png");

    public ForcePowersOverlay() {
        minecraft = Minecraft.getInstance();
    }

    @SuppressWarnings("resource")
    @SubscribeEvent
    public void renderGameOverlay(TickEvent.RenderTickEvent event)
    {
        if(event.type != TickEvent.Type.RENDER || event.phase != TickEvent.Phase.END)
        {
            return;
        }

        Screen screen = Minecraft.getInstance().currentScreen;

        if(Minecraft.getInstance().player != null && screen == null)
        {
            renderCustomOverlay(ForcePowers.getHandler(minecraft.player).getStamina());
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void renderCustomOverlay(int data) {

        Minecraft minecraft = Minecraft.getInstance();

        int scaledHeight = minecraft.getMainWindow().getScaledHeight();

        float oneUnitofBar = (float) 52 / data;
        int stamina = (int) (oneUnitofBar * data);

        RenderSystem.pushMatrix();

        RenderSystem.enableAlphaTest();
        RenderSystem.color4f(1F, 1F, 1F, 1F);
        minecraft.getTextureManager().bindTexture(tex);
        //Slots
        minecraft.ingameGUI.blit(40, scaledHeight - 22, 0, 0, 62, 21);
        //Push
        minecraft.ingameGUI.blit(44, scaledHeight - 18, 4, 25, 15, 15);
        //Epicenter
        minecraft.ingameGUI.blit(64, scaledHeight - 18, 23, 25, 15, 15);
        //Cool Down
        //minecraft.ingameGUI.blit(61, scaledHeight - 22, 86, 0, (int) (20 / data.getCooldown()), 21);
        //Heal
        minecraft.ingameGUI.blit(84, scaledHeight - 18, 42, 25, 15, 15);

        //Stamina Background
        //minecraft.ingameGUI.blit(43, scaledHeight - 26, 0, 47, 53, 3);

        //Stamina
        //minecraft.ingameGUI.blit(43, scaledHeight - 26, 0, 43, stamina, 3);

        RenderSystem.disableAlphaTest();
        RenderSystem.popMatrix();

    }


//    private void renderCustomOverlay(){
//        PlayerEntity player = (PlayerEntity)this.minecraft.getRenderViewEntity();
//        if (player != null) {
//            IForceSensitive data = player.getCapability(CapabilityForceSensetive.FORCE_CAPABILITY).orElseThrow(IllegalStateException::new);
//
//            int scaledWidth = this.minecraft.getMainWindow().getScaledWidth();
//            int scaledHeight = this.minecraft.getMainWindow().getScaledHeight();
//
//            float oneUnitofBar = (float) 52 / data.getMaxStamina();
//            int stamina = (int) (oneUnitofBar * data.getStamina());
//
//            RenderSystem.pushMatrix();
//            RenderSystem.pushTextureAttributes();
//
//            RenderSystem.enableAlphaTest();
//            RenderSystem.enableBlend();
//            RenderSystem.color4f(1F, 1F, 1F, 1F);
//            minecraft.getTextureManager().bindTexture(tex);
//            //Slots
//            minecraft.ingameGUI.blit(40, scaledHeight - 22, 0, 0, 62, 21);
//            //Push
//            minecraft.ingameGUI.blit(44, scaledHeight - 18, 4, 25, 15, 15);
//            //Epicenter
//            minecraft.ingameGUI.blit(64, scaledHeight - 18, 23, 25, 15, 15);
//            //Cool Down
//            minecraft.ingameGUI.blit(61, scaledHeight - 22, 86, 0, (int) (20 / data.getCooldown()), 21);
//            //Heal
//            minecraft.ingameGUI.blit(84, scaledHeight - 18, 42, 25, 15, 15);
//
//            //Stamina Background
//            minecraft.ingameGUI.blit(43, scaledHeight - 26, 0, 47, 53, 3);
//
//            //Stamina
//            minecraft.ingameGUI.blit(43, scaledHeight - 26, 0, 43, stamina, 3);
//
//            minecraft.getRenderManager().getFontRenderer().drawString("Stamina: " + data.getStamina(), 8.0f, 6.0f, 410752);
//            minecraft.getRenderManager().getFontRenderer().drawString("" + data.getCooldown(), 8.0f, 26.0f, 410752);
//
//            minecraft.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
//
//            RenderSystem.disableAlphaTest();
//            RenderSystem.popAttributes();
//            RenderSystem.popMatrix();
//        }
//    }
}
