package mod.syconn.starwars.init;

import mod.syconn.starwars.Main;
import mod.syconn.starwars.item.HammerItem;
import mod.syconn.starwars.item.KyberCrystalItem;
import mod.syconn.starwars.item.LightsaberItem;
import mod.syconn.starwars.util.Reference;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = Reference.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {

    public static final List<Item> ITEMS = new ArrayList<>();

    public static final Item LIGHTSABER = register("lightsaber", new LightsaberItem(new Item.Properties().maxStackSize(1).group(Main.StarWarsGroup.instance)));
    public static final Item KYBER_CRYSTAL = register("kyber_crystal_item", new KyberCrystalItem(new Item.Properties().maxStackSize(16).group(Main.StarWarsGroup.instance)));
    public static final Item HAMMER = register("hammer", new HammerItem());
    public static final Item BLASTER_RIFLE = register("blaster_rifle", new Item(new Item.Properties().group(Main.StarWarsGroup.instance).maxStackSize(1)));
    public static final Item STEEL_INGOT = register("steel_ingot", new Item(new Item.Properties().group(Main.StarWarsGroup.instance)));
    public static final Item CRUSHED_GOLD = register("crushed_gold", new Item(new Item.Properties().group(Main.StarWarsGroup.instance)));
    public static final Item CHARGED_GOLD = register("charged_gold", new Item(new Item.Properties().group(Main.StarWarsGroup.instance)));

    private static Item register(String name, Item item)
    {
        item.setRegistryName(new ResourceLocation(Reference.MOD_ID, name));
        ModItems.ITEMS.add(item);
        return item;
    }

    @SubscribeEvent
    @SuppressWarnings("unused")
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();
        ITEMS.forEach(registry::register);
        ITEMS.clear();;
    }
}