package mod.syconn.starwars.network;

import mod.syconn.starwars.network.message.IMessage;
import mod.syconn.starwars.network.message.MessageCraftLightsaber;
import mod.syconn.starwars.network.message.MessageSetSide;
import mod.syconn.starwars.util.Reference;
import mod.syconn.starwars.util.enums.ForceSideEnum;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class PacketHandler
{
    public static final String PROTOCOL_VERSION = "1";

    public static SimpleChannel instance;
    private static int nextId = 0;

    public static void register()
    {
        instance = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(Reference.MOD_ID, "network"))
                .networkProtocolVersion(() -> PROTOCOL_VERSION)
                .clientAcceptedVersions(PROTOCOL_VERSION::equals)
                .serverAcceptedVersions(PROTOCOL_VERSION::equals)
                .simpleChannel();

        register(MessageCraftLightsaber.class, new MessageCraftLightsaber());
        register(MessageSetSide.class, new MessageSetSide());
    }

    private static <T> void register(Class<T> clazz, IMessage<T> message)
    {
        instance.registerMessage(nextId++, clazz, message::encode, message::decode, message::handle);
    }
}
