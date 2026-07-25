package com.xiaoyue.celestial_forge.content.data;

import dev.xkmc.l2core.serial.config.BaseConfig;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;

@SerialClass
public class UpgradeRecipe extends BaseConfig {

	@SerialField
	public int exp, level;
	@SerialField
	public ArrayList<Ingredient> items;
	@SerialField
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
