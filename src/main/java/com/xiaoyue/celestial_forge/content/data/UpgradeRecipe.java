package com.xiaoyue.celestial_forge.content.data;

import dev.xkmc.l2library.serial.config.BaseConfig;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;

@SerialClass
public class UpgradeRecipe extends BaseConfig {

	@SerialClass.SerialField
	public int exp, level;
	@SerialClass.SerialField
	public ArrayList<Ingredient> items;
	@SerialClass.SerialField
	public ResourceLocation target;

	public UpgradeRecipe() {

	}

	public UpgradeRecipe(ResourceLocation id, int level, int exp, ArrayList<Ingredient> items) {
		this.exp = exp;
		this.items = items;
		this.target = id;
		this.level = level;
	}

	public int exp() {
		return exp;
	}

	public ArrayList<Ingredient> items() {
		return items;
	}

	public ResourceLocation target() {
		return target;
	}

}
