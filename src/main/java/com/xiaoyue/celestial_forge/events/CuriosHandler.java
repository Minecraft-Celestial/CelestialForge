package com.xiaoyue.celestial_forge.events;

import com.xiaoyue.celestial_forge.content.data.ModifierType;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import com.xiaoyue.celestial_forge.utils.TypeTestUtils;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.event.CurioAttributeModifierEvent;

import java.util.UUID;

public class CuriosHandler {

	@SubscribeEvent
	public static void onCuriosAttribute(CurioAttributeModifierEvent event) {
		ItemStack stack = event.getItemStack();
		var mod = ModifierUtils.getModifier(stack);
		if (mod == null) return;
		var ctx = event.getSlotContext();
		if (TypeTestUtils.getType(stack) == ModifierType.CURIO) {
			for (int i = 0; i < mod.size(); i++) {
				var entry = mod.get(i);
				String name = ctx.identifier() + "/" + ctx.index() + "/" + mod.holder().id() + "/" + i;
				UUID id = MathHelper.getUUIDFromString(name);
				event.addModifier(entry.entry().attr(), entry.getAttributeModifier(id, name));
			}
		}
	}

}
