package mod.syconn.starwars.capability;

import mod.syconn.starwars.util.Reference;
import mod.syconn.starwars.util.enums.PowersEnum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ForcePowers {

    @CapabilityInject(IForceSensitive.class)
    public static final Capability<IForceSensitive> CAPABILITY_FORCE_SENSITIVE = null;

    public static void register(){
        CapabilityManager.INSTANCE.register(IForceSensitive.class, new Storage(), ForceSensitive::new);
        MinecraftForge.EVENT_BUS.register(new ForcePowers());
    }

    @Nullable
    public static IForceSensitive getHandler(PlayerEntity player) {
        return player.getCapability(CAPABILITY_FORCE_SENSITIVE, Direction.DOWN).orElse(null);
    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<Entity> event)
    {
        if(event.getObject() instanceof PlayerEntity)
        {
            event.addCapability(new ResourceLocation(Reference.MOD_ID, "force"), new ForcePowers.Provider());
            event.getObject().getCapability(CAPABILITY_FORCE_SENSITIVE).ifPresent( data -> data.setStamina(data.getMaxStamina()));
        }
    }

    public interface IForceSensitive
    {
        int getMaxStamina();
        void setMaxStamina(int value);
        int getStamina();
        void setStamina(int value);
        void refreshStamina(boolean value);
        void increaseStamina();
        void tick();
        boolean canUsePower(PlayerEntity player, PowersEnum power);
    }

    public static class ForceSensitive implements IForceSensitive
    {
        private int stamina;
        private int maxStamina = 200;
        private boolean refreshStamina = false;

        @Override
        public int getMaxStamina() {
            return maxStamina;
        }

        @Override
        public void setMaxStamina(int value) {
            maxStamina = value;
        }

        @Override
        public int getStamina() {
            return stamina;
        }

        @Override
        public void setStamina(int value) {
            stamina = value;
        }

        @Override
        public void increaseStamina() {
            stamina++;
        }

        @Override
        public void refreshStamina(boolean value) {
            refreshStamina = value;
        }

        @Override
        public void tick() {
            if (stamina < maxStamina && refreshStamina)
                increaseStamina();

            if (stamina >= maxStamina)
                refreshStamina = false;
        }

        @Override
        public boolean canUsePower(PlayerEntity player, PowersEnum power) {
            System.out.println(stamina);
            System.out.println(power.getStaminaRequired());

            if (stamina >= power.getStaminaRequired() && !refreshStamina) {
                System.out.println(stamina);
                setStamina(stamina -= power.getStaminaRequired());
                System.out.println(stamina);
                return true;
            }

            refreshStamina = true;
            return false;
        }
    }

    public static class Storage implements Capability.IStorage<IForceSensitive>
    {
        @Nullable
        @Override
        public INBT writeNBT(Capability<IForceSensitive> capability, IForceSensitive instance, Direction side) {
            CompoundNBT nbt = new CompoundNBT();
            nbt.putInt("stamina", instance.getStamina());
            nbt.putInt("max", instance.getMaxStamina());
            System.out.println("writing");
            return nbt;
        }

        @Override
        public void readNBT(Capability<IForceSensitive> capability, IForceSensitive instance, Direction side, INBT nbt) {
            CompoundNBT compound = (CompoundNBT) nbt;
            instance.setStamina(compound.getInt("stamina"));
            instance.setMaxStamina(compound.getInt("max"));
            System.out.println("Reading");
        }
    }

    public static class Provider implements ICapabilitySerializable<CompoundNBT>
    {
        final IForceSensitive INSTANCE = CAPABILITY_FORCE_SENSITIVE.getDefaultInstance();

        @Override
        public CompoundNBT serializeNBT()
        {
            return (CompoundNBT) CAPABILITY_FORCE_SENSITIVE.getStorage().writeNBT(CAPABILITY_FORCE_SENSITIVE, INSTANCE, null);
        }

        @Override
        public void deserializeNBT(CompoundNBT compound)
        {
            CAPABILITY_FORCE_SENSITIVE.getStorage().readNBT(CAPABILITY_FORCE_SENSITIVE, INSTANCE, null, compound);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side)
        {
            return CAPABILITY_FORCE_SENSITIVE.orEmpty(cap, LazyOptional.of(() -> INSTANCE));
        }
    }
}
