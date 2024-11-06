package com.xiaoyue.celestial_forge.content.modifier;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

public record ModifierHolder(ModifierType type, ResourceLocation id, ModifierData data) {

	public MutableComponent getFormattedName() {
		return Component.translatable("modifier." + id.getNamespace() + "." + id.getPath()).withStyle(ChatFormatting.AQUA);
	}

	@Nullable
	private static MutableComponent getModifierDescription(ModifierEntry entry) {
		double d0 = entry.amount();
		double d1;
		if (entry.op() == AttributeModifier.Operation.ADDITION) {
			if (entry.attr() == Attributes.KNOCKBACK_RESISTANCE) {
				d1 = d0 * 10.0D;
			} else {
				d1 = d0;
			}
		} else {
			d1 = d0 * 100.0D;
		}

		if (d0 > 0.0D) {
			return Component.translatable("attribute.modifier.plus." + entry.op().toValue(), ATTRIBUTE_MODIFIER_FORMAT.format(d1),
					Component.translatable(entry.attr().getDescriptionId())).withStyle(ChatFormatting.BLUE);
		} else if (d0 < 0.0D) {
			d1 = d1 * -1.0D;
			return Component.translatable("attribute.modifier.take." + entry.op().toValue(), ATTRIBUTE_MODIFIER_FORMAT.format(d1),
					Component.translatable(entry.attr().getDescriptionId())).withStyle(ChatFormatting.RED);
		}
		return null;
	}

	public List<MutableComponent> getInfoLines() {
		List<MutableComponent> lines = new ArrayList<>();
		int size = data.modifiers().size();
		if (size < 1) {
			return lines;
		}
		if (size == 1) {
			MutableComponent description = getModifierDescription(data.modifiers().get(0));
			if (description == null) return lines;
			lines.add(getFormattedName().append(": ").withStyle(ChatFormatting.GRAY).append(description));
		} else {
			lines.add(getFormattedName().append(":").withStyle(ChatFormatting.GRAY));
			for (var entry : data.modifiers()) {
				MutableComponent description = getModifierDescription(entry);
				if (description != null) {
					lines.add(description);
				}
			}
			if (lines.size() == 1) {
				lines.clear();
			}
		}
		return lines;
	}

}
