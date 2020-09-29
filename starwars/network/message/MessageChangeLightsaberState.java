package mod.syconn.starwars.network.message;

import mod.syconn.starwars.item.LightsaberItem;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageChangeLightsaberState implements IMessage<MessageChangeLightsaberState> {

    @Override
    public void encode(MessageChangeLightsaberState message, PacketBuffer buffer) {

    }

    @Override
    public MessageChangeLightsaberState decode(PacketBuffer buffer) {
        return null;
    }

    @Override
    public void handle(MessageChangeLightsaberState message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            ServerPlayerEntity player = supplier.get().getSender();

            ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);

            if (stack.getTag() == null) {
                stack.setTag(new CompoundNBT());
            }
            if (stack.getItem() instanceof LightsaberItem) {
                CompoundNBT compound = stack.getTag();

                if (compound != null) {
                    if (compound.getFloat("activated") == 0.0F) {
                        compound.putFloat("activated", 1.0F);
                    } else if (compound.getFloat("activated") == 1.0F) {
                        compound.putFloat("activated", 0.0F);
                    }

                    System.out.println(compound.get("activated"));
                }
            }
        });
    }
}
