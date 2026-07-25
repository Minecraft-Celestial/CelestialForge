package com.xiaoyue.celestial_forge.content.overlay;

import dev.xkmc.l2itemselector.overlay.OverlayUtil;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.phys.BlockHitResult;

import java.util.ArrayList;
import java.util.List;

public class TileInfoDisplay implements LayeredDraw.Layer {

	@Override
	public void render(GuiGraphics g, DeltaTracker tracker) {
		ClientLevel level = Minecraft.getInstance().level;
		if (level == null || Minecraft.getInstance().screen != null) return;
		var hit = Minecraft.getInstance().hitResult;
		if (!(hit instanceof BlockHitResult bhit)) return;
		if (!(level.getBlockEntity(bhit.getBlockPos()) instanceof InfoTile tile)) return;
		new ImageBox(g, (int) (g.guiWidth() * 0.55), (int) (g.guiHeight() * 0.5), (int) (g.guiWidth() * 0.25))
				.render(tile);
	}

	public static class ImageBox extends OverlayUtil {

		public ImageBox(GuiGraphics g, int x0, int y0, int maxW) {
			super(g, x0, y0, maxW);
		}

		public void render(InfoTile tile) {
			var font = Minecraft.getInstance().font;
			List<ClientTooltipComponent> tooltip = new ArrayList<>(tile.lines().stream()
					.flatMap((text) -> font.split(text, this.maxW).stream())
					.map(ClientTooltipComponent::create).toList());
			var img = tile.getImage();
			if (img != null) tooltip.add(new TileClientTooltip(img));
			if (tooltip.isEmpty()) return;
			renderTooltipInternal(font, tooltip);
		}
	}
}
