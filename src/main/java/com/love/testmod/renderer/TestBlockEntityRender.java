package com.love.testmod.renderer;

import com.love.testmod.tile.TestBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class TestBlockEntityRender implements BlockEntityRenderer<TestBlockEntity> {
    private final ItemRenderHelper itemRenderHelper;

    public TestBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemRenderHelper = new ItemRenderHelper(context.getItemRenderer());
    }

    @Override
    public void render(@NotNull TestBlockEntity blockEntity, float partialTicks, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int light,
                       int packedOverlay) {

        ItemStack bowStack = new ItemStack(Items.BOW);
        Level level = blockEntity.getLevel();
        BlockPos pos = blockEntity.getBlockPos().above();

        Vector3f targetPos = TestBlockEntity.getPos().toVector3f();

        Vector3f direction = new Vector3f(
                (float) (targetPos.x - pos.getX()),
                (float) (targetPos.y - pos.getY()),
                (float) (targetPos.z - pos.getZ())
        );

        Quaternionf rot = lookAt(direction, new Vector3f(0, 1, 0));

        poseStack.pushPose();

        poseStack.translate(0.5d, 1.5d, 0.5d);
        poseStack.mulPose(rot);
        poseStack.scale(1, 1, 1);

        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight = level.getBrightness(LightLayer.SKY, pos);
        int packedLight = LightTexture.pack(blockLight, skyLight);

        itemRenderHelper.render(bowStack, poseStack, bufferSource, packedLight, packedOverlay, ItemDisplayContext.GROUND);

        poseStack.popPose();
    }

    public static Quaternionf lookAt(Vector3f direction, Vector3f up) {
        Vector3f forward = new Vector3f(direction).normalize();
        Vector3f right = new Vector3f();
        up.normalize().cross(forward, right);

        if (right.lengthSquared() < 1e-6f) {
            // 处理垂直情况
            right = new Vector3f(1, 0, 0);
            right.cross(forward, up);
            up.normalize();
        } else {
            right.normalize();
            forward.cross(right, up);
        }

        Matrix4f matrix = new Matrix4f();
        matrix.set(
                right.x, right.y, right.z, 0,
                up.x, up.y, up.z, 0,
                forward.x, forward.y, forward.z, 0,
                0, 0, 0, 1
        );

        return matrix.getNormalizedRotation(new Quaternionf());
    }
}
