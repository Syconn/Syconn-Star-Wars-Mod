package mod.syconn.starwars.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.syconn.starwars.capability.ForcePowers;
import mod.syconn.starwars.capability.interfaces.IForceSensitive;
import mod.syconn.starwars.init.ModCapabilities;
import mod.syconn.starwars.util.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.GameType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.CapabilityItemHandler;

@OnlyIn(value = Dist.CLIENT)
public class ForcePowersOverlay {

    private Minecraft minecraft;
    private static final ResourceLocation tex = new ResourceLocation(Reference.MOD_ID, "textures/gui/mod_widgets.png");

    public ForcePowersOverlay() {
        minecraft = Minecraft.getInstance();
    }

    @SubscribeEvent
    public void renderOverlay(RenderGameOverlayEvent.Post event) {
        if(event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            renderCustomOverlay();
        }
    }

    private void renderCustomOverlay(){
        PlayerEntity player = (PlayerEntity)this.minecraft.getRenderViewEntity();
        if (player != null) {
            IForceSensitive data = player.getCapability(ModCapabilities.FORCE_CAPABILITY).orElseThrow(IllegalStateException::new);

            int scaledWidth = this.minecraft.getMainWindow().getScaledWidth();
            int scaledHeight = this.minecraft.getMainWindow().getScaledHeight();

            RenderSystem.pushMatrix();
            RenderSystem.pushTextureAttributes();

            RenderSystem.enableAlphaTest();
            RenderSystem.enableBlend();
            RenderSystem.color4f(1F, 1F, 1F, 1F);
            minecraft.getTextureManager().bindTexture(tex);
            //Slots
            minecraft.ingameGUI.blit(40, scaledHeight - 22, 0, 0, 62, 21);
            //Push
            minecraft.ingameGUI.blit(44, scaledHeight - 18, 4, 25, 15, 15);
            //Epicenter
            minecraft.ingameGUI.blit(64, scaledHeight - 18, 23, 25, 15, 15);
            //Heal
            minecraft.ingameGUI.blit(84, scaledHeight - 18, 42, 25, 15, 15);

            //minecraft.getRenderManager().getFontRenderer().drawString(data.getSide().getName(), 8.0f, 6.0f, 410752);

            minecraft.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);

            RenderSystem.popAttributes();
            RenderSystem.popMatrix();
        }
    }
}
