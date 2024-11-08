package com.xiaoyue.celestial_forge.content.data;

import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;

public record DataModifierSet(LinkedHashMap<ResourceLocation, ModifierData> entries, LevelGater gate) {

}
