package com.xiaoyue.celestial_forge.content.data;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public record ModifierEntry(Attribute attr, double base, double perLevel, AttributeModifier.Operation op) {

	public ModifierEntry(Attribute attr, double base, AttributeModifier.Operation op) {
		this(attr, base, 0.04, op);
	}

	public AttributeModifier getAttributeModifier(UUID id, String name, int level) {
		return new AttributeModifier(id, name, getAmount(level), op);
	}

	public double getAmount(int level) {
		return base * (1 + level * perLevel);
	}
}
