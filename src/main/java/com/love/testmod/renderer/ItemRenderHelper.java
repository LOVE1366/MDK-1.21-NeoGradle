package com.love.testmod.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ItemRenderHelper {
    private final ItemRenderer itemRenderer;

    public ItemRenderHelper(ItemRenderer itemRenderer) {
        this.itemRenderer = itemRenderer;
    }

    public void render(ItemStack itemStack, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay, ItemDisplayContext displayContext) {

        BakedModel model = itemRenderer.getModel(itemStack, null, null, 0);

        itemRenderer.render(itemStack, displayContext, false, poseStack, buffer, light, overlay, model);
    }
}
