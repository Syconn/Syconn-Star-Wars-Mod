package mod.syconn.starwars.util.powers;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class ForcePush implements ForcePower {
    private final int minDamage = 40;

    @Override
    public void usePower(PlayerEntity player) {
        List<MobEntity> entities = player.world.getEntitiesWithinAABB(MobEntity.class, player.getBoundingBox().grow(1 * getLevel(player), 0, 3 * getLevel(player)).offset(player.getForward().add(1, 0, 0)), LivingEntity::isAlive);
        for (MobEntity entity : entities)
            System.out.println(entity.getDisplayName());
        applyKnockBack(player, entities);
    }

    @Override
    public int getLevel(PlayerEntity player) {
        return 1;
    }

    @Override
    public int calculateDamage(PlayerEntity player, List<MobEntity> entities) {
        return (minDamage * getLevel(player)) / entities.size() + 1;
    }

    @Override
    public void applyKnockBack(PlayerEntity player, List<MobEntity> entities) {
        for (MobEntity entity : entities){
            entity.knockBack(entity, 5f, player.getPosX() - entity.getPosX(), player.getPosZ() - entity.getPosZ());
            entity.attackEntityFrom(damage, calculateDamage(player, entities));
            entity.setRevengeTarget(player);
        }
    }
}
