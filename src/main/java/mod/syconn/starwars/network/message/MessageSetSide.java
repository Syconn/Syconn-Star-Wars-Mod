package mod.syconn.starwars.network.message;

import mod.syconn.starwars.capability.interfaces.IForceSensitive;
import mod.syconn.starwars.containers.LightsaberCrafterContainer;
import mod.syconn.starwars.init.ModCapabilities;
import mod.syconn.starwars.init.ModItems;
import mod.syconn.starwars.util.enums.ForceSideEnum;
import mod.syconn.starwars.util.handlers.ColorHandlers;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageSetSide implements IMessage<MessageSetSide> {

    private ForceSideEnum side;

    public MessageSetSide() {}

    public MessageSetSide(ForceSideEnum side) {
        this.side = side;
    }

    @Override
    public void encode(MessageSetSide message, PacketBuffer buffer) {
        buffer.writeEnumValue(side);
    }

    @Override
    public MessageSetSide decode(PacketBuffer buffer) {
        return new MessageSetSide(buffer.readEnumValue(ForceSideEnum.class));
    }

    @Override
    public void handle(MessageSetSide message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayerEntity player = supplier.get().getSender();
            if(player != null)
            {
                IForceSensitive data = player.getCapability(ModCapabilities.FORCE_CAPABILITY).orElseThrow(IllegalStateException::new);
                data.setSide(side);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
