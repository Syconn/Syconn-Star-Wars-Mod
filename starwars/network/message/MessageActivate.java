package mod.syconn.starwars.network.message;

import mod.syconn.starwars.block.Bomb;
import mod.syconn.starwars.util.helpers.BlockUtils;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageActivate implements IMessage<MessageActivate> {

    private int id;

    public MessageActivate() {
    }

    public MessageActivate(int id) {
        this.id = id;
    }

    @Override
    public void encode(MessageActivate message, PacketBuffer buffer) {
        buffer.writeVarInt(message.id);
    }

    @Override
    public MessageActivate decode(PacketBuffer buffer) {
        return new MessageActivate(buffer.readVarInt());
    }

    @Override
    public void handle(MessageActivate message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            ServerPlayerEntity player = supplier.get().getSender();
            int[] coords = player.getHeldItemMainhand().getOrCreateTag().getIntArray("bomb" + message.id);
            BlockPos pos = new BlockPos(coords[0], coords[1], coords[2]);

            if (BlockUtils.getBlock(player.world, new BlockPos(coords[0], coords[1], coords[2])) instanceof Bomb) {
                player.world.setBlockState(pos, player.world.getBlockState(pos).with(Bomb.FLASH, true), 3);
            }
        });
    }
}
