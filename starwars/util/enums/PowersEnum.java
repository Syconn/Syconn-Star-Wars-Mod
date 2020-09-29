package mod.syconn.starwars.util.enums;

import mod.syconn.starwars.util.powers.Epicenter;
import mod.syconn.starwars.util.powers.ForceHeal;
import mod.syconn.starwars.util.powers.ForcePower;
import mod.syconn.starwars.util.powers.ForcePush;
import net.minecraft.util.ResourceLocation;

public enum PowersEnum {

    PUSH(0, ForceSideEnum.BOTH, 40, 100, new ForcePush()),
    EPICENTER(1, ForceSideEnum.BOTH, 65, 140, new Epicenter()),
    HEAL(2, ForceSideEnum.JEDI, 60, 200, new ForceHeal()),
    CHOKE(3, ForceSideEnum.SITH, 60, 120, null);

    private int id;
    private ForceSideEnum side;
    private int staminaRequired;
    private int coolDown;
    private ForcePower power;

    PowersEnum(int id, ForceSideEnum side, int staminaRequired, int coolDown, ForcePower power) {
        this.id = id;
        this.side = side;
        this.staminaRequired = staminaRequired;
        this.coolDown = coolDown;
        this.power = power;
    }

    public int getId() {
        return id;
    }

    public ForceSideEnum getSide() {
        return side;
    }

    public int getStaminaRequired() {
        return staminaRequired;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public ForcePower getPower() {
        return power;
    }

    public static PowersEnum getForcePowerById(int id){
        for (PowersEnum power : PowersEnum.values()){
            if (power.id == id){
                return power;
            }
        }
        return null;
    }
}
