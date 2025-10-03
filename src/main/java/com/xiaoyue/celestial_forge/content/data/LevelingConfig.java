package com.xiaoyue.celestial_forge.content.data;

import dev.xkmc.l2library.serial.config.BaseConfig;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.resources.ResourceLocation;

@SerialClass
public class LevelingConfig extends BaseConfig {

	@SerialClass.SerialField
	private int baseCost;
	@SerialClass.SerialField
	private double expCost;
	@SerialClass.SerialField
	private ResourceLocation upgrades;

	public LevelingConfig() {

	}

	public LevelingConfig(int baseCost, double expCost, ResourceLocation upgrades) {
		this.baseCost = baseCost;
		this.expCost = expCost;
		this.upgrades = upgrades;
	}

	public int baseCost() {
		return baseCost;
	}

	public double expCost() {
		return expCost;
	}

	public ResourceLocation upgrades() {
		return upgrades;
	}

}
