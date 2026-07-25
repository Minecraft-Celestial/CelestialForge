package com.xiaoyue.celestial_forge.content.data;

import dev.xkmc.l2core.serial.config.BaseConfig;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

@SerialClass
public class ModifierData extends BaseConfig {

	@SerialField
	private ModifierType type;
	@SerialField
	private int weight;
	@SerialField
	private ArrayList<ModifierEntry> modifiers;
	@SerialField
	private ResourceLocation leveling;

	public ModifierData() {
	}

	public ModifierData(
			ModifierType type,
			int weight,
			ArrayList<ModifierEntry> modifiers,
			ResourceLocation leveling
	) {
		this.type = type;
		this.weight = weight;
		this.modifiers = modifiers;
		this.leveling = leveling;
	}

	public ModifierType type() {
		return type;
	}

	public int weight() {
		return weight;
	}

	public List<ModifierEntry> modifiers() {
		return modifiers;
	}

	public ResourceLocation leveling() {
		return leveling;
	}

}
