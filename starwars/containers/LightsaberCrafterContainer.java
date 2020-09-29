package mod.syconn.starwars.containers;

import mod.syconn.starwars.init.ModContainers;
import mod.syconn.starwars.init.ModItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftResultInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.*;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraft.world.World;

public class LightsaberCrafterContainer extends Container {
    public final IInventory craftingInventory = new Inventory(3);
    public final CraftResultInventory resultInventory = new CraftResultInventory();
    public final Slot outputInventorySlot;
    public final Slot inputInventorySlot;
    private final IWorldPosCallable worldCallable;
    private final PlayerEntity player;

    public LightsaberCrafterContainer(int windowId, PlayerInventory playerInventory) {
        this(windowId, playerInventory, IWorldPosCallable.DUMMY);
    }

    public LightsaberCrafterContainer(final int id, final PlayerInventory playerInventory, final IWorldPosCallable callable) {
        super(ModContainers.LIGHTSABER_CRAFTER, id);
        this.player = playerInventory.player;
        worldCallable = callable;

        outputInventorySlot = this.addSlot(new Slot(this.resultInventory, 0, 83, 44) {

            public boolean isItemValid(ItemStack stack) {
                return false;
            }
        });

        inputInventorySlot = this.addSlot(new Slot(this.craftingInventory, 0, 23, 18){

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
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 102 + i * 18));
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

    @Override
    public void onCraftMatrixChanged(IInventory inventoryIn) {
        this.detectAndSendChanges();
        //slotChangedCraftMatrix(this.windowId, this.player.world, this.player, this.craftingInventory, this.resultInventory);
    }

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