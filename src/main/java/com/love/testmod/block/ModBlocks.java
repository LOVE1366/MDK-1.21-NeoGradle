package com.love.testmod.block;

import com.love.testmod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

import static com.love.testmod.TestMod.MODID;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static DeferredBlock<Block> registerBlock(String name, Supplier<Block> blockSupplier){
        return BLOCKS.register(name,blockSupplier);
    }
    public static DeferredItem<BlockItem> registerBlockItem(String name, Supplier<BlockItem> itemSupplier){
        return ModItems.ITEMS.register(name,itemSupplier);
    }

    public static final DeferredBlock<Block> TEST_BLOCK = registerBlock("test_block", ()-> new TestBlock(BlockBehaviour.Properties.of()));
    public static final DeferredItem<BlockItem> TEST_BLOCK_ITEM = registerBlockItem("test_block", ()-> new BlockItem(TEST_BLOCK.get(), new Item.Properties()));
}
