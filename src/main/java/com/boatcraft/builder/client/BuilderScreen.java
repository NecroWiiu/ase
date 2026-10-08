package com.boatcraft.builder.client;

import com.boatcraft.builder.network.Networking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.block.Block;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class BuilderScreen extends Screen {
    private static final List<String> BLOCKS = List.of(
            "minecraft:oak_planks", "minecraft:spruce_planks", "minecraft:birch_planks",
            "minecraft:stone", "minecraft:cobblestone", "minecraft:smooth_stone",
            "minecraft:bricks", "minecraft:glass", "minecraft:iron_block",
            "minecraft:gold_block", "minecraft:quartz_block", "minecraft:purpur_block",
            "minecraft:obsidian", "minecraft:slime_block", "minecraft:honey_block",
            "minecraft:terracotta", "minecraft:white_concrete", "minecraft:black_concrete"
    );

    public BuilderScreen() {
        super(Text.translatable("screen.boatcraft.builder.title"));
    }

    @Override
    protected void init() {
        int panelW = 260;
        int left = (width - panelW) / 2;
        int top = (height - 180) / 2;
        int cols = 6;
        for (int i = 0; i < BLOCKS.size(); i++) {
            int col = i % cols;
            int row = i / cols;
            String id = BLOCKS.get(i);
            addDrawableChild(ButtonWidget.builder(Text.literal(""), b -> select(id))
                    .dimensions(left + 8 + col * 41, top + 38 + row * 40, 36, 34)
                    .build());
        }
        addDrawableChild(ButtonWidget.builder(Text.literal("×"), b -> close())
                .dimensions(left + panelW - 30, top + 8, 22, 18).build());
    }

    private void select(String blockId) {
        var buf = PacketByteBufs.create();
        buf.writeString(blockId);
        ClientPlayNetworking.send(Networking.SELECT_BLOCK, buf);
        close();
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        int panelW = 260;
        int panelH = 180;
        int left = (width - panelW) / 2;
        int top = (height - panelH) / 2;

        ctx.fill(0, 0, width, height, 0x55000000);
        ctx.fill(left, top, left + panelW, top + panelH, 0xFF17191C);
        ctx.fill(left, top, left + panelW, top + 2, 0xFFE3E7EA);
        ctx.drawText(textRenderer, title, left + 10, top + 9, 0xFFE3E7EA, false);
        ctx.drawText(textRenderer, Text.translatable("screen.boatcraft.hint"), left + 10, top + 25, 0xFF7F8A92, false);

        super.render(ctx, mouseX, mouseY, delta);

        int cols = 6;
        for (int i = 0; i < BLOCKS.size(); i++) {
            int col = i % cols;
            int row = i / cols;
            Block block = Registries.BLOCK.get(new Identifier(BLOCKS.get(i)));
            if (block != null && block != net.minecraft.block.Blocks.AIR) {
                ctx.drawItem(new ItemStack(block.asItem()), left + 17 + col * 41, top + 47 + row * 40);
            }
        }
    }

    @Override
    public boolean shouldPause() { return false; }
}
