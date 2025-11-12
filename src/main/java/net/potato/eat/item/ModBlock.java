package net.potato.eat.item;


import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.potato.eat.Learningfabric;

public class ModBlock {



    public static final Block REVIVE_BEACON = registerBlock("revive_beacon", AbstractBlock.Settings.create().strength(4.0F).sounds(BlockSoundGroup.GLASS).nonOpaque());

    private static Block registerBlock2(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Learningfabric.MOD_ID, name), block);
    }

    private static void registerBlockItem2(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Learningfabric.MOD_ID, name), new BlockItem(block, new Item.Settings()));
    }
        private static Block registerBlock(String name, AbstractBlock.Settings blockSettings)
        {
            RegistryKey<Block> key = RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Learningfabric.MOD_ID, name));
            Block block1 = new Block(blockSettings.registryKey(key));
            registerBlockItem(name, block1);
            return Registry.register(Registries.BLOCK, key, block1);
        }

        private static void registerBlockItem(String name, Block block2) {
            RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Learningfabric.MOD_ID, name));
            BlockItem item = new BlockItem(block2, new Item.Settings().registryKey(key));
            Registry.register(Registries.ITEM, key, item);
        }


    public static void register(){
        Learningfabric.LOGGER.info("Registering ModBlocks");


    }
}
