package mod.syconn.starwars.item;

import com.google.common.collect.Multimap;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class KyloSaber extends Item {
    private static final String NBT_COLOR = "LightsaberColor";
    private static final String NBT_STATE = "activated";

    public KyloSaber(Item.Properties group) {
        super(group);
        this.addPropertyOverride(new ResourceLocation(NBT_STATE), (stack, world, player) -> {
            if(player == null)
                return 1.0f;

            CompoundNBT compound = stack.getOrCreateTag();
            if (compound.contains(NBT_STATE, Constants.NBT.TAG_FLOAT)) {
                return compound.getFloat(NBT_STATE);
            }

            else return 1.0f;
        });
    }

    public UseAction getUseAction(ItemStack stack) {
        return UseAction.BLOCK;
    }

    public int getUseDuration(ItemStack stack) {
        if (stack.getOrCreateTag().getFloat(NBT_STATE) == 1.0f){
            return 0;
        }
        return 72000;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        playerIn.setActiveHand(handIn);
        return ActionResult.resultConsume(itemstack);
    }

    public static int getLightsaberColor(ItemStack stack){
        return stack.getOrCreateTag().getInt(NBT_COLOR);
    }

    public static void setLightsaberColor(ItemStack stack, int color){
        stack.getOrCreateTag().putInt(NBT_COLOR, color);
    }

    public static int getItemColor(ItemStack stack, int tintIndex){
        if (tintIndex == 0)
            return getLightsaberColor(stack);

        return 0xFFFFFF;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EquipmentSlotType slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(slot);
        if (slot == EquipmentSlotType.MAINHAND && stack.getOrCreateTag().getFloat(NBT_STATE) == 0.0f) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double) 10, AttributeModifier.Operation.ADDITION));
        }
        if (slot == EquipmentSlotType.MAINHAND && stack.getOrCreateTag().getFloat(NBT_STATE) == 1.0f) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double) 2, AttributeModifier.Operation.ADDITION));
        }

        return multimap;
    }

    @Override
    public void fillItemGroup(ItemGroup group, NonNullList<ItemStack> items) {
        if (isInGroup(group)){
            ItemStack red = new ItemStack(this);
            setLightsaberColor(red, DyeColor.RED.getFireworkColor());
            red.setDisplayName(new TranslationTextComponent("item.kylosaber.red"));
            items.add(red);
        }
    }
}
