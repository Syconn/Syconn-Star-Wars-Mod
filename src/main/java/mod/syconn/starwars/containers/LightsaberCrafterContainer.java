package mod.syconn.starwars.containers;

import java.util.Map;
import java.util.Optional;

import mod.syconn.starwars.init.ModContainers;
import mod.syconn.starwars.init.ModItems;
import mod.syconn.starwars.init.ModRecipeSerializers;
import mod.syconn.starwars.init.ModRecipes;
import mod.syconn.starwars.item.LightsaberItem;
import mod.syconn.starwars.util.handlers.CraftingHandler;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.client.gui.screen.inventory.CraftingScreen;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.*;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeItemHelper;
import net.minecraft.network.play.server.SSetSlotPacket;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;

public class LightsaberCrafterContainer extends Container {
    private final CraftingInventory craftingInventory = new CraftingInventory(this, 3, 3);
    private CraftResultInventory resultInventory = new CraftResultInventory();
    private final IWorldPosCallable worldCallable;
    private final PlayerEntity player;

    public LightsaberCrafterContainer(int windowId, PlayerInventory playerInventory) {
        this(windowId, playerInventory, IWorldPosCallable.DUMMY);
    }

    public LightsaberCrafterContainer(final int id, final PlayerInventory playerInventory, final IWorldPosCallable callable) {
        super(ModContainers.LIGHTSABER_CRAFTER, id);
        this.player = playerInventory.player;
        worldCallable = callable;

        this.addSlot(new CraftingResultSlot(this.player, this.craftingInventory, this.resultInventory, 2, 83, 44));

        this.addSlot(new Slot(this.craftingInventory, 0, 23, 18){

            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() != ModItems.KYBER_CRYSTAL;
            }
        });
        this.addSlot(new Slot(this.craftingInventory, 1, 23, 44){

            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() == ModItems.KYBER_CRYSTAL;
            }
    });
        this.addSlot(new Slot(this.craftingInventory, 2, 23, 70) {

            public boolean isItemValid(ItemStack stack) {
                return stack.getItem() != ModItems.KYBER_CRYSTAL;
            }
        });

        for(int i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 106 + i * 18));
            }
        }

        for(int k = 0; k < 9; ++k) {
            this.addSlot(new Slot(playerInventory, k, 8 + k * 18, 160));
        }
    }

    @Override
    public boolean getCanCraft(PlayerEntity player) {
        return true;
    }

    @Override
    public void setCanCraft(PlayerEntity player, boolean canCraft) {
        super.setCanCraft(player, true);
    }

    protected void slotChangedCraftMatrix(int windowId, World world, PlayerEntity player, CraftingInventory inventory, CraftResultInventory resultInventory) {
        if(!world.isRemote) {
            ServerPlayerEntity serverplayerentity = (ServerPlayerEntity)player;
            ItemStack itemstack;
//            if(optional.isPresent()) {
//                ICraftingRecipe icraftingrecipe = optional.get();
//                System.out.println(icraftingrecipe);
//                if(resultInventory.canUseRecipe(world, serverplayerentity, icraftingrecipe) && icraftingrecipe.getSerializer() == ModRecipeSerializers.LIGHTSABER_CRAFTING_RECIPE) {
//                    itemstack = icraftingrecipe.getCraftingResult(inventory);
//                    System.out.println("Result");
//                }
//            }

            itemstack = CraftingHandler.Craft(inventory);

            //if (craftingInventory.getStackInSlot(0).getItem() == ModItems.STEEL_INGOT && craftingInventory.getStackInSlot(1).getItem() == ModItems.KYBER_CRYSTAL && craftingInventory.getStackInSlot(2).getItem() == Items.STICK){
            //    ItemStack lightsaber = ModItems.LIGHTSABER.getDefaultInstance();
            //    LightsaberItem.setLightsaberColor(lightsaber.getStack(), LightsaberItem.getLightsaberColor(craftingInventory.getStackInSlot(1)));
            //    itemstack = lightsaber.getStack();
            //}
            if (itemstack != ItemStack.EMPTY){
                CraftComplete(inventory);
            }

            System.out.println(itemstack);
            resultInventory.setInventorySlotContents(0, itemstack);
            serverplayerentity.connection.sendPacket(new SSetSlotPacket(windowId, 0, itemstack));
        }
    }

    private void CraftComplete(@Nonnull CraftingInventory inventory){
        inventory.getStackInSlot(0).shrink(1);
        inventory.getStackInSlot(1).shrink(1);
        inventory.getStackInSlot(2).shrink(1);
    }

    public void onCraftMatrixChanged(IInventory inventoryIn) {
        this.worldCallable.consume((p_217069_1_, p_217069_2_) -> {
            slotChangedCraftMatrix(this.windowId, p_217069_1_, this.player, this.craftingInventory, this.resultInventory);
        });
    }

    public void fillStackedContents(RecipeItemHelper itemHelperIn) {
        this.craftingInventory.fillStackedContents(itemHelperIn);
    }

    public void clear() {
        this.craftingInventory.clear();
        this.resultInventory.clear();
    }


    /*@Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        this.detectAndSendChanges();
        slotChangedCraftMatrix(this.windowId, this.player.world, this.player, this.craftingInventory, this.resultInventory);
    }*/

    @Override
    protected void clearContainer(PlayerEntity playerIn, World worldIn, IInventory inventoryIn) {
        this.craftingInventory.clear();
        this.resultInventory.clear();
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        if(playerIn.isSneaking())
            return false;
        else
            return true;
    }

    @Override
    public void onContainerClosed(PlayerEntity playerIn) {
        super.onContainerClosed(playerIn);
        for(int i = 0; i < this.craftingInventory.getSizeInventory(); i ++) {
            ItemStack stack = this.craftingInventory.getStackInSlot(i).copy();
            playerIn.addItemStackToInventory(stack);
        }
        this.clearContainer(playerIn, playerIn.world, this.craftingInventory);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            ItemStack itemstack2 = this.craftingInventory.getStackInSlot(0);
            ItemStack itemstack3 = this.craftingInventory.getStackInSlot(1);

            if (index == 2) {
                if (!this.mergeItemStack(itemstack1, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (index != 0 && index != 1) {
                if (!itemstack2.isEmpty() && !itemstack3.isEmpty()) {
                    if (index >= 3 && index < 30) {
                        if (!this.mergeItemStack(itemstack1, 30, 39, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (index >= 30 && index < 39 && !this.mergeItemStack(itemstack1, 3, 30, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (!this.mergeItemStack(itemstack1, 0, 2, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 3, 39, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(playerIn, itemstack1);
        }

        return itemstack;
    }

    @Override
    public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
        return slotIn.inventory != this.resultInventory && super.canMergeSlot(stack, slotIn);
    }
}