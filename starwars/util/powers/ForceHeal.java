package mod.syconn.starwars.util.powers;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.PotionItem;

import java.util.List;

public class ForceHeal implements ForcePower {

    @Override
    public int getLevel(PlayerEntity player) {
        return 1;
    }

    @Override
    public void usePower(PlayerEntity player){
        player.heal(10F * getLevel(player));
    }

    @Override
    public int calculateDamage(PlayerEntity player, List<MobEntity> entities) {
        return 0;
    }

    @Override
    public void applyKnockBack(PlayerEntity player, List<MobEntity> entities) {

    }
}
