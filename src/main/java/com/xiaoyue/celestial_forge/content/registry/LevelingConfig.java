package com.xiaoyue.celestial_forge.content.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.xiaoyue.celestial_forge.register.CFItems;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;

public record LevelingConfig(int baseCost, double expCost, ResourceLocation upgrades) {

	public static final Codec<LevelingConfig> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.INT.fieldOf("baseCost").forGetter(LevelingConfig::baseCost),
			Codec.DOUBLE.fieldOf("expCost").forGetter(LevelingConfig::expCost),
			CodecHelper.RL.fieldOf("upgrades").forGetter(LevelingConfig::upgrades)
	).apply(i, LevelingConfig::new));

	public static final Codec<Holder<LevelingConfig>> HOLDER = CodecHelper.lazyCodec(CFItems.LEVELING);

}
