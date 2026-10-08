package com.boatcraft.builder.client;

import com.boatcraft.builder.entity.BuildBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class BuildBlockRenderer extends EntityRenderer<BuildBlockEntity> {
    public BuildBlockRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.shadowRadius = 0.35f;
    }

    @Override
    public void render(BuildBlockEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider providers, int light) {
        Block block = Registries.BLOCK.get(new Identifier(entity.getBlockId()));
        if (block == null || block == net.minecraft.block.Blocks.AIR) return;
        BlockState state = block.getDefaultState();

        matrices.push();
        matrices.translate(-0.5, -0.5, -0.5);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(entity.getRotX()));
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(entity.getRotY()));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(entity.getRotZ()));
        matrices.scale(entity.getScaleX(), entity.getScaleY(), entity.getScaleZ());

        float alpha = entity.getAlpha();
        if (alpha < 0.999f) {
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, alpha);
            VertexConsumerProvider translucent = layer -> providers.getBuffer(RenderLayer.getTranslucentMovingBlock());
            MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(state, matrices, translucent, light, OverlayTexture.DEFAULT_UV);
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.disableBlend();
        } else {
            MinecraftClient.getInstance().getBlockRenderManager().renderBlockAsEntity(state, matrices, providers, light, OverlayTexture.DEFAULT_UV);
        }

        matrices.pop();
    }

    @Override
    public Identifier getTexture(BuildBlockEntity entity) {
        return new Identifier("minecraft", "textures/atlas/blocks.png");
    }
}
