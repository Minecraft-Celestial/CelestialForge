package com.xiaoyue.celestial_forge.content.data;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public record ModifierEntry(Holder<Attribute> attr, double base, double perLevel, AttributeModifier.Operation op) {

	public ModifierEntry(Holder<Attribute> attr, double base, AttributeModifier.Operation op) {
		this(attr, base, 0.04, op);
	}

	public AttributeModifier getAttributeModifier(String name, int level) {
		return new AttributeModifier(ResourceLocation.parse(name), getAmount(level), op);
	}

	public double getAmount(int level) {
		return base * (1 + level * perLevel);
	}

}
