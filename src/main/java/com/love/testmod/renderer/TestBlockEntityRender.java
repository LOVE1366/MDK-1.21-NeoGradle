package com.love.testmod.renderer;

import com.love.testmod.tile.TestBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class TestBlockEntityRender implements BlockEntityRenderer<TestBlockEntity> {

    private final ItemRenderer itemRenderer;

    public TestBlockEntityRender(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(@NotNull TestBlockEntity blockEntity, float partialTicks, PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int light,
                       int packedOverlay) {

        ItemStack stack = new ItemStack(Items.BOW);
        Level level = blockEntity.getLevel();
        BlockPos pos = blockEntity.getBlockPos().above();
        Vector3f targetPos = TestBlockEntity.getPos().toVector3f();
        Vector3f direction = new Vector3f(
                targetPos.x - pos.getX() - 0.5f,
                targetPos.y - pos.getY() + 0.5f,
                targetPos.z - pos.getZ() - 0.5f
        );
        Quaternionf rot = lookAt(direction, new Quaternionf().rotateXYZ((float) Math.PI / 4, (float) Math.PI / 2, 0));
        int blockLight = level.getBrightness(LightLayer.BLOCK, pos);
        int skyLight = level.getBrightness(LightLayer.SKY, pos);
        int packedLight = LightTexture.pack(blockLight, skyLight);

        ModelResourceLocation modelId = new ModelResourceLocation(ResourceLocation.parse("minecraft:bow_pulling_1"), "inventory");

        poseStack.pushPose();

        poseStack.translate(0.5d, 1.5d, 0.5d);
        poseStack.mulPose(rot);
        poseStack.scale(1, 1, 1);

        itemRenderer.render(stack, ItemDisplayContext.GROUND, false, poseStack, bufferSource, packedLight, packedOverlay, Minecraft.getInstance().getModelManager().getModel(modelId));;

        poseStack.popPose();
    }

    public static BakedModel getBowModel(float pull) {
        ItemStack bowStack = new ItemStack(Items.BOW);

        CompoundTag tag = new CompoundTag();
        tag.putFloat("pull", pull);
        tag.putBoolean("pulling", pull > 0);
        bowStack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));

        BakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelShaper().getItemModel(bowStack);

        return model.getOverrides().resolve(model, bowStack, Minecraft.getInstance().level, Minecraft.getInstance().player, 0);
    }

    public static Quaternionf lookAt(Vector3f direction, Quaternionf mul) {
        Vector3f forward = new Vector3f(direction).normalize();
        Vector3f right = new Vector3f();
        Vector3f up = new Vector3f(0, 1, 0);
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

        return matrix.getNormalizedRotation(new Quaternionf()).mul(mul);
    }
}
