package com.xiaoyue.celestial_forge.content.data;

import com.xiaoyue.celestial_forge.utils.CurioUtils;
import com.xiaoyue.celestial_forge.utils.TypeTestUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.function.Predicate;

public enum ModifierType {
	WEAPON(TypeTestUtils::isWeapon, e -> e == EquipmentSlot.MAINHAND),
	RANGED(TypeTestUtils::isRangedWeapon, e -> e == EquipmentSlot.MAINHAND),
	ARMOR(TypeTestUtils::isArmor, EquipmentSlot::isArmor),
	TOOL(TypeTestUtils::isTool, e -> e == EquipmentSlot.MAINHAND),
	CURIO(CurioUtils::isCurio, e -> false),
	ALL(e -> false, e -> false);

	private final Predicate<ItemStack> item;
	private final Predicate<EquipmentSlot> slot;

	ModifierType(Predicate<ItemStack> item, Predicate<EquipmentSlot> slot) {
		this.item = item;
		this.slot = slot;
	}

	public boolean test(ItemStack stack) {
		return this.item.test(stack);
	}


	public boolean test(EquipmentSlot slot) {
		return this.slot.test(slot);
	}

}
