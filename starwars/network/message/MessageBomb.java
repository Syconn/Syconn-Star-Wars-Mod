package mod.syconn.starwars.network.message;

import mod.syconn.starwars.block.Bomb;
import mod.syconn.starwars.util.helpers.BlockUtils;
import mod.syconn.starwars.util.helpers.TriggerHelper;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageBomb implements IMessage<MessageBomb> {

    private int id;

    public MessageBomb() {}

    public MessageBomb(int id) {
        this.id = id;
    }

    @Override
    public void encode(MessageBomb message, PacketBuffer buffer) {
        buffer.writeVarInt(message.id);
    }

    @Override
    public MessageBomb decode(PacketBuffer buffer) {
        return new MessageBomb(buffer.readVarInt());
    }

    @Override
    public void handle(MessageBomb message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            ServerPlayerEntity player = supplier.get().getSender();
            int[] coords = player.getHeldItemMainhand().getOrCreateTag().getIntArray("bomb" + message.id);
            BlockPos pos = new BlockPos(coords[0], coords[1], coords[2]);

            if (BlockUtils.getBlock(player.world, new BlockPos(coords[0], coords[1], coords[2])) instanceof Bomb) {
                TriggerHelper.createExplosion(player.world, pos);
            }
        });
    }
}
