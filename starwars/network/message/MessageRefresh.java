package mod.syconn.starwars.network.message;

import mod.syconn.starwars.capability.ForcePowers;
import mod.syconn.starwars.util.powers.ForcePower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageRefresh implements IMessage<MessageRefresh> {

    public MessageRefresh() { }



    @Override
    public void encode(MessageRefresh message, PacketBuffer buffer) { }

    @Override
    public MessageRefresh decode(PacketBuffer buffer) {
        return new MessageRefresh();
    }

    @Override
    public void handle(MessageRefresh message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            ServerPlayerEntity player = supplier.get().getSender();
            if (player != null) {
                player.sendMessage(new TranslationTextComponent("Stamina: " + ForcePowers.getHandler(player).getStamina()));
                ForcePowers.getHandler(player).refreshStamina(true);
            }
        });
    }
}
