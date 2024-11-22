package com.xiaoyue.celestial_forge.events;

import com.xiaoyue.celestial_forge.content.modifier.ModifierInstance;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientHandler {

	@SubscribeEvent
	public static void addTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		ModifierInstance modifier = ModifierUtils.getModifier(stack);
		if (modifier == null) return;
		event.getToolTip().addAll(modifier.getInfoLines());
	}

}
