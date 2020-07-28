package mod.syconn.starwars.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.syconn.starwars.containers.SidePickerContainer;
import mod.syconn.starwars.network.PacketHandler;
import mod.syconn.starwars.network.message.MessageSetSide;
import mod.syconn.starwars.util.Reference;
import mod.syconn.starwars.util.enums.ForceSideEnum;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.awt.*;

public class SidePickerScreen extends ContainerScreen<SidePickerContainer> {

    private static final ResourceLocation BACKROUND_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/Side_Picker.png");

    public SidePickerScreen(SidePickerContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void init() {
        super.init();
        this.addButton(new Button(8, 100,  18, 57, "SITH", button ->
        {
            PacketHandler.instance.sendToServer(new MessageSetSide(ForceSideEnum.SITH));
            onClose();
        }));

        this.addButton(new Button(109, 100,  18, 57, "JEDI", button ->
        {
            PacketHandler.instance.sendToServer(new MessageSetSide(ForceSideEnum.JEDI));
            onClose();
        }));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(BACKROUND_TEXTURE);
        int x = (this.width - 176) / 2;
        int y = (this.height - 184) / 2;
        this.blit(x, y, 0, 0, 176, 184);
    }

    @Override
    public void renderBackground() {
        super.renderBackground();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
    }
}
