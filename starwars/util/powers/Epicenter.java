package mod.syconn.starwars.util.powers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class Epicenter implements ForcePower {

    private final int minDamage = 80;

    @Override
    public int getLevel(PlayerEntity player) {
        //Will return something like:: return data.getAbilityLevel(ForcePowerEnum.Epicenter);
        return 1;
    }

    @Override
    public void usePower(PlayerEntity player) {
        List<MobEntity> entities = player.world.getEntitiesWithinAABB(MobEntity.class, player.getBoundingBox().grow(3 * getLevel(player), 0, 3 * getLevel(player)), LivingEntity::isAlive);
        entities.remove(player);
        System.out.println("I Hate Cows");

        double stompStrength = 0.3 * (getLevel(player) / 2.0f);
        Vec3d direction = new Vec3d(player.getPosX() - player.getPosX(), 0, player.getPosZ() - player.getPosZ()).normalize();
        player.setMotion(direction.x * stompStrength, stompStrength * 2, direction.z * stompStrength);
        player.addVelocity(direction.x * stompStrength, stompStrength * 3, direction.z * stompStrength);
        player.velocityChanged = true;

        applyKnockBack(player, entities);
    }

    @Override
    public int calculateDamage(PlayerEntity player, List<MobEntity> entities) {
        return (minDamage * getLevel(player)) / entities.size();
    }

    @Override
    public void applyKnockBack(PlayerEntity player, List<MobEntity> entities) {
        double stompStrength = 0.3 * ((entities.size() * 2) / 4.0f);
        Vec3d direction = new Vec3d(player.getPosX() - player.getPosX(), 0, player.getPosZ() - player.getPosZ()).normalize();

        for (MobEntity entity : entities){
            entity.knockBack(entity, 1f, player.getPosX() - entity.getPosX(), player.getPosZ() - entity.getPosZ());
            //entity.setMotion(direction.x * stompStrength, stompStrength * 3, direction.z * stompStrength);
            //entity.addVelocity(direction.x * stompStrength, stompStrength * 3, direction.z * stompStrength);
            entity.attackEntityFrom(damage, calculateDamage(player, entities));
            entity.setRevengeTarget(player);
        }
    }
}