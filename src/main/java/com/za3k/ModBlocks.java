package com.za3k;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;


public class ModBlocks {
    public static Block register(Function<AbstractBlock.Settings, Block> factory, AbstractBlock.Settings settings, String name, boolean shouldRegisterItem) {
        Identifier id = Identifier.of(DirtSlabMod.MOD_ID, name);
        RegistryKey<Block> blockKey = RegistryKey.of(RegistryKeys.BLOCK, id);

        Block block = factory.apply(settings.registryKey(blockKey));

        if (shouldRegisterItem)  {
            RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, id);
            BlockItem blockItem = new BlockItem(block, new Item.Settings().useBlockPrefixedTranslationKey().registryKey(itemKey));
            Registry.register(Registries.ITEM, id, blockItem);
        }

        return Registry.register(Registries.BLOCK, id, block);
    }

    public static final Block DIRT_SLAB = register(
            SlabBlock::new,
            AbstractBlock.Settings.create().sounds(BlockSoundGroup.GRAVEL)
                    .strength(0.5f, 0.5f),
            "dirt_slab",
            true
    );

    public static void initialize() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register((itemGroup) -> {
            itemGroup.add(ModBlocks.DIRT_SLAB.asItem());
        });

    }
}
