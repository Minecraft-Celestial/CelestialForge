package com.xiaoyue.celestial_forge.content.modifier;

import com.xiaoyue.celestial_forge.content.data.ModifierEntry;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public record ModifierInstanceEntry(ModifierEntry entry, int level) {

	public double getAmount() {
		return entry.getAmount(level);
	}

	public AttributeModifier getAttributeModifier(UUID id, String name) {
		return entry.getAttributeModifier(id, name, level);
	}
}
