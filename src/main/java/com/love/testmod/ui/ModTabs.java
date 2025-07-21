package com.love.testmod.ui;

import com.love.testmod.block.ModBlocks;
import com.love.testmod.item.ModItems;
import io.netty.handler.ssl.JdkApplicationProtocolNegotiator;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.love.testmod.TestMod.MODID;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB,MODID);

    public static final DeferredHolder<CreativeModeTab,CreativeModeTab> TEST_MOD = CREATIVE_TABS.register("test_mod", () -> CreativeModeTab.builder()
            .icon(Items.ACACIA_BOAT::getDefaultInstance)
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .displayItems((itemDisplayParameters, output) -> {
                output.accept(ModItems.TEST_ITEM.get());
                output.accept(ModBlocks.TEST_BLOCK_ITEM.get());
            }).build());
}
