package com.xiaoyue.celestial_forge.content.reinforce;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public record AttributeEntry(Holder<Attribute> attr, double val, AttributeModifier.Operation operation) {
}
