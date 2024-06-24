package com.xiaoyue.celestial_forge.utils;

import com.xiaoyue.celestial_forge.content.modifier.Modifier;
import com.xiaoyue.celestial_forge.register.CModifiers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.UUID;

public class ModifierUtils {

    public static final long COMMON_SEGMENT_CURIO = (0x7a6ca76cL) << 32;
    public static final long COMMON_SEGMENT_EQUIPMENT = 0x9225d5c4fd8d434bL;
    public static final String tagName = "itemModifier";
    public static final String rerollTagName = "rollModifier";
    public static final String bookTagName = "bookModifier";

    public static boolean canHaveModifiers(ItemStack stack) {
        return !stack.isEmpty() && stack.getMaxStackSize() <= 1;
    }

    public static Component addModifierTypeTip(Modifier modifier) {
        return switch (modifier.type) {
            case EQUIPPED -> Component.translatable("tooltip.celestial_forge.type.equipped");
            case HELD -> Component.translatable("tooltip.celestial_forge.type.tool");
            case CURIO -> Component.translatable("tooltip.celestial_forge.type.curio");
            case ALL -> Component.translatable("tooltip.celestial_forge.type.all");
        };
    }

    public static AttributeModifier.Operation getOP(String op) {
        if (op.equals("add")) {
            return AttributeModifier.Operation.ADDITION;
        } else if (op.equals("mult_base")) {
            return AttributeModifier.Operation.MULTIPLY_BASE;
        } else if (op.equals("mult_total")) {
            return AttributeModifier.Operation.MULTIPLY_TOTAL;
        }
        return null;
    }

    @Nullable
    public static Modifier rollModifier(ItemStack stack, RandomSource random) {
        if (!canHaveModifiers(stack)) return null;
        if (CModifiers.curioPool.isApplicable.test(stack)) {
            return CModifiers.curioPool.roll(random);
        }
        if (CModifiers.toolPool.isApplicable.test(stack)) {
            return CModifiers.toolPool.roll(random);
        }
        if (CModifiers.bowPool.isApplicable.test(stack)) {
            return CModifiers.bowPool.roll(random);
        }
        if (CModifiers.armorPool.isApplicable.test(stack)) {
            return CModifiers.armorPool.roll(random);
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
        stack.getOrCreateTag().putString(rerollTagName, "default");
    }

    public static boolean isWaitingReroll(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains(rerollTagName);
    }

    public static void setModifier(ItemStack stack, Modifier modifier) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.remove(rerollTagName);
        tag.putString(tagName, modifier.name.toString());
    }

    public static boolean hasModifier(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(tagName);
    }

    @Nullable public static Modifier getModifier(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag == null) return null;
        if (!tag.contains(tagName)) return null;
        return CModifiers.modifiers.get(new ResourceLocation(tag.getString(tagName)));
    }

    public static UUID getCurioUuid(String identifier, int slot, int attributeIndex) {
        long second = (((long) attributeIndex)<<32) | ((long) identifier.hashCode());
        long first = COMMON_SEGMENT_CURIO | (((long) slot) << 32);
        return new UUID(first, second);
    }

    public static void applyCurioModifier(LivingEntity entity, Modifier modifier, String slotIdentifier, int index) {
        for (int i = 0; i < modifier.modifiers.size(); i++) {
            Pair<Attribute, Modifier.AttributeModifierSupplier> entry = modifier.modifiers.get(i);
            UUID id = getCurioUuid(slotIdentifier, index, i);
            AttributeInstance instance = entity.getAttribute(entry.getKey());
            if (instance != null && instance.getModifier(id) == null) {
                instance.addTransientModifier(entry.getValue().getAttributeModifier(id, "curio_modifier_"+modifier.debugName));
            }
        }
    }

    public static void removeCurioModifier(LivingEntity entity, Modifier modifier, String slotIdentifier, int index) {
        for (int i = 0; i < modifier.modifiers.size(); i++) {
            Pair<Attribute, Modifier.AttributeModifierSupplier> entry = modifier.modifiers.get(i);
            UUID id = getCurioUuid(slotIdentifier, index, i);
            AttributeInstance instance = entity.getAttribute(entry.getKey());
            if (instance != null) {
                instance.removeModifier(id);
            }
        }
    }

    public static UUID getEquipmentUuid(EquipmentSlot slot, int attributeIndex) {
        long second = (((long) attributeIndex)<<32) | ((long) slot.hashCode());
        return new UUID(COMMON_SEGMENT_EQUIPMENT, second);
    }

    public static void applyEquipmentModifier(LivingEntity entity, Modifier modifier, EquipmentSlot type) {
        if (modifier.type == Modifier.ModifierType.HELD && type.getType() == EquipmentSlot.Type.ARMOR
                || modifier.type == Modifier.ModifierType.EQUIPPED && type.getType() == EquipmentSlot.Type.HAND) {
            return;
        }
        for (int i = 0; i < modifier.modifiers.size(); i++) {
            Pair<Attribute, Modifier.AttributeModifierSupplier> entry = modifier.modifiers.get(i);
            UUID id = getEquipmentUuid(type, i);
            AttributeInstance instance = entity.getAttribute(entry.getKey());
            if (instance != null && instance.getModifier(id) == null) {
                instance.addTransientModifier(entry.getValue().getAttributeModifier(id, "equipment_modifier_"+modifier.debugName));
            }
        }
    }

    public static void removeEquipmentModifier(LivingEntity entity, Modifier modifier, EquipmentSlot type) {
        for (int i = 0; i < modifier.modifiers.size(); i++) {
            Pair<Attribute, Modifier.AttributeModifierSupplier> entry = modifier.modifiers.get(i);
            UUID id = getEquipmentUuid(type, i);
            AttributeInstance instance = entity.getAttribute(entry.getKey());
            if (instance != null) {
                instance.removeModifier(id);
            }
        }
    }
}
