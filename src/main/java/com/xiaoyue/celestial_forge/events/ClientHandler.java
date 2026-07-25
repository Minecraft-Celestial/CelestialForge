package com.xiaoyue.celestial_forge.events;

import com.xiaoyue.celestial_forge.content.modifier.ModifierInstance;
import com.xiaoyue.celestial_forge.data.CFModConfig;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientHandler {

	@SubscribeEvent
	public static void addTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		ModifierInstance modifier = ModifierUtils.getModifier(stack);
		if (modifier == null) return;
		event.getToolTip().addAll(modifier.getInfoLines());
		if (CFModConfig.SERVER.enableModifierUpgrades.get()) {
            event.getToolTip().addAll(modifier.extraLines());
        }
	}

}
