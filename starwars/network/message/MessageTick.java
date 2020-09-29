package mod.syconn.starwars.network.message;

import mod.syconn.starwars.capability.ForcePowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageTick implements IMessage<MessageTick> {

    public MessageTick() {
    }

    @Override
    public void encode(MessageTick message, PacketBuffer buffer) { }

    @Override
    public MessageTick decode(PacketBuffer buffer) {
        return new MessageTick();
    }

    @Override
    public void handle(MessageTick message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            ServerPlayerEntity player = supplier.get().getSender();
            if (player != null) {
                ForcePowers.getHandler(player).tick();
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
