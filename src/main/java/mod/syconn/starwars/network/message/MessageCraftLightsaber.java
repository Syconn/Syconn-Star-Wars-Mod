package mod.syconn.starwars.network.message;

import mod.syconn.starwars.containers.LightsaberCrafterContainer;
import mod.syconn.starwars.init.ModItems;
import mod.syconn.starwars.util.handlers.ColorHandlers;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageCraftLightsaber implements IMessage<MessageCraftLightsaber> {

    public MessageCraftLightsaber() {}

    @Override
    public void encode(MessageCraftLightsaber message, PacketBuffer buffer)
    {
        //buffer.writeString(message.vehicleId, 128);
        //buffer.writeBlockPos(message.pos);
    }

    @Override
    public MessageCraftLightsaber decode(PacketBuffer buffer)
    {
        return new MessageCraftLightsaber();
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void handle(MessageCraftLightsaber message, Supplier<NetworkEvent.Context> supplier)
    {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayerEntity player = supplier.get().getSender();
            if(player != null)
            {
                World world = player.world;
                if(!(player.openContainer instanceof LightsaberCrafterContainer))
                    return;

                LightsaberCrafterContainer crafter = (LightsaberCrafterContainer) player.openContainer;

                for(int i = 0; i < crafter.craftingInventory.getSizeInventory(); i++)
                {
                    crafter.craftingInventory.getStackInSlot(i).shrink(1);
                }

                //Craft
                ItemStack itemstack = ModItems.LIGHTSABER.getDefaultInstance();
                ColorHandlers.setItemColor(itemstack, crafter.craftingInventory.getStackInSlot(1));

                crafter.outputInventorySlot.putStack(itemstack);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
