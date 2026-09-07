package com.xiaoyue.celestial_forge.events;

import com.xiaoyue.celestial_forge.content.data.DataReinforce;
import com.xiaoyue.celestial_forge.content.data.ModifierType;
import com.xiaoyue.celestial_forge.content.reinforce.AttributeEntry;
import com.xiaoyue.celestial_forge.register.CFFlags;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import com.xiaoyue.celestial_forge.utils.TypeTestUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import top.theillusivec4.curios.api.event.CurioAttributeModifierEvent;

public class CuriosHandler {

	@SubscribeEvent
	public static void onCuriosAttribute(CurioAttributeModifierEvent event) {
		ItemStack stack = event.getItemStack();
		CFFlags.DATA_MAP.values().forEach(data -> {
			if (data instanceof DataReinforce attrData) {
				ModifierType type = TypeTestUtils.getType(stack);
				if (type == null) return;
				if (attrData.hasFlag(stack) && type == ModifierType.CURIO) {
					for (int i = 0; i < attrData.attrs().size(); i++) {
						AttributeEntry entry = attrData.attrs().get(i);
                        AttributeModifier modifier = new AttributeModifier(ResourceLocation.parse(attrData.flag()), entry.val(), entry.operation());
						event.addModifier(entry.attr(), modifier);
					}
				}
			}
		});
		var mod = ModifierUtils.getModifier(stack);
		if (mod == null) return;
		var ctx = event.getSlotContext();
		if (TypeTestUtils.getType(stack) == ModifierType.CURIO) {
			for (int i = 0; i < mod.size(); i++) {
				var entry = mod.get(i);
				String name = ctx.identifier() + "_" + ctx.index() + "_" + mod.holder().id() + "_" + i;
				event.addModifier(entry.entry().attr(), entry.getAttributeModifier(name));
			}
		}
	}

}
