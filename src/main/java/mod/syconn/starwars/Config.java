package mod.syconn.starwars;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Config
{
    public static class Common
    {
        public final ForgeConfigSpec.IntValue caveCampGenerateChance;

        Common(ForgeConfigSpec.Builder builder)
        {
            builder.comment("Common configuration settings").push("common");
            {
                builder.comment("Structures").push("structures");
                {
                    this.caveCampGenerateChance = builder.comment("The chance for a survival camp to generate. This value is interpreted as \"1 out of X\" to generate a survival camp.").defineInRange("survivalCampGenerateChance", 5, 1, Integer.MAX_VALUE);
                }
                builder.pop();
            }
            builder.pop();
        }
    }

    static final ForgeConfigSpec commonSpec;
    public static final Common COMMON;

    static
    {
        final Pair<Common, ForgeConfigSpec> clientSpecPair = new ForgeConfigSpec.Builder().configure(Config.Common::new);
        commonSpec = clientSpecPair.getRight();
        COMMON = clientSpecPair.getLeft();
    }
}
