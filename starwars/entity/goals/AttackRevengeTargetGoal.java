package mod.syconn.starwars.entity.goals;

import mod.syconn.starwars.entity.StormTrooperEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;

import java.util.EnumSet;
import java.util.List;

/**
 * Author: MrCrayfish
 */
public class AttackRevengeTargetGoal extends Goal {
    private StormTrooperEntity entity;

    public AttackRevengeTargetGoal(StormTrooperEntity entity) {
        this.entity = entity;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean shouldExecute() {
        return this.entity.getRevengeTarget() != null && this.entity.getRevengeTarget().isAlive() && this.entity.getDistance(this.entity.getRevengeTarget()) <= 10.0F && (!(this.entity.getRevengeTarget() instanceof PlayerEntity) || !((PlayerEntity) this.entity.getRevengeTarget()).isCreative());
    }

    @Override
    public void tick() {
        LivingEntity revengeTarget = this.entity.getRevengeTarget();
        if (revengeTarget != null && !(revengeTarget instanceof StormTrooperEntity)) {
            List<StormTrooperEntity> squad = entity.world.getEntitiesWithinAABB(StormTrooperEntity.class, entity.getBoundingBox().expand(4, 1, 4), MobEntity::isAlive);
            for (StormTrooperEntity trooper : squad){
                trooper.setAttackTarget(revengeTarget);
                trooper.setAggroed(true);
            }
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.entity.getRevengeTarget() != null && this.entity.getRevengeTarget().isAlive() && this.entity.getDistance(this.entity.getRevengeTarget()) <= 25.0F;
    }

    @Override
    public void resetTask() {
        this.entity.setRevengeTarget(null);
    }
}
