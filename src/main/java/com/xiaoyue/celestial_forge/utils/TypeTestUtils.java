package com.xiaoyue.celestial_forge.utils;

import com.xiaoyue.celestial_forge.content.data.ModifierType;
import com.xiaoyue.celestial_forge.data.CFModConfig;
import com.xiaoyue.celestial_forge.data.CFTagGen;
import net.minecraft.world.item.*;
import net.minecraftforge.server.ServerLifecycleHooks;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;

public class TypeTestUtils {

	private static final HashSet<Item> INVALID = new HashSet<>();
	public static final HashMap<Item, ModifierType> CACHE = new HashMap<>();

	public static void clearCache() {
		INVALID.clear();
		CACHE.clear();
	}

	public static boolean mightHaveModifiers(ItemStack stack) {
		return !stack.isEmpty() && stack.getMaxStackSize() == 1 && !stack.is(CFTagGen.MODIFIER_BLACK_LIST);
	}

	public static boolean isRangedWeapon(ItemStack stack) {
		if (!CFModConfig.COMMON.enableRangedForging.get()) return false;
		return stack.getItem() instanceof BowItem ||
				stack.getItem() instanceof CrossbowItem ||
				stack.is(CFTagGen.RANGED_MODIFIABLE);
	}

	public static boolean isTool(ItemStack stack) {
		if (!CFModConfig.COMMON.enableToolForging.get()) return false;
		return stack.getItem() instanceof TieredItem ||
				stack.getItem() instanceof ShieldItem ||
				stack.getItem() instanceof FishingRodItem ||
				stack.is(CFTagGen.TOOL_MODIFIABLE);
	}

	public static boolean isArmor(ItemStack stack) {
		if (!CFModConfig.COMMON.enableArmorForging.get()) return false;
		return stack.getItem() instanceof ArmorItem ||
				stack.is(CFTagGen.ARMOR_MODIFIABLE);
	}

	public static boolean isWeapon(ItemStack stack) {
		if (!CFModConfig.COMMON.enableWeaponForging.get()) return false;
		return stack.getItem() instanceof SwordItem ||
				stack.getItem() instanceof AxeItem ||
				stack.getItem() instanceof TridentItem ||
				stack.is(CFTagGen.WEAPON_MODIFIABLE);
	}

	@Nullable
	public static ModifierType getType(ItemStack stack) {
		return getType(stack, ServerLifecycleHooks.getCurrentServer() == null);
	}

	@Nullable
	public static ModifierType getType(ItemStack stack, boolean isClient) {
		if (!mightHaveModifiers(stack)) return null;
		if (INVALID.contains(stack.getItem())) return null;
		var ans = CACHE.get(stack.getItem());
		if (ans != null) return ans;
		for (var e : ModifierType.values()) {
			if (e.test(stack, isClient)) {
				CACHE.put(stack.getItem(), e);
				return e;
			}
		}
		INVALID.add(stack.getItem());
		return null;
	}

}
