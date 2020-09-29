package mod.syconn.starwars.network.message;

import mod.syconn.starwars.block.Bomb;
import mod.syconn.starwars.init.ModBlocks;
import mod.syconn.starwars.init.ModItems;
import mod.syconn.starwars.util.helpers.BlockUtils;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageDefuse implements IMessage<MessageDefuse> {

    private int id;

    public MessageDefuse() { }

    public MessageDefuse(int id) {
        this.id = id;
    }

    @Override
    public void encode(MessageDefuse message, PacketBuffer buffer) {
        buffer.writeVarInt(message.id);
    }

    @Override
    public MessageDefuse decode(PacketBuffer buffer) {
        return new MessageDefuse(buffer.readVarInt());
    }

    @Override
    public void handle(MessageDefuse message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            ServerPlayerEntity player = supplier.get().getSender();
            int[] coords = player.getHeldItemMainhand().getOrCreateTag().getIntArray("bomb" + message.id);
            BlockPos pos = new BlockPos(coords[0], coords[1], coords[2]);

            if (BlockUtils.getBlock(player.world, new BlockPos(coords[0], coords[1], coords[2])) instanceof Bomb) {
                player.world.destroyBlock(pos, true);
                player.world.addEntity(new ItemEntity(player.world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModBlocks.BOMB_BLOCK)));
            }
        });
    }
}
