package com.boatcraft.builder.client;

import com.boatcraft.builder.BoatCraftMod;
import com.boatcraft.builder.item.ToolItems;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRendererRegistry;
import org.lwjgl.glfw.GLFW;

public class BoatCraftClient implements ClientModInitializer {
    private static KeyBinding openBuilder;

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(BoatCraftMod.BUILD_BLOCK, BuildBlockRenderer::new);
        openBuilder = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.boatcraft.open_builder", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, "category.boatcraft"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openBuilder.wasPressed()) {
                if (client.player != null && client.player.getMainHandStack().isOf(ToolItems.BUILDER_TOOL)) {
                    client.setScreen(new BuilderScreen());
                }
            }
        });
    }
}
