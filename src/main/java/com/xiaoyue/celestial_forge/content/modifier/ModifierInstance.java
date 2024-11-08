package com.xiaoyue.celestial_forge.content.modifier;

import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.item.ItemStack.ATTRIBUTE_MODIFIER_FORMAT;

public record ModifierInstance(ModifierHolder holder, int level, int exp) {

	public static ModifierInstance of(ModifierHolder ins) {
		return new ModifierInstance(ins, 0, 0);
	}

	@Nullable
	private static MutableComponent getModifierDescription(ModifierInstanceEntry entry) {
		double d0 = entry.getAmount();
		double d1;
		if (entry.entry().op() == AttributeModifier.Operation.ADDITION) {
			if (entry.entry().attr() == Attributes.KNOCKBACK_RESISTANCE) {
				d1 = d0 * 10.0D;
			} else {
				d1 = d0;
			}
		} else {
			d1 = d0 * 100.0D;
		}

		if (d0 > 0.0D) {
			return Component.translatable("attribute.modifier.plus." + entry.entry().op().toValue(), ATTRIBUTE_MODIFIER_FORMAT.format(d1),
					Component.translatable(entry.entry().attr().getDescriptionId())).withStyle(ChatFormatting.BLUE);
		} else if (d0 < 0.0D) {
			d1 = d1 * -1.0D;
			return Component.translatable("attribute.modifier.take." + entry.entry().op().toValue(), ATTRIBUTE_MODIFIER_FORMAT.format(d1),
					Component.translatable(entry.entry().attr().getDescriptionId())).withStyle(ChatFormatting.RED);
		}
		return null;
	}

	public List<MutableComponent> getInfoLines() {
		List<MutableComponent> lines = new ArrayList<>();
		int size = holder.data().modifiers().size();
		if (size < 1) {
			return lines;
		}
		if (size == 1) {
			MutableComponent description = getModifierDescription(new ModifierInstanceEntry(holder.data().modifiers().get(0), level));
			if (description == null) return lines;
			lines.add(holder.getFormattedName().append(": ").withStyle(ChatFormatting.GRAY).append(description));
		} else {
			lines.add(holder.getFormattedName().append(":").withStyle(ChatFormatting.GRAY));
			for (var entry : holder.data().modifiers()) {
				MutableComponent description = getModifierDescription(new ModifierInstanceEntry(entry, level));
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

	public int size() {
		return holder.data().modifiers().size();
	}

	public ModifierInstanceEntry get(int i) {
		return new ModifierInstanceEntry(holder.data().modifiers().get(i), level);
	}

	public boolean canUpgrade() {
		return level * 10 < holder.gate().list().size();
	}

	public ModifierInstance addExp(int toAdd) {
		int lv = level;
		int xp = toAdd + exp;
		int maxLv = (lv > 0 && lv % 10 == 0) ? lv : lv / 10 * 10 + 10;
		while (true) {
			if (lv >= maxLv) break;
			int next = ModifierUtils.getMaxExp(lv);
			if (xp < next) break;
			lv++;
			xp -= next;
		}
		return new ModifierInstance(holder, lv, xp);
	}

}
