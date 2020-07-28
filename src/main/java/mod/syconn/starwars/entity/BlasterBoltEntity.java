package mod.syconn.starwars.entity;

import mod.syconn.starwars.init.ModEntities;
import mod.syconn.starwars.init.ModItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class BlasterBoltEntity extends ProjectileItemEntity {

    public BlasterBoltEntity(World worldIn)
    {
        super(ModEntities.BLASTER_BOLT_ENTITY, worldIn);
    }

    public BlasterBoltEntity(World worldIn, LivingEntity throwerIn) {
        super(ModEntities.BLASTER_BOLT_ENTITY, throwerIn, worldIn);
    }

    public BlasterBoltEntity(World worldIn, double x, double y, double z) {
        super(ModEntities.BLASTER_BOLT_ENTITY, x, y, z, worldIn);
    }

    @Override
    public IPacket<?> createSpawnPacket()
    {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.BLASTER_BOLT;
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        if (result.getType() == RayTraceResult.Type.ENTITY) {
            Entity entity = ((EntityRayTraceResult)result).getEntity();
            entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 5f);
        }

        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)3);
            this.remove();
        }
    }
}
