package com.love.testmod.event;

import com.love.testmod.TestMod;
import com.love.testmod.renderer.TestBlockEntityRender;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import static com.love.testmod.tile.ModBlockEntityTypes.TEST_BLOCK_ENTITY;

@EventBusSubscriber(modid = TestMod.MODID, bus = EventBusSubscriber.Bus.MOD,value = Dist.CLIENT)
public class EventHandler {
    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(TEST_BLOCK_ENTITY.get(), TestBlockEntityRender::new);
    }
}
