package com.xiaoyue.celestial_forge.content.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.UUID;

public record ModifierEntry(Attribute attr, double base, double perLevel, AttributeModifier.Operation op) {

	public static final Codec<AttributeModifier.Operation> OP_CODEC =
			CodecHelper.enumCodec(AttributeModifier.Operation.class, AttributeModifier.Operation.values());

	public static final Codec<ModifierEntry> CODEC = RecordCodecBuilder.create(i -> i.group(
			ForgeRegistries.ATTRIBUTES.getCodec().fieldOf("attr").forGetter(ModifierEntry::attr),
			Codec.DOUBLE.fieldOf("base").forGetter(ModifierEntry::base),
			Codec.DOUBLE.fieldOf("perLevel").forGetter(ModifierEntry::perLevel),
			OP_CODEC.fieldOf("op").forGetter(ModifierEntry::op)
	).apply(i, ModifierEntry::new));

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
