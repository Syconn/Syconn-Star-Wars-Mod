package mod.syconn.starwars.world.gen.feature.configs;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class CrystalCaveConfig implements IFeatureConfig
{
    public final int chance;
    public final ResourceLocation template;

    public CrystalCaveConfig(int chance, ResourceLocation template)
    {
        this.chance = chance;
        this.template = template;
    }

    @Override
    public <T> Dynamic<T> serialize(DynamicOps<T> ops)
    {
        return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("chance"), ops.createInt(this.chance), ops.createString("template"), ops.createString(this.template.toString()))));
    }

    public static CrystalCaveConfig deserialize(Dynamic<?> dynamic)
    {
        int chance = dynamic.get("chance").asInt(0);
        ResourceLocation template = new ResourceLocation(dynamic.get("template").asString("minecraft:empty"));
        return new CrystalCaveConfig(chance, template);
    }
}
