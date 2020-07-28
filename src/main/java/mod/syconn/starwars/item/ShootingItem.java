package mod.syconn.starwars.item;

import mod.syconn.starwars.entity.BlasterBoltEntity;
import net.minecraft.client.renderer.entity.FireworkRocketRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.awt.*;

public class ShootingItem extends Item {

    protected final int MAX_HEAT;
    protected final String HEAT = "current_heat";
    protected final int COOLDOWN_SPEED = 40;

    public ShootingItem(Properties properties, int maxHeat) {
        super(properties);
        MAX_HEAT = maxHeat;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundNBT nbt) {
        stack.getOrCreateTag().putInt(HEAT, 0);
        return null;
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        CompoundNBT nbt = stack.getOrCreateTag();

        if (nbt.getInt(HEAT) < MAX_HEAT) {
            Shoot(worldIn, playerIn);
            nbt.putInt(HEAT, nbt.getInt(HEAT) + 1);
            playerIn.swing(handIn, true);
            playerIn.getCooldownTracker().setCooldown(this, 10);
        }

        else {
            playerIn.getCooldownTracker().setCooldown(this, COOLDOWN_SPEED);
            nbt.putInt(HEAT, 0);
        }

        return new ActionResult<>(ActionResultType.SUCCESS, stack);
    }

    @Override
    public boolean onEntitySwing(ItemStack stack, LivingEntity entity)
    {
        return true;
    }

    public void Shoot(World world, PlayerEntity player){
        BlasterBoltEntity bolt = new BlasterBoltEntity(world, player);
        bolt.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 3.0F, 1.0F);
        world.addEntity(bolt);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack)
    {
        return stack.getOrCreateTag().getInt(HEAT) != 0;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack)
    {
        CompoundNBT tagCompound = stack.getOrCreateTag();
        return 1.0 - (tagCompound.getInt(HEAT) / (double) MAX_HEAT);
    }

    @Override
    public int getRGBDurabilityForDisplay(ItemStack stack)
    {
        return Color.CYAN.getRGB();
    }
}
