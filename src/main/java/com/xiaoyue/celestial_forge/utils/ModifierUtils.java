package com.xiaoyue.celestial_forge.utils;

import com.xiaoyue.celestial_forge.content.data.ModifierConfig;
import com.xiaoyue.celestial_forge.content.data.ModifierType;
import com.xiaoyue.celestial_forge.content.modifier.ModifierHolder;
import com.xiaoyue.celestial_forge.content.modifier.ModifierInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.*;

import javax.annotation.Nullable;
import java.util.UUID;

public class ModifierUtils {

	public static final long COMMON_SEGMENT_CURIO = (0x7a6ca76cL) << 32;
	public static final long COMMON_SEGMENT_EQUIPMENT = 0x9225d5c4fd8d434bL;
	public static final String tagName = "itemModifier";
	public static final String levelName = "itemLevel";
	public static final String expName = "itemExp";
	public static final String reRollTagName = "rollModifier";
	public static final String bookTagName = "bookModifier";

	public static boolean canHaveModifiers(ItemStack stack) {
		return !stack.isEmpty() && stack.getMaxStackSize() <= 1;
	}

	public static Component addModifierTypeTip(ModifierHolder modifier) {
		return switch (modifier.type()) {
			case ARMOR -> Component.translatable("celestial_forge.tooltip.type.equipped");
			case TOOL -> Component.translatable("celestial_forge.tooltip.type.tool");
			case RANGED -> Component.translatable("celestial_forge.tooltip.type.ranged");
			case CURIO -> Component.translatable("celestial_forge.tooltip.type.curio");
			case ALL -> Component.translatable("celestial_forge.tooltip.type.all");
		};
	}

	public static boolean isRangedWeapon(ItemStack stack) {
		return stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem;
	}

	public static boolean isTool(ItemStack stack) {
		return stack.getItem() instanceof DiggerItem || stack.getItem() instanceof SwordItem || stack.getItem() instanceof ShieldItem;
	}

	public static boolean isArmor(ItemStack stack) {
		return stack.getItem() instanceof ArmorItem;
	}

	@Nullable
	public static ModifierType getType(ItemStack stack) {
		if (!canHaveModifiers(stack)) return null;
		for (var e : ModifierType.values()) {
			if (e.test(stack)) {
				return e;
			}
		}
		return null;
	}

	@Nullable
	public static ModifierInstance rollModifier(ItemStack stack, RandomSource random) {
		var type = getType(stack);
		if (type == null) return null;
		var ins = ModifierConfig.getAll().byType(type).roll(random);
		return ins == null ? null : new ModifierInstance(ins, 0);
	}

	public static void removeModifier(ItemStack stack) {
		if (stack.hasTag()) {
			stack.getTag().remove(tagName);
		}
	}

	public static void prepareReroll(ItemStack stack) {
		removeModifier(stack);
		stack.getOrCreateTag().putString(reRollTagName, "default");
	}

	public static boolean isWaitingReRoll(ItemStack stack) {
		return stack.hasTag() && stack.getTag().contains(reRollTagName);
	}

	public static void setModifier(ItemStack stack, ModifierInstance modifier) {
		CompoundTag tag = stack.getOrCreateTag();
		tag.remove(reRollTagName);
		tag.putString(tagName, modifier.holder().id().toString());
		//TODO
	}

	public static boolean hasModifier(ItemStack stack) {
		CompoundTag tag = stack.getTag();
		return tag != null && tag.contains(tagName);
	}

	@Nullable
	public static ModifierInstance getModifier(ItemStack stack) {
		if (!canHaveModifiers(stack)) return null;
		CompoundTag tag = stack.getTag();
		if (tag == null) return null;
		if (!tag.contains(tagName)) return null;
		int level = tag.getInt(levelName);
		var ans = ModifierConfig.getAll().byId(new ResourceLocation(tag.getString(tagName)));
		return ans == null ? null : new ModifierInstance(ans, level);
	}

	public static UUID getCurioUuid(String identifier, int slot, int attributeIndex) {
		long second = (((long) attributeIndex) << 32) | ((long) identifier.hashCode());
		long first = COMMON_SEGMENT_CURIO | (((long) slot) << 32);
		return new UUID(first, second);
	}

	public static void applyCurioModifier(LivingEntity entity, ModifierInstance modifier, String slotIdentifier, int index) {
		for (int i = 0; i < modifier.size(); i++) {
			var entry = modifier.get(i);
			UUID id = getCurioUuid(slotIdentifier, index, i);
			AttributeInstance instance = entity.getAttribute(entry.entry().attr());
			if (instance != null && instance.getModifier(id) == null) {
				instance.addTransientModifier(entry.getAttributeModifier(id, modifier.holder().id().toString()));
			}
		}
	}

	public static void removeCurioModifier(LivingEntity entity, ModifierInstance modifier, String slotIdentifier, int index) {
		for (int i = 0; i < modifier.size(); i++) {
			var entry = modifier.get(i);
			UUID id = getCurioUuid(slotIdentifier, index, i);
			AttributeInstance instance = entity.getAttribute(entry.entry().attr());
			if (instance != null) {
				instance.removeModifier(id);
			}
		}
	}

}
