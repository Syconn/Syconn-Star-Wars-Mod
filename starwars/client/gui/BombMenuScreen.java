package mod.syconn.starwars.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import jdk.nashorn.internal.ir.Block;
import mod.syconn.starwars.block.Bomb;
import mod.syconn.starwars.client.gui.componets.ClickButton;
import mod.syconn.starwars.network.PacketHandler;
import mod.syconn.starwars.network.message.*;
import mod.syconn.starwars.util.helpers.BlockUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.InputMappings;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BombMenuScreen extends Screen {

    private final ResourceLocation TEXTURE = new ResourceLocation("swm:textures/gui/bomb_screen.png");
    private final ClickButton[][] buttons = new ClickButton[6][5];
    private final int EXPLODE = 0, TIMED = 1, ACTIVATE = 2, DEACTIVATE = 3, DEFUSE = 4;
    private ItemStack stack;
    private World world;
    private int xSize = 256, ySize = 184;

    public BombMenuScreen(ITextComponent titleIn, World world, ItemStack stack) {
        super(titleIn);
        this.stack = stack;
        this.world = world;
    }

    @Override
    protected void init() {
        super.init();
        for (int i = 0; i < 6; i++) {
            int[] coords = stack.getOrCreateTag().getIntArray("bomb" + i);
            BlockPos pos = new BlockPos(coords[0], coords[1], coords[2]);
            int scaledHeight = minecraft.getMainWindow().getScaledHeight();
            int scaledWidth = minecraft.getMainWindow().getScaledWidth();

            if (BlockUtils.getBlock(world, new BlockPos(coords[0], coords[1], coords[2])) instanceof Bomb) {
                for (int j = 0; j < 5; j++) {

                    switch (j) {
                        case EXPLODE:
                            buttons[i][j] = new ClickButton(i, (scaledWidth / 2) + 72, scaledHeight / 2 - (84 - (i * 30)), 50, 20, "Explode", this::explode);
                            buttons[i][j].active = world.getBlockState(pos).get(Bomb.FLASH);
                            break;

                        case TIMED:
                            buttons[i][j] = new ClickButton(i, (scaledWidth / 2) + 84, scaledHeight / 2 - (84 - (i * 30)), 50, 20, "Timed Explosion ", this::timedExplosion);
                            buttons[i][j].active = false;
                            buttons[i][j].visible = false;
                            break;

                        case ACTIVATE:
                            buttons[i][j] = new ClickButton(i, (scaledWidth / 2) - 38, scaledHeight / 2 - (84 - (i * 30)), 50, 20, "Activate", this::activate);
                            buttons[i][j].active = !world.getBlockState(pos).get(Bomb.FLASH);;
                            break;

                        case DEACTIVATE:
                            buttons[i][j] = new ClickButton(i, (scaledWidth / 2) + 17, scaledHeight / 2 - (84 - (i * 30)), 50, 20, "Deactivate", this::deactivate);
                            buttons[i][j].active = world.getBlockState(pos).get(Bomb.FLASH);;
                            break;

                        case DEFUSE:
                            buttons[i][j] = new ClickButton(i, (scaledWidth / 2) - 93, scaledHeight / 2 - (84 - (i * 30)), 50, 20, "Defuse", this::defuse);
                            buttons[i][j].active = !world.getBlockState(pos).get(Bomb.FLASH);;
                            break;
                    }

                    addButton(buttons[i][j]);
                }
            }
        }
    }

    private void timedExplosion(ClickButton button) {
        PacketHandler.instance.sendToServer(new MessageExplodeTimed(button.id));
    }

    private void explode(ClickButton button) {
        PacketHandler.instance.sendToServer(new MessageBomb(button.id));
    }

    private void activate(ClickButton button) {
        PacketHandler.instance.sendToServer(new MessageActivate(button.id));
        changeButtonState(button.id, true, EXPLODE, DEACTIVATE);
        changeButtonState(button.id, false, DEFUSE, ACTIVATE);
    }

    private void deactivate(ClickButton button) {
        PacketHandler.instance.sendToServer(new MessageDeactive(button.id));
        changeButtonState(button.id, false, EXPLODE, DEACTIVATE);
        changeButtonState(button.id, true, DEFUSE, ACTIVATE);
    }

    private void defuse(ClickButton button) {
        PacketHandler.instance.sendToServer(new MessageDefuse(button.id));
    }

    private void changeButtonState(int i, boolean value, int... button){
        for (int b : button){
            buttons[i][b].active = value;
        }
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        renderBackground();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(TEXTURE);
        int startX = (width - xSize) / 2;
        int startY = (height - ySize) / 2;
        this.blit(startX, startY, 0, 0, xSize, ySize);

        for (int i = 0; i < 6; i++) {
            minecraft.getTextureManager().bindTexture(TEXTURE);
            int[] coords = stack.getOrCreateTag().getIntArray("bomb" + i);
            int scaledHeight = minecraft.getMainWindow().getScaledHeight();
            int scaledWidth = minecraft.getMainWindow().getScaledWidth();

            if (BlockUtils.getBlock(world, new BlockPos(coords[0], coords[1], coords[2])) instanceof Bomb){
                this.blit((scaledWidth / 2) - 123, scaledHeight / 2 - (87 - (i * 30)), 0, 185, 246, 25);
            }
        }
        super.render(mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

    @Override
    public boolean keyPressed(int p_keyPressed_1_, int p_keyPressed_2_, int p_keyPressed_3_) {
        if (minecraft.gameSettings.keyBindInventory.isActiveAndMatches(InputMappings.getInputByCode(p_keyPressed_1_, p_keyPressed_2_))) {
            this.onClose();
            return true;
        }
        return super.keyPressed(p_keyPressed_1_, p_keyPressed_2_, p_keyPressed_3_);
    }
}
