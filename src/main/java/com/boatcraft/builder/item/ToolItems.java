package com.boatcraft.builder.item;

import com.boatcraft.builder.BoatCraftMod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public final class ToolItems {
    public static final Item BUILDER_TOOL = register("builder_tool", new BuilderToolItem(new FabricItemSettings().maxCount(1)));
    public static final Item ERASE_TOOL = register("erase_tool", new Item(new FabricItemSettings().maxCount(1)));
    public static final Item SCALE_TOOL = register("scale_tool", new Item(new FabricItemSettings().maxCount(1)));
    public static final Item TROWEL_TOOL = register("trowel_tool", new Item(new FabricItemSettings().maxCount(1)));
    public static final Item ANCHOR_TOOL = register("anchor_tool", new Item(new FabricItemSettings().maxCount(1)));
    public static final Item TRANSPARENCY_TOOL = register("transparency_tool", new Item(new FabricItemSettings().maxCount(1)));
    public static final Item PHYSICS_TOOL = register("physics_tool", new Item(new FabricItemSettings().maxCount(1)));

    public static final ItemGroup GROUP = Registry.register(
            Registries.ITEM_GROUP,
            BoatCraftMod.id("tools"),
            FabricItemGroup.builder()
                    .displayName(Text.literal("BoatCraft"))
                    .icon(() -> new ItemStack(BUILDER_TOOL))
                    .entries((context, entries) -> {
                        entries.add(BUILDER_TOOL);
                        entries.add(ERASE_TOOL);
                        entries.add(SCALE_TOOL);
                        entries.add(TROWEL_TOOL);
                        entries.add(ANCHOR_TOOL);
                        entries.add(TRANSPARENCY_TOOL);
                        entries.add(PHYSICS_TOOL);
                    })
                    .build()
    );

    private static Item register(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(BoatCraftMod.MOD_ID, name), item);
    }

    public static void register() {
    }
}
