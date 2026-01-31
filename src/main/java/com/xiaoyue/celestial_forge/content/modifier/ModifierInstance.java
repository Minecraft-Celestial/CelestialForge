package com.xiaoyue.celestial_forge.content.modifier;

import com.xiaoyue.celestial_forge.content.data.UpgradeRecipe;
import com.xiaoyue.celestial_forge.data.CFLang;
import com.xiaoyue.celestial_forge.data.CFModConfig;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import dev.xkmc.l2damagetracker.contents.curios.AttrTooltip;
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
		int op = entry.entry().op().toValue();
		double d0 = entry.getAmount();
		double d1;
		if (entry.entry().op() == AttributeModifier.Operation.ADDITION) {
			if (entry.entry().attr() == Attributes.KNOCKBACK_RESISTANCE) {
				d1 = d0 * 10.0D;
			} else if (AttrTooltip.isMult(entry.entry().attr())) {
				d1 = d0 * 100.0D;
				op = AttributeModifier.Operation.MULTIPLY_BASE.toValue();
			} else {
				d1 = d0;
			}

		} else {
			d1 = d0 * 100.0D;
		}

		if (d0 > 0.0D) {
			return Component.translatable("attribute.modifier.plus." + op, ATTRIBUTE_MODIFIER_FORMAT.format(d1),
					Component.translatable(entry.entry().attr().getDescriptionId())).withStyle(ChatFormatting.BLUE);
		} else if (d0 < 0.0D) {
			d1 = d1 * -1.0D;
			return Component.translatable("attribute.modifier.take." + op, ATTRIBUTE_MODIFIER_FORMAT.format(d1),
					Component.translatable(entry.entry().attr().getDescriptionId())).withStyle(ChatFormatting.RED);
		}
		return null;
	}

	public List<MutableComponent> extraLines() {
		List<MutableComponent> lines = new ArrayList<>();
		lines.add(CFLang.MODIFIER_LEVEL.get(Component.literal("" + level).withStyle(ChatFormatting.BLUE))
				.withStyle(ChatFormatting.GRAY));
		if (canUpgrade()) {
			if (needUpgrade()) {
				lines.add(CFLang.NEED_UPGRADE.get().withStyle(ChatFormatting.YELLOW));
			} else {
				lines.add(CFLang.GRADE_PROGRESS.get(
						Component.literal("" + exp).withStyle(ChatFormatting.BLUE),
						Component.literal("" + ModifierUtils.getMaxExp(level)).withStyle(ChatFormatting.BLUE)
				).withStyle(ChatFormatting.GRAY));
			}
		} else {
			lines.add(CFLang.IS_MAX_LEVEL.get().withStyle(ChatFormatting.GOLD));
		}
		return lines;
	}

	public List<MutableComponent> getInfoLines() {
		List<MutableComponent> lines = new ArrayList<>();
		lines.add(Component.empty());
		int size = holder.data().modifiers().size();
		if (size < 1) {
			return lines;
		}
		if (size == 1) {
			MutableComponent description = getModifierDescription(new ModifierInstanceEntry(holder.data().modifiers().get(0), level));
			if (description == null) return lines;
			lines.add(CFLang.CELESTIAL_MODIFIER.get(holder.getFormattedName().withStyle(ChatFormatting.AQUA)).withStyle(ChatFormatting.GRAY));
			lines.add(description);
		} else {
			lines.add(CFLang.CELESTIAL_MODIFIER.get(holder.getFormattedName().withStyle(ChatFormatting.AQUA)).withStyle(ChatFormatting.GRAY));
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
		return level < holder.gate().upgrades().size() * 10 && CFModConfig.COMMON.enableModifierUpgrades.get();
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

	public boolean needUpgrade() {
		return canUpgrade() && level > 0 && level % 10 == 0 && exp >= ModifierUtils.getMaxExp(level);
	}

	public UpgradeRecipe getNextUpgrade() {
		return holder.gate().upgrades().get(level);
	}

	public ModifierInstance upgrade() {
		if (needUpgrade()) {
			return new ModifierInstance(holder, level + 1, exp - ModifierUtils.getMaxExp(level));
		}
		return this;
	}
}
