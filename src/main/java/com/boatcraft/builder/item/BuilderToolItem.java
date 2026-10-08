package com.boatcraft.builder.item;

import com.boatcraft.builder.client.BuilderScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class BuilderToolItem extends Item {
    public BuilderToolItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        if (world.isClient) {
            MinecraftClient.getInstance().setScreen(new BuilderScreen());
        }
        return ActionResult.SUCCESS;
    }
}
