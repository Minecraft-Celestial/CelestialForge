package com.xiaoyue.celestial_forge.content.modifier;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.UUID;

public record ModifierEntry(Attribute attr, double amount, AttributeModifier.Operation op) {

	public AttributeModifier getAttributeModifier(UUID id, String name) {
		return new AttributeModifier(id, name, amount, op);
	}

}
