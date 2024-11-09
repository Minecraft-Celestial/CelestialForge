package com.xiaoyue.celestial_forge.utils;

import com.mojang.datafixers.util.Pair;
import com.xiaoyue.celestial_forge.content.data.ModifierConfig;
import com.xiaoyue.celestial_forge.content.registry.ModifierType;
import com.xiaoyue.celestial_forge.content.modifier.ModifierHolder;
import com.xiaoyue.celestial_forge.content.modifier.ModifierInstance;
import com.xiaoyue.celestial_forge.data.CFLang;
import com.xiaoyue.celestial_forge.data.CFTagGen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import top.theillusivec4.curios.api.CuriosApi;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ModifierUtils {

	public static final String tagName = "itemModifier";
	public static final String levelName = "itemLevel";
	public static final String expName = "itemExp";
	public static final String bookTagName = "bookModifier";

	public static boolean canHaveModifiers(ItemStack stack) {
		return !stack.isEmpty() && stack.getMaxStackSize() <= 1 && !stack.is(CFTagGen.BLACK_LIST);
	}

	public static Component addModifierTypeTip(ModifierHolder modifier) {
		return switch (modifier.type()) {
			case ARMOR -> CFLang.EQUIPPED_TYPE.get();
			case TOOL -> CFLang.TOOLS_TYPE.get();
			case RANGED -> CFLang.RANGED_TYPE.get();
			case CURIO -> CFLang.CURIO_TYPE.get();
			case ALL -> CFLang.ALL_TYPE.get();
			case WEAPON -> CFLang.WEAPON_TYPE.get();
		};
	}

	public static boolean isRangedWeapon(ItemStack stack) {
		return stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem || stack.is(CFTagGen.RANGED_MODIFIABLE);
	}

	public static boolean isTool(ItemStack stack) {
		return stack.getItem() instanceof DiggerItem || stack.getItem() instanceof SwordItem || stack.getItem() instanceof ShieldItem || stack.is(CFTagGen.TOOL_MODIFIABLE);
	}

	public static boolean isArmor(ItemStack stack) {
		return stack.getItem() instanceof ArmorItem || stack.is(CFTagGen.ARMOR_MODIFIABLE);
	}

	public static boolean isWeapon(ItemStack stack) {
		return stack.getItem() instanceof SwordItem || isRangedWeapon(stack) || stack.is(CFTagGen.WEAPON_MODIFIABLE);
	}

	@Nullable
	public static ModifierType getType(ItemStack stack) {
		if (!canHaveModifiers(stack)) return null;
		for (var e : ModifierType.values()) {
			if (e.test(stack)) {
				return e;
			}
		}
		return null;
	}

	@Nullable
	public static ModifierInstance rollModifier(ItemStack stack, RandomSource random) {
		var type = getType(stack);
		if (type == null) return null;
		var ins = ModifierConfig.getAll().byType(type).roll(random);
		return ins == null ? null : ModifierInstance.of(ins);
	}

	public static void removeModifier(ItemStack stack) {
		if (stack.hasTag()) {
			stack.getTag().remove(tagName);
			stack.getTag().remove(levelName);
			stack.getTag().remove(expName);
		}
	}

	public static void setModifier(ItemStack stack, ModifierInstance modifier) {
		CompoundTag tag = stack.getOrCreateTag();
		tag.putString(tagName, modifier.holder().id().toString());
		tag.putInt(levelName, modifier.level());
		tag.putInt(expName, modifier.exp());
	}

	@Nullable
	public static ModifierInstance getModifier(ItemStack stack) {
		if (!canHaveModifiers(stack)) return null;
		CompoundTag tag = stack.getTag();
		if (tag == null) return null;
		if (!tag.contains(tagName)) return null;
		int level = tag.getInt(levelName);
		int exp = tag.getInt(expName);
		var ans = ModifierConfig.getAll().byId(new ResourceLocation(tag.getString(tagName)));
		return ans == null ? null : new ModifierInstance(ans, level, exp);
	}

	public static int getMaxExp(int level) {
		return (int) (Math.pow(2d, level * 0.2) * 25);
	}

	public static void addExp(ItemStack stack, ModifierInstance modifier, int toAdd) {
		setModifier(stack, modifier.addExp(toAdd));
	}

	public static boolean hasModifier(ItemStack stack) {
		if (!canHaveModifiers(stack)) return false;
		CompoundTag tag = stack.getTag();
		return tag != null && tag.contains(tagName);
	}

	public static List<Pair<ItemStack, ModifierInstance>> getAllOnPlayer(Player player) {
		List<Pair<ItemStack, ModifierInstance>> list = new ArrayList<>();
		for (var e : EquipmentSlot.values()) {
			ItemStack stack = player.getItemBySlot(e);
			if (stack.isEmpty()) continue;
			var ins = ModifierUtils.getModifier(stack);
			if (ins != null && ins.canUpgrade()) {
				list.add(Pair.of(stack, ins));
			}
		}
		var opt = CuriosApi.getCuriosInventory(player).resolve();
		if (opt.isPresent()) {
			for (var a : opt.get().getCurios().values()) {
				int n = a.getSlots();
				for (int i = 0; i < n; i++) {
					ItemStack stack = a.getStacks().getStackInSlot(n);
					if (stack.isEmpty()) continue;
					var ins = ModifierUtils.getModifier(stack);
					if (ins != null && ins.canUpgrade()) {
						list.add(Pair.of(stack, ins));
					}
				}
			}
		}
		return list;
	}

}
