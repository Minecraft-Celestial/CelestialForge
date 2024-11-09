package com.xiaoyue.celestial_forge.content.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;

import java.util.List;

public record ModifierData(
		ModifierType type,
		int weight,
		List<ModifierEntry> modifiers,
		Holder<LevelingConfig> leveling
) {

	public static final Codec<ModifierType> TYPE_CODEC = CodecHelper.enumCodec(ModifierType.class, ModifierType.values());

	public static final Codec<ModifierData> CODEC = RecordCodecBuilder.create(i -> i.group(
			TYPE_CODEC.fieldOf("type").forGetter(ModifierData::type),
			Codec.INT.fieldOf("weight").forGetter(ModifierData::weight),
			ModifierEntry.CODEC.listOf().fieldOf("modifiers").forGetter(ModifierData::modifiers),
			LevelingConfig.HOLDER.fieldOf("leveling").forGetter(ModifierData::leveling)
	).apply(i, ModifierData::new));

}
