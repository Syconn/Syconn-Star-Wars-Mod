package mod.syconn.starwars.containers;

import mod.syconn.starwars.init.ModContainers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.IWorldPosCallable;

public class SidePickerContainer extends Container {

    public SidePickerContainer(int windowId, PlayerInventory playerInventory) {
        this(windowId, playerInventory, IWorldPosCallable.DUMMY);
    }

    public SidePickerContainer(final int id, final PlayerInventory playerInventory, final IWorldPosCallable callable) {
        super(ModContainers.SIDE_PICKER, id);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return true;
    }
}
