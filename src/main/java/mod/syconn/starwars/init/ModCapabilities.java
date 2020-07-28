package mod.syconn.starwars.init;

import mod.syconn.starwars.capability.ForcePowers;
import mod.syconn.starwars.capability.interfaces.IForceSensitive;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nullable;

public class ModCapabilities {

    @CapabilityInject(IForceSensitive.class)
    public static Capability<IForceSensitive> FORCE_CAPABILITY = null;

    public static void register() {
        CapabilityManager.INSTANCE.register(IForceSensitive.class, new Capability.IStorage<IForceSensitive>() {
            @Nullable
            @Override
            public INBT writeNBT(Capability<IForceSensitive> capability, IForceSensitive instance, Direction side) {
                return null;
            }

            @Override
            public void readNBT(Capability<IForceSensitive> capability, IForceSensitive instance, Direction side, INBT nbt) {

            }
        }, ForcePowers::new);
    }

}
