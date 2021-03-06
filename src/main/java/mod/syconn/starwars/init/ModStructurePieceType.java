package mod.syconn.starwars.init;

import mod.syconn.starwars.world.gen.feature.structure.CrystalCave;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public class ModStructurePieceType {

    public static final IStructurePieceType CRYSTAL_CAVE = register(CrystalCave.Piece::new, "swm:crystal_cave");

    public static void init() {} //Force static fields to initialize

    private static IStructurePieceType register(IStructurePieceType type, String key)
    {
        return Registry.register(Registry.STRUCTURE_PIECE, new ResourceLocation(key), type);
    }
}
