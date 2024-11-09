package com.xiaoyue.celestial_forge.content.modifier;

import com.xiaoyue.celestial_forge.content.registry.LevelingConfig;
import com.xiaoyue.celestial_forge.content.registry.ModifierData;
import com.xiaoyue.celestial_forge.content.registry.ModifierType;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public record ModifierHolder(ModifierType type, ResourceLocation id, ModifierData data, LevelingConfig gate) {

	public MutableComponent getFormattedName() {
		return Component.translatable("modifier." + id().getNamespace() + "." + id().getPath()).withStyle(ChatFormatting.AQUA);
	}

}
