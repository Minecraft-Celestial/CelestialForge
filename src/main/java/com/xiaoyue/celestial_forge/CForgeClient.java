package com.xiaoyue.celestial_forge;

import com.xiaoyue.celestial_forge.content.overlay.TileClientTooltip;
import com.xiaoyue.celestial_forge.content.overlay.TileInfoDisplay;
import com.xiaoyue.celestial_forge.content.overlay.TileTooltip;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.xiaoyue.celestial_forge.CelestialForge.MODID;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = MODID, value = Dist.CLIENT)
public class CForgeClient {

	@SubscribeEvent
	public static void registerOverlay(RegisterGuiOverlaysEvent event) {
		event.registerAbove(VanillaGuiOverlay.CROSSHAIR.id(), "info_tile", new TileInfoDisplay());
	}

	@SubscribeEvent
	public static void registerClientTooltip(RegisterClientTooltipComponentFactoriesEvent event) {
		event.register(TileTooltip.class, TileClientTooltip::new);
	}

}
