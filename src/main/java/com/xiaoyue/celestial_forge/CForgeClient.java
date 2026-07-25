package com.xiaoyue.celestial_forge;

import com.xiaoyue.celestial_forge.content.overlay.TileClientTooltip;
import com.xiaoyue.celestial_forge.content.overlay.TileInfoDisplay;
import com.xiaoyue.celestial_forge.content.overlay.TileTooltip;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

import static com.xiaoyue.celestial_forge.CelestialForge.MODID;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class CForgeClient {

	@SubscribeEvent
	public static void registerOverlay(RegisterGuiLayersEvent event) {
		event.registerAbove(VanillaGuiLayers.CROSSHAIR, CelestialForge.loc("info_tile"), new TileInfoDisplay());
	}

	@SubscribeEvent
	public static void registerClientTooltip(RegisterClientTooltipComponentFactoriesEvent event) {
		event.register(TileTooltip.class, TileClientTooltip::new);
	}

}
