package mod.syconn.starwars.capability.interfaces;

import mod.syconn.starwars.util.enums.ForceSideEnum;
import mod.syconn.starwars.util.enums.PowersEnum;
import mod.syconn.starwars.util.helpers.PowerSlot;
import net.minecraft.entity.player.PlayerEntity;

public interface IForceSensitive {

    PowerSlot getSlot0();
    PowerSlot getSlot1();
    PowerSlot getSlot2();
    int getStamina();
    int getMaxStamina();
    boolean isRecharging();
    ForceSideEnum getSide();

    void increaseStamina();
    void increaseMaxStamina();

    void decreaseStamina();
    void decreaseMaxStamina();

    void setStamina(int value);
    void setMaxStamina(int value);

    void setIsRecharging(boolean value);
    ForceSideEnum setSide(ForceSideEnum side);

    PowerSlot slot(int num);
    void tick(IForceSensitive data);
    boolean canUsePower(IForceSensitive data, PlayerEntity player, int num);
}
