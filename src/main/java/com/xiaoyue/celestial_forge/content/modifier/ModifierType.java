package com.xiaoyue.celestial_forge.content.modifier;

import com.xiaoyue.celestial_forge.utils.ICurioUtils;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public enum ModifierType {
	CURIO(ICurioUtils::isCurio),
	TOOL(ModifierUtils::isTool),
	RANGED(ModifierUtils::isRangedWeapon),
	ARMOR(ModifierUtils::isArmor),
	ALL(e -> false);

	private final Predicate<ItemStack> pred;

	ModifierType(Predicate<ItemStack> pred) {
		this.pred = pred;
	}

	public boolean test(ItemStack stack) {
		return pred.test(stack);
	}

}
