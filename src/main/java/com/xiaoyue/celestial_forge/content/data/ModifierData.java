package com.xiaoyue.celestial_forge.content.data;

import dev.xkmc.l2library.serial.config.BaseConfig;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

@SerialClass
public class ModifierData extends BaseConfig {

	@SerialClass.SerialField
	private ModifierType type;
	@SerialClass.SerialField
	private int weight;
	@SerialClass.SerialField
	private ArrayList<ModifierEntry> modifiers;
	@SerialClass.SerialField
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
