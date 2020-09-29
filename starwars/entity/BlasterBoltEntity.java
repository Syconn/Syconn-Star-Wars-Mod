package mod.syconn.starwars.entity;

import mod.syconn.starwars.init.ModEntities;
import mod.syconn.starwars.init.ModItems;
import net.minecraft.client.renderer.entity.GuardianRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.monster.BlazeEntity;
import net.minecraft.entity.monster.GuardianEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.Item;
import net.minecraft.network.IPacket;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
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
            if(entity instanceof PlayerEntity && ((PlayerEntity) entity).isActiveItemStackBlocking() && ((PlayerEntity) entity).getHeldItemMainhand().getItem() == ModItems.LIGHTSABER){
                RayTrace((PlayerEntity)entity);
            }

            else entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 5f);
        }


        if (!this.world.isRemote) {
            this.world.setEntityState(this, (byte)3);
            this.remove();
        }
    }

    private void RayTrace(PlayerEntity player){
        float f = player.rotationPitch;
        float f1 = player.rotationYaw;
        Vec3d vec3d = player.getEyePosition(1.0F);
        float f2 = MathHelper.cos(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f3 = MathHelper.sin(-f1 * ((float)Math.PI / 180F) - (float)Math.PI);
        float f4 = -MathHelper.cos(-f * ((float)Math.PI / 180F));
        float f5 = MathHelper.sin(-f * ((float)Math.PI / 180F));
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        double d0 = 100; //distance
        Vec3d vec3d1 = vec3d.add((double)f6 * d0, (double)f5 * d0, (double)f7 * d0);
        RayTraceResult rts = player.world.rayTraceBlocks(new RayTraceContext(vec3d, vec3d1, RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.ANY, player));

        if (rts != null && rts.getType() == RayTraceResult.Type.ENTITY || rts.getType() == RayTraceResult.Type.BLOCK) {

            if (player.world instanceof ServerWorld) {
                ServerWorld world = (ServerWorld) player.world;
                BlasterBoltEntity b = new BlasterBoltEntity(world, player);
                b.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 3.0F, 1.0F);
                world.addEntity(b);
            }
        }

    }
}
