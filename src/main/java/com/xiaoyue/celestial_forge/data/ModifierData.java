package com.xiaoyue.celestial_forge.data;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public record ModifierData(String name, String type, int weight, Attribute[] attributes, Double[] amounts, AttributeModifier.Operation[] ops) {

}
