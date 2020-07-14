package mod.syconn.starwars.init;

import mod.syconn.starwars.Main;
import mod.syconn.starwars.block.KyberCrystalBlock;
import mod.syconn.starwars.block.LightsaberCrafterBlock;
import mod.syconn.starwars.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {

    private static final List<Block> BLOCKS = new ArrayList<>();

    public static final Block LIGHTSABER_CRAFTER = register(new ResourceLocation(Reference.MOD_ID, "lightsaber_crafter"), new LightsaberCrafterBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(2.8F)), Main.StarWarsGroup.instance);
    public static final Block KYBER_CRYSTAL = register(new ResourceLocation(Reference.MOD_ID, "kyber_crystal"), new KyberCrystalBlock(Block.Properties.create(Material.ICE).hardnessAndResistance(1.4F).sound(SoundType.GLASS)), Main.StarWarsGroup.instance);
    public static final Block STEEL_BLOCK = register(new ResourceLocation(Reference.MOD_ID, "steel_block"), new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(5.0F, 6.0F).sound(SoundType.METAL)), Main.StarWarsGroup.instance);

    private static Block register(ResourceLocation key, Block block, ItemGroup group)
    {
        block.setRegistryName(key);
        BLOCKS.add(block);
        BlockItem item = new BlockItem(block, new Item.Properties().group(group));
        item.setRegistryName(key);
        ModItems.ITEMS.add(item);
        return block;
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        IForgeRegistry<Block> registry = event.getRegistry();
        BLOCKS.forEach(registry::register);
        BLOCKS.clear();;
    }
}
