package com.xiaoyue.celestial_forge.content.builder;

import com.xiaoyue.celestial_forge.content.data.UpgradeRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.stream.Stream;

public class UpgradeRecipeBuilder {

	public int exp;
	public ArrayList<Ingredient> items;

	public UpgradeRecipeBuilder(int exp, ArrayList<Ingredient> items) {
		this.exp = exp;
		this.items = items;
	}

	public static UpgradeRecipeBuilder of(int exp, Item... items) {
		return new UpgradeRecipeBuilder(exp, new ArrayList<>(Stream.of(items).map(Ingredient::of).toList()));
	}

	public UpgradeRecipe build(ResourceLocation id, int i) {
		return new UpgradeRecipe(id, i, exp, items);
	}

}
