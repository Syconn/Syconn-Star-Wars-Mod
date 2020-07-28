package mod.syconn.starwars.util.powers;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.List;

public class ForcePush implements ForcePower {

    @Override
    public void usePower(PlayerEntity player) {

    }

    @Override
    public int getLevel(PlayerEntity player) {
        return 1;
    }

    @Override
    public int calculateDamage(PlayerEntity player, List<MobEntity> entities) {
        return 0;
    }

    @Override
    public void applyKnockBack(PlayerEntity player, List<MobEntity> entities) {

    }
}
