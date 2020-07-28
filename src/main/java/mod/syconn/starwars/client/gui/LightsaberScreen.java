package mod.syconn.starwars.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import mod.syconn.starwars.containers.LightsaberCrafterContainer;
import mod.syconn.starwars.network.PacketHandler;
import mod.syconn.starwars.network.message.MessageCraftLightsaber;
import mod.syconn.starwars.util.Reference;
import mod.syconn.starwars.util.handlers.CraftingHandler;
import net.minecraft.client.gui.screen.EnchantmentScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class LightsaberScreen extends ContainerScreen<LightsaberCrafterContainer> {

    private static final ResourceLocation BACKROUND_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/gui/crafter.png");
    private Button btnCraft;
    private LightsaberCrafterContainer container;

    public LightsaberScreen(LightsaberCrafterContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        container = screenContainer;
        this.guiLeft = 0;
        this.guiTop = 0;
        this.ySize = 183;
        this.xSize = 175;
    }

    @Override
    protected void init() {
        super.init();
        this.btnCraft = this.addButton(new Button( this.guiLeft + 45, this.guiTop + 42, 30, 20, "Craft", button ->
        {
            PacketHandler.instance.sendToServer(new MessageCraftLightsaber());
            this.btnCraft.active = false;
        }));

        this.btnCraft.active = false;
    }

    @Override
    public void tick() {
        super.tick();
        if (CraftingHandler.CanCraft(container.craftingInventory)){
            this.btnCraft.active = true;
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.renderBackground();
        super.render(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        this.font.drawString(this.title.getFormattedText(), 8.0f, 6.0f, 410752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bindTexture(BACKROUND_TEXTURE);
        int x = (this.width - this.xSize) / 2;
        int y = (this.height - this.ySize) / 2;
        this.blit(x, y, 0, 0, this.xSize, this.ySize);
    }
}
