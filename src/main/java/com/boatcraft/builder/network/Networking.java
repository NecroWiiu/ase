package com.boatcraft.builder.network;

import com.boatcraft.builder.BoatCraftMod;
import com.boatcraft.builder.item.ToolItems;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public final class Networking {
    public static final Identifier SELECT_BLOCK = BoatCraftMod.id("select_block");

    public static void registerServer() {
        ServerPlayNetworking.registerGlobalReceiver(SELECT_BLOCK, (server, player, handler, buf, responseSender) -> {
            String blockId = buf.readString(128);
            server.execute(() -> {
                ItemStack stack = player.getMainHandStack();
                if (stack.isEmpty()) stack = player.getOffHandStack();
                if (stack.isOf(ToolItems.BUILDER_TOOL)) {
                    stack.getOrCreateNbt().putString("SelectedBlock", blockId);
                }
            });
        });
    }
}
