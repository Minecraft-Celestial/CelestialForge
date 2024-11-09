package com.xiaoyue.celestial_forge.content.registry;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public record UpgradeRecipe(int exp, List<Item> items) {

	public static final Codec<UpgradeRecipe> CODEC = RecordCodecBuilder.create(i -> i.group(
			Codec.INT.fieldOf("exp").forGetter(UpgradeRecipe::exp),
			ForgeRegistries.ITEMS.getCodec().listOf().fieldOf("items").forGetter(UpgradeRecipe::items)
	).apply(i, UpgradeRecipe::new));

	public static UpgradeRecipe of(int exp, Item... items) {
		return new UpgradeRecipe(exp, List.of(items));
	}

}
