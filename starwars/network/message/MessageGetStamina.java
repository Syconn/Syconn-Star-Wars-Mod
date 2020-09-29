package mod.syconn.starwars.network.message;

import mod.syconn.starwars.capability.ForcePowers;
import mod.syconn.starwars.client.gui.ForcePowersOverlay;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageGetStamina implements IMessage<MessageGetStamina> {

    public MessageGetStamina() {
    }

    @Override
    public void encode(MessageGetStamina message, PacketBuffer buffer) {

    }

    @Override
    public MessageGetStamina decode(PacketBuffer buffer) {
        return new MessageGetStamina();
    }

    @Override
    public void handle(MessageGetStamina message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayerEntity player = supplier.get().getSender();
            if (player != null) {

            }
        });
        supplier.get().setPacketHandled(true);
    }
}
