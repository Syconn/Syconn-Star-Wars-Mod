package mod.syconn.starwars.util.powers;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;

import java.util.List;

public interface ForcePower {

    DamageSource damage = new DamageSource("force").setDamageBypassesArmor();

    int getLevel(PlayerEntity player);
    void usePower(PlayerEntity player);
    int calculateDamage(PlayerEntity player, List<MobEntity> entities);
    void applyKnockBack(PlayerEntity player, List<MobEntity> entities);
}
