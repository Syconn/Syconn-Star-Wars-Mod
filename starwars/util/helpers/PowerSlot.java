package mod.syconn.starwars.util.helpers;

import mod.syconn.starwars.util.enums.PowersEnum;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class PowerSlot {

    private PowersEnum power;
    private int x;
    private int y;
    private int id;
    private int coolDown;
    private boolean isSelected;

    public PowerSlot(int x, int y, int id, PowersEnum power) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.coolDown = power.getCoolDown();
        this.power = power;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getID() {
        return id;
    }

    public int getCoolDown() {
        return coolDown;
    }

    public PowersEnum getPower() {
        return power;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public boolean isCoolingDown(){
        return coolDown != power.getCoolDown();
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public void setCoolDown(int value) {
        coolDown = value;
    }

    public void increaseCoolDown() {
        System.out.println(coolDown);
        if (coolDown == power.getCoolDown()) {
            return;
        }
        coolDown++;
    }

    public void setPower(PowersEnum power) {
        this.power = power;
    }

    public CompoundNBT serializeNBT(CompoundNBT nbt) {
        nbt.putInt("x", x);
        nbt.putInt("y", y);
        nbt.putInt("id", id);
        nbt.putInt("power", power.getId());
        nbt.putInt("coolDown", coolDown);
        return nbt;
    }

    public void deserializeNBT(CompoundNBT nbt) {
        x = nbt.getInt("x");
        y = nbt.getInt("y");
        id = nbt.getInt("id");
        coolDown = nbt.getInt("coolDown");
        power = PowersEnum.getForcePowerById(nbt.getInt("power"));
    }
}
