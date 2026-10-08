package com.boatcraft.builder.util;

import com.boatcraft.builder.BoatCraftMod;
import com.boatcraft.builder.entity.BuildBlockEntity;
import com.boatcraft.builder.item.ToolItems;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public final class BuildWorld {
    private static final float SCALE_STEP = 1.0f / 16.0f;

    public static void placeFromHeldTool(PlayerEntity player, net.minecraft.util.math.BlockPos pos, ItemStack tool) {
        if (!player.getWorld().canPlayerModifyAt(player, pos)) return;
        String blockId = tool.getOrCreateNbt().getString("SelectedBlock");
        if (blockId == null || blockId.isBlank()) blockId = "minecraft:oak_planks";
        Block block = Registries.BLOCK.get(new Identifier(blockId));
        if (block == null || block == net.minecraft.block.Blocks.AIR) return;

        BuildBlockEntity entity = new BuildBlockEntity(BoatCraftMod.BUILD_BLOCK, player.getWorld());
        entity.setPosition(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
        entity.setBlockId(Registries.BLOCK.getId(block).toString());
        entity.setScale(1.0f, 1.0f, 1.0f);
        entity.setRotation(0, 0, 0);
        entity.setAnchored(true);
        entity.setAlpha(1.0f);
        player.getWorld().spawnEntity(entity);
    }

    public static ActionResult useToolOnBuildBlock(PlayerEntity player, ItemStack stack, BuildBlockEntity block) {
        if (stack.isOf(ToolItems.ERASE_TOOL)) {
            block.discard();
            return ActionResult.SUCCESS;
        }
        if (stack.isOf(ToolItems.ANCHOR_TOOL)) {
            block.setAnchored(!block.isAnchored());
            return ActionResult.SUCCESS;
        }
        if (stack.isOf(ToolItems.PHYSICS_TOOL)) {
            block.setAnchored(false);
            Vec3d look = player.getRotationVec(1.0f).normalize();
            block.setVelocity(look.multiply(0.15).add(0, 0.18, 0));
            return ActionResult.SUCCESS;
        }
        if (stack.isOf(ToolItems.SCALE_TOOL)) {
            if (player.isSneaking()) block.cycleScaleAxis();
            else block.adjustScale(block.getScaleAxis(), SCALE_STEP);
            return ActionResult.SUCCESS;
        }
        if (stack.isOf(ToolItems.TROWEL_TOOL)) {
            if (player.isSneaking()) block.cycleRotationAxis();
            else block.adjustRotation(block.getRotAxis(), 22.5f);
            return ActionResult.SUCCESS;
        }
        if (stack.isOf(ToolItems.TRANSPARENCY_TOOL)) {
            block.setAlpha(block.getAlpha() - 0.10f);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }

    public static ActionResult attackToolOnBuildBlock(PlayerEntity player, ItemStack stack, BuildBlockEntity block) {
        if (stack.isOf(ToolItems.SCALE_TOOL)) {
            if (player.isSneaking()) block.cycleScaleAxis();
            else block.adjustScale(block.getScaleAxis(), -SCALE_STEP);
            return ActionResult.SUCCESS;
        }
        if (stack.isOf(ToolItems.TROWEL_TOOL)) {
            if (!player.isSneaking()) block.adjustRotation(block.getRotAxis(), -22.5f);
            return ActionResult.SUCCESS;
        }
        if (stack.isOf(ToolItems.TRANSPARENCY_TOOL)) {
            block.setAlpha(block.getAlpha() + 0.10f);
            return ActionResult.SUCCESS;
        }
        if (stack.isOf(ToolItems.PHYSICS_TOOL)) {
            block.setAnchored(true);
            block.setVelocity(Vec3d.ZERO);
            return ActionResult.SUCCESS;
        }
        return ActionResult.PASS;
    }
}
