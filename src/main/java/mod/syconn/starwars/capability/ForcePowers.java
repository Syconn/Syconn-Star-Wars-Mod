package mod.syconn.starwars.capability;

import mod.syconn.starwars.capability.interfaces.IForceSensitive;
import mod.syconn.starwars.init.ModCapabilities;
import mod.syconn.starwars.util.enums.ForceSideEnum;
import mod.syconn.starwars.util.enums.PowersEnum;
import mod.syconn.starwars.util.helpers.PowerSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ForcePowers implements IForceSensitive, ICapabilitySerializable<CompoundNBT> {

    private LazyOptional<IForceSensitive> instance = LazyOptional.of(ModCapabilities.FORCE_CAPABILITY::getDefaultInstance);

    private int maxStamina = 200;
    private int stamina = 200;
    private ForceSideEnum side = ForceSideEnum.JEDI;
    private int selectedSlot = 0;
    private boolean rechargingStamina = false;

    private String MAX_STAMINA = "maxStamina";
    private String STAMINA = "stamina";
    private String SIDE = "side";
    private String SELECTED_SLOT = "selectedSlot";
    private String RECHARGING = "recharging";

    private PowerSlot slot0 = new PowerSlot(0, 0, 0, PowersEnum.PUSH);
    private PowerSlot slot1 = new PowerSlot(0, 0, 1, PowersEnum.EPICENTER);
    private PowerSlot slot2 = new PowerSlot(0, 0, 2, PowersEnum.HEAL);

    public ForcePowers() {
    }

    @Override
    public int getStamina() {
        return stamina;
    }

    @Override
    public int getMaxStamina() {
        return maxStamina;
    }

    @Override
    public PowerSlot getSlot0() {
        return slot0;
    }

    @Override
    public PowerSlot getSlot1() {
        return slot1;
    }

    @Override
    public PowerSlot getSlot2() {
        return slot2;
    }

    @Override
    public ForceSideEnum getSide() {
        return side;
    }

    @Override
    public boolean isRecharging() {
        return rechargingStamina;
    }

    @Override
    public void increaseStamina() {
        if (stamina == maxStamina){
            return;
        }
        maxStamina++;
    }

    @Override
    public void decreaseStamina() {
        if (stamina == 0){
            return;
        }
        maxStamina--;
    }

    @Override
    public void increaseMaxStamina() {
        maxStamina++;
    }

    @Override
    public void decreaseMaxStamina() {
        if (maxStamina == 0){
            return;
        }
        maxStamina--;
    }

    @Override
    public void setStamina(int value) {
        stamina = value;
    }

    @Override
    public void setMaxStamina(int value) {
        stamina = value;
    }

    @Override
    public ForceSideEnum setSide(ForceSideEnum side) {
        return this.side = side;
    }

    @Override
    public PowerSlot slot(int num)
    {
        switch (num){
            case 1:
                return slot1;
            case 2:
                return slot2;

            default:
                return slot0;
        }
    }

    @Override
    public void setIsRecharging(boolean value) {
        rechargingStamina = value;
    }

    @Override
    public boolean canUsePower(IForceSensitive data, PlayerEntity player, int abilityNum)
    {
        if (!data.slot(abilityNum).isCoolingDown()){
            data.slot(abilityNum).setCoolDown(0);

            if (data.getStamina() >= data.slot(abilityNum).getPower().getStaminaRequired()){
                data.setStamina(data.getStamina() - data.slot(abilityNum).getPower().getStaminaRequired());

                return true;
            }
        }

        return false;
    }

    @Override
    public void tick(IForceSensitive data) {
        data.getSlot0().increaseCoolDown();
        data.getSlot1().increaseCoolDown();
        data.getSlot2().increaseCoolDown();

        if (data.getStamina() <= 45){
            data.setIsRecharging(true);
        }

        if (data.isRecharging())
            increaseStamina();

        if (data.getStamina() == maxStamina)
            data.setIsRecharging(false);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == ModCapabilities.FORCE_CAPABILITY ? instance.cast() : LazyOptional.empty();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        return getCapability(cap, null);
    }

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.putInt(MAX_STAMINA, maxStamina);
        nbt.putInt(STAMINA, stamina);
        nbt.putInt(SELECTED_SLOT, selectedSlot);
        nbt.putInt(SIDE, side.getID());
        nbt.putBoolean(RECHARGING, rechargingStamina);
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        maxStamina = nbt.getInt(MAX_STAMINA);
        stamina = nbt.getInt(STAMINA);
        selectedSlot = nbt.getInt(SELECTED_SLOT);
        side = ForceSideEnum.getForcePowerById(nbt.getInt(SIDE));
        rechargingStamina = nbt.getBoolean(RECHARGING);
    }
}
