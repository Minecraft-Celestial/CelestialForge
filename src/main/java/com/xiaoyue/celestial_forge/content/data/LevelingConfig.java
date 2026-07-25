package com.xiaoyue.celestial_forge.content.data;

import dev.xkmc.l2core.serial.config.BaseConfig;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.resources.ResourceLocation;

@SerialClass
public class LevelingConfig extends BaseConfig {

	@SerialField
	private int baseCost;
	@SerialField
	private double expCost;
	@SerialField
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
