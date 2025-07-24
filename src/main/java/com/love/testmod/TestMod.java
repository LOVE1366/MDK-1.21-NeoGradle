package com.love.testmod;

import com.love.testmod.block.ModBlocks;
import com.love.testmod.item.ModItems;
import com.love.testmod.tile.ModBlockEntityTypes;
import com.love.testmod.ui.ModTabs;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(TestMod.MODID)
public class TestMod {
    public static final String MODID = "testmod";

    private static ModelLayerLocation loc(String name) {
        return new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(MODID, name), name);
    }

    public TestMod(IEventBus modEventBus, ModContainer modContainer) {
        ModItems.ITEMS.register(modEventBus);
        ModBlocks.BLOCKS.register(modEventBus);
        ModTabs.CREATIVE_TABS.register(modEventBus);
        ModBlockEntityTypes.BLOCK_ENTITY_TYPES.register(modEventBus);
    }
}
