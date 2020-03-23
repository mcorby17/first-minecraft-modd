package com.example.examplemod;

import com.example.examplemod.lists.BlockList;
import com.example.examplemod.lists.ItemList;
import com.example.examplemod.lists.ToolMaterialList;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ExampleMod.MOD_ID)
public class ExampleMod
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "examplemod";
    private static final ModItemGroup mod_group = new ModItemGroup();

    public ExampleMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        InterModComms.sendTo(ExampleMod.MOD_ID, "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(FMLServerStartingEvent event) {
        // do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
    // Event bus for receiving Registry Events)
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> blockItemEvent)
        {
            ItemList.bomb_item = new Item(new Item.Properties().group(mod_group));
            ItemList.bomb_item.setRegistryName(new ResourceLocation(ExampleMod.MOD_ID, ItemList.bomb_item_name));

            ItemList.cow_block = new BlockItem(BlockList.cow_block, new Item.Properties().group(mod_group));
            ItemList.cow_block.setRegistryName(new ResourceLocation(ExampleMod.MOD_ID, BlockList.cow_block_name));

            // TODO figure out WTF each of these fields does in the thingItem constructors
            ItemList.axe_tool = new AxeItem(ToolMaterialList.THAT_GOOD_STUFF, -1.0f, 6.0f,
                    new Item.Properties().group(mod_group));
            ItemList.axe_tool.setRegistryName(new ResourceLocation(MOD_ID, "axe_tool"));

            ItemList.hoe_tool = new HoeItem(ToolMaterialList.THAT_GOOD_STUFF, 6.0f, new Item.Properties().group(mod_group));
            ItemList.hoe_tool.setRegistryName(new ResourceLocation(MOD_ID, "hoe_tool"));

            ItemList.pickaxe_tool = new PickaxeItem(ToolMaterialList.THAT_GOOD_STUFF, -2, 6.0f,
                    new Item.Properties().group(mod_group));
            ItemList.pickaxe_tool.setRegistryName(new ResourceLocation(MOD_ID, "pickaxe_tool"));

            ItemList.shovel_tool = new ShovelItem(ToolMaterialList.THAT_GOOD_STUFF, -3.0f, 6.0f,
                    new Item.Properties().group(mod_group));
            ItemList.shovel_tool.setRegistryName(new ResourceLocation(MOD_ID, "shovel_tool"));

            ItemList.sword_tool = new SwordItem(ToolMaterialList.THAT_GOOD_STUFF, 0, 6.0f,
                    new Item.Properties().group(mod_group));
            ItemList.sword_tool.setRegistryName(new ResourceLocation(MOD_ID, "sword_tool"));

            blockItemEvent.getRegistry().registerAll(ItemList.bomb_item,
                                                     ItemList.cow_block,
                                                     ItemList.axe_tool,
                                                     ItemList.hoe_tool,
                                                     ItemList.pickaxe_tool,
                                                     ItemList.shovel_tool,
                                                     ItemList.sword_tool);

            LOGGER.info("Items registered.");
        }

        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
            BlockList.cow_block = new Block(Block.Properties.create(Material.CAKE)
                                                .hardnessAndResistance(2.0f, 2.0f)
                                                .sound(SoundType.GLASS));

            BlockList.cow_block.setRegistryName(new ResourceLocation(MOD_ID, BlockList.cow_block_name));

            blockRegistryEvent.getRegistry().registerAll(BlockList.cow_block);

            LOGGER.info("Blocks registered.");
        }
    }
}
