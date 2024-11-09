package com.xiaoyue.celestial_forge.content.modifier;

import com.xiaoyue.celestial_forge.content.registry.ModifierType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public record ModifierPool(ModifierType type, int totalWeight, List<ModifierHolder> modifiers) {

	public static ModifierPool construct(ModifierType type, List<ModifierHolder> modifiers) {
		int total = 0;
		for (var e : modifiers) {
			total += e.data().weight();
		}
		return new ModifierPool(type, total, modifiers);
	}

	@Nullable
	public ModifierHolder roll(RandomSource random) {
		if (totalWeight == 0 || modifiers.isEmpty()) return null;
		int i = random.nextInt(totalWeight);
		int j = 0;
		for (ModifierHolder modifier : modifiers) {
			j += modifier.data().weight();
			if (i < j) {
				return modifier;
			}
		}
		return null;
	}

}
