package com.xiaoyue.celestial_forge.content.data;

import com.xiaoyue.celestial_forge.content.registry.LevelingConfig;
import com.xiaoyue.celestial_forge.content.registry.ModifierData;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedHashMap;

public record DataModifierSet(LinkedHashMap<ResourceLocation, ModifierData> entries, LevelingConfig gate) {

}
