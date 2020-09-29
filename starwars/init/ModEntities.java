package mod.syconn.starwars.init;

import mod.syconn.starwars.entity.BlasterBoltEntity;
import mod.syconn.starwars.entity.StormTrooperEntity;
import mod.syconn.starwars.util.Reference;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {

    private static final List<EntityType<?>> ENTITY_TYPES = new ArrayList<>();

    public static final EntityType<BlasterBoltEntity> BLASTER_BOLT_ENTITY = build("blaster_bolt_entity", EntityClassification.CREATURE, BlasterBoltEntity::new, 0.25F, 0.25F);
    public static final EntityType<StormTrooperEntity> STORMTROPER = build("stormtroper", EntityClassification.MONSTER, StormTrooperEntity::new, 0.5f, 2.0f);

    private static <T extends Entity> EntityType<T> build(String id, EntityClassification classification, Function<World, T> function, float width, float height)
    {
        EntityType<T> type = EntityType.Builder.<T>create((entityType, world) -> function.apply(world), classification).size(width, height).setCustomClientFactory((spawnEntity, world) -> function.apply(world)).build(new ResourceLocation(Reference.MOD_ID, id).toString());
        type.setRegistryName(new ResourceLocation(Reference.MOD_ID, id));
        ENTITY_TYPES.add(type);
        return type;
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerTypes(final RegistryEvent.Register<EntityType<?>> event)
    {
        IForgeRegistry<EntityType<?>> registry = event.getRegistry();
        ENTITY_TYPES.forEach(registry::register);
    }
}
