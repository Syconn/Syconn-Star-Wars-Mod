package mod.syconn.starwars;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config
{
    public static class Common
    {
        public final ForgeConfigSpec.IntValue crystalCaveGenerationChance;

        Common(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Common configuration settings").push("common");
            {
                builder.comment("Structures").push("structures");
                {
                    this.crystalCaveGenerationChance = builder.comment("The chance for a crystal cave to generate. This value is interpreted as \"1 out of X\" to generate it.").defineInRange("crystalCaveGenerationChance", 5, 1, Integer.MAX_VALUE);
                }
                builder.pop();
            }
            builder.pop();
        }
    }

    public static class Server
    {
        public final ForgeConfigSpec.DoubleValue stormTrooperBackUpChance;

        Server(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Server configuration settings").push("server");
            {
                builder.comment("Mob Settings").push("mobs");
                {
                    stormTrooperBackUpChance = builder.comment("The chance for stormtroopers to call in reinforcements").defineInRange("stormTrooperBackUpChance", 0.5D, 0.0D, Integer.MAX_VALUE);
                }
                builder.pop();
            }
            builder.pop();
        }
    }

    public static class Client
    {
        Client(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Client configuration settings").push("client");
            {

            }
            builder.pop();
        }
    }

    static final ForgeConfigSpec clientSpec;
    public static final Config.Client CLIENT;

    static final ForgeConfigSpec commonSpec;
    public static final Config.Common COMMON;

    static final ForgeConfigSpec serverSpec;
    public static final Config.Server SERVER;

    static
    {
        final Pair<Client, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Client::new);
        clientSpec = clientSpecPair.getRight();
        CLIENT = clientSpecPair.getLeft();

        final Pair<Common, ForgeConfigSpec> commonSpecPair = new ForgeConfigSpec.Builder().configure(Common::new);
        commonSpec = commonSpecPair.getRight();
        COMMON = commonSpecPair.getLeft();

        final Pair<Server, ForgeConfigSpec> serverSpecPair = new ForgeConfigSpec.Builder().configure(Server::new);
        serverSpec = serverSpecPair.getRight();
        SERVER = serverSpecPair.getLeft();
    }
}
