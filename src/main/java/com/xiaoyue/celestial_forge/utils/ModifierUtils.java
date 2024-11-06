package com.xiaoyue.celestial_forge.utils;

import com.xiaoyue.celestial_forge.content.modifier.ModifierConfig;
import com.xiaoyue.celestial_forge.content.modifier.ModifierHolder;
import com.xiaoyue.celestial_forge.content.modifier.ModifierType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.item.*;

import javax.annotation.Nullable;
import java.util.UUID;

public class ModifierUtils {

	public static final long COMMON_SEGMENT_CURIO = (0x7a6ca76cL) << 32;
	public static final long COMMON_SEGMENT_EQUIPMENT = 0x9225d5c4fd8d434bL;
	public static final String tagName = "itemModifier";
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
	public static ModifierHolder rollModifier(ItemStack stack, RandomSource random) {
		if (!canHaveModifiers(stack)) return null;
		for (var e : ModifierType.values()) {
			if (e.test(stack)) {
				return ModifierConfig.getAll().byType(e).roll(random);
			}
		}
		return null;
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

	public static void setModifier(ItemStack stack, ModifierHolder modifier) {
		CompoundTag tag = stack.getOrCreateTag();
		tag.remove(reRollTagName);
		tag.putString(tagName, modifier.id().toString());
	}

	public static boolean hasModifier(ItemStack stack) {
		CompoundTag tag = stack.getTag();
		return tag != null && tag.contains(tagName);
	}

	@Nullable
	public static ModifierHolder getModifier(ItemStack stack) {
		CompoundTag tag = stack.getTag();
		if (tag == null) return null;
		if (!tag.contains(tagName)) return null;
		return ModifierConfig.getAll().byId(new ResourceLocation(tag.getString(tagName)));
	}

	public static UUID getCurioUuid(String identifier, int slot, int attributeIndex) {
		long second = (((long) attributeIndex) << 32) | ((long) identifier.hashCode());
		long first = COMMON_SEGMENT_CURIO | (((long) slot) << 32);
		return new UUID(first, second);
	}

	public static void applyCurioModifier(LivingEntity entity, ModifierHolder modifier, String slotIdentifier, int index) {
		for (int i = 0; i < modifier.data().modifiers().size(); i++) {
			var entry = modifier.data().modifiers().get(i);
			UUID id = getCurioUuid(slotIdentifier, index, i);
			AttributeInstance instance = entity.getAttribute(entry.attr());
			if (instance != null && instance.getModifier(id) == null) {
				instance.addTransientModifier(entry.getAttributeModifier(id, modifier.id().toString()));
			}
		}
	}

	public static void removeCurioModifier(LivingEntity entity, ModifierHolder modifier, String slotIdentifier, int index) {
		for (int i = 0; i < modifier.data().modifiers().size(); i++) {
			var entry = modifier.data().modifiers().get(i);
			UUID id = getCurioUuid(slotIdentifier, index, i);
			AttributeInstance instance = entity.getAttribute(entry.attr());
			if (instance != null) {
				instance.removeModifier(id);
			}
		}
	}

	public static UUID getEquipmentUuid(EquipmentSlot slot, int attributeIndex) {
		long second = (((long) attributeIndex) << 32) | ((long) slot.hashCode());
		return new UUID(COMMON_SEGMENT_EQUIPMENT, second);
	}

	public static void applyEquipmentModifier(LivingEntity entity, ModifierHolder modifier, EquipmentSlot type) {
		if (modifier.type() == ModifierType.TOOL && type.getType() == EquipmentSlot.Type.ARMOR
				|| modifier.type() == ModifierType.ARMOR && type.getType() == EquipmentSlot.Type.HAND) {
			return;
		}
		for (int i = 0; i < modifier.data().modifiers().size(); i++) {
			var entry = modifier.data().modifiers().get(i);
			UUID id = getEquipmentUuid(type, i);
			AttributeInstance instance = entity.getAttribute(entry.attr());
			if (instance != null && instance.getModifier(id) == null) {
				instance.addTransientModifier(entry.getAttributeModifier(id, modifier.id().toString()));
			}
		}
	}

	public static void removeEquipmentModifier(LivingEntity entity, ModifierHolder modifier, EquipmentSlot type) {
		for (int i = 0; i < modifier.data().modifiers().size(); i++) {
			var entry = modifier.data().modifiers().get(i);
			UUID id = getEquipmentUuid(type, i);
			AttributeInstance instance = entity.getAttribute(entry.attr());
			if (instance != null) {
				instance.removeModifier(id);
			}
		}
	}
}
