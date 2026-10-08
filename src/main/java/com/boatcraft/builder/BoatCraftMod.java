package com.boatcraft.builder;

import com.boatcraft.builder.entity.BuildBlockEntity;
import com.boatcraft.builder.item.ToolItems;
import com.boatcraft.builder.network.Networking;
import com.boatcraft.builder.util.BuildWorld;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;

public class BoatCraftMod implements ModInitializer {
    public static final String MOD_ID = "boatcraft";

    public static final EntityType<BuildBlockEntity> BUILD_BLOCK = Registry.register(
            Registries.ENTITY_TYPE,
            id("build_block"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, BuildBlockEntity::new)
                    .dimensions(EntityDimensions.fixed(1.0f, 1.0f))
                    .trackRangeBlocks(64)
                    .trackedUpdateRate(1)
                    .build()
    );

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        ToolItems.register();
        Networking.registerServer();

        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            if (!player.getStackInHand(hand).isOf(ToolItems.BUILDER_TOOL)) return ActionResult.PASS;
            if (!world.isClient) {
                BuildWorld.placeFromHeldTool(player, hit.getBlockPos().offset(hit.getSide()), player.getStackInHand(hand));
            }
            return ActionResult.SUCCESS;
        });

        UseEntityCallback.EVENT.register((player, world, hand, entity, hit) -> {
            if (!(entity instanceof BuildBlockEntity block)) return ActionResult.PASS;
            if (world.isClient) return ActionResult.SUCCESS;
            return BuildWorld.useToolOnBuildBlock(player, player.getStackInHand(hand), block);
        });

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hit) -> {
            if (!(entity instanceof BuildBlockEntity block)) return ActionResult.PASS;
            if (world.isClient) return ActionResult.SUCCESS;
            return BuildWorld.attackToolOnBuildBlock(player, player.getStackInHand(hand), block);
        });
    }
}
