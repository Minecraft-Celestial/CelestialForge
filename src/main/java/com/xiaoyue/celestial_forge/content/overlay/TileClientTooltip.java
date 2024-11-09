package com.xiaoyue.celestial_forge.content.overlay;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public record TileClientTooltip(List<ItemStack> items) implements ClientTooltipComponent {

	public TileClientTooltip(TileTooltip inv) {
		this(inv.items());
	}

	public int getHeight() {
		return 20;
	}

	public int getWidth(Font font) {
		return items.size() * 18 + 2;
	}

	public void renderImage(Font font, int mx, int my, GuiGraphics g) {
		int n = 0;
		for (ItemStack stack : items) {
			int x = mx + n * 18 + 1;
			renderSlot(font, x, my + 1, g, stack);
			n++;
		}
	}

	private void renderSlot(Font font, int x, int y, GuiGraphics g, ItemStack stack) {
		if (!stack.isEmpty()) {
			g.renderItem(stack, x + 1, y + 1, 0);
			g.renderItemDecorations(font, stack, x + 1, y + 1);
		}
	}

}
