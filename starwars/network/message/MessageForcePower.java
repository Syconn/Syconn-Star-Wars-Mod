package mod.syconn.starwars.network.message;

import mod.syconn.starwars.capability.ForcePowers;
import mod.syconn.starwars.util.enums.PowersEnum;
import mod.syconn.starwars.util.handlers.ColorHandlers;
import mod.syconn.starwars.util.powers.ForcePower;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageForcePower implements IMessage<MessageForcePower> {

    private int power;

    public MessageForcePower() { }

    public MessageForcePower(int power) {
        this.power = power;
    }

    @Override
    public void encode(MessageForcePower message, PacketBuffer buffer) {
        buffer.writeVarInt(message.power);
    }

    @Override
    public MessageForcePower decode(PacketBuffer buffer) {
        return new MessageForcePower(buffer.readVarInt());
    }

    @Override
    public void handle(MessageForcePower message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() -> {
            PowersEnum force = PowersEnum.getForcePowerById(message.power);

            ServerPlayerEntity player = supplier.get().getSender();
            ForcePowers.IForceSensitive data = ForcePowers.getHandler(player);
            player.sendMessage(new TranslationTextComponent("Stamina: " + data.getStamina()));
            if (data.getStamina() >= force.getStaminaRequired()) {
                if (force == PowersEnum.HEAL){
                    if (player.getHealth() == player.getMaxHealth())
                        return;
                    data.setStamina(data.getStamina() - force.getStaminaRequired());
                    force.getPower().usePower(player);
                }

                data.setStamina(data.getStamina() - force.getStaminaRequired());
                force.getPower().usePower(player);
            }
        });
        supplier.get().setPacketHandled(true);
    }
}
