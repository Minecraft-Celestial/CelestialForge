package com.xiaoyue.celestial_forge.utils;

import com.mojang.datafixers.util.Pair;
import com.xiaoyue.celestial_forge.content.data.DataHolder;
import com.xiaoyue.celestial_forge.content.modifier.ModifierHolder;
import com.xiaoyue.celestial_forge.content.modifier.ModifierInstance;
import com.xiaoyue.celestial_forge.data.CFModConfig;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModifierUtils {

	public static final String tagName = "CelestialForge_ItemModifier";
	public static final String levelName = "CelestialForge_ItemLevel";
	public static final String expName = "CelestialForge_ItemExp";
	public static final String bookTagName = "CelestialForge_BookModifier";

	@Nullable
	public static ModifierInstance rollModifier(ItemStack stack, RandomSource random) {
		var type = TypeTestUtils.getType(stack);
		if (type == null) return null;
		var ins = DataHolder.byType(type).roll(random);
		return ins == null ? null : ModifierInstance.of(ins);
	}

	public static void removeModifier(ItemStack stack) {
		var tag = stack.getTag();
		if (tag != null) {
			tag.remove(tagName);
			tag.remove(levelName);
			tag.remove(expName);
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
		if (stack.isEmpty()) return null;
		if (!TypeTestUtils.mightHaveModifiers(stack)) return null;
		CompoundTag tag = stack.getTag();
		if (tag == null) return null;
		if (!tag.contains(tagName)) return null;
		int level = tag.getInt(levelName);
		int exp = tag.getInt(expName);
		var ans = DataHolder.byId(new ResourceLocation(tag.getString(tagName)));
		return ans == null ? null : new ModifierInstance(ans, level, exp);
	}

	@Nullable
	public static ModifierHolder fromBook(ItemStack right) {
		var tag = right.getTag();
		return tag == null ? null : DataHolder.byId(new ResourceLocation(tag.getString(ModifierUtils.bookTagName)));
	}

	public static int getMaxExp(int level) {
		double rate = CFModConfig.COMMON.modifierMaxLevelRate.get();
		int base = CFModConfig.COMMON.modifierMaxLevelBase.get();
		return (int) (Math.pow(2d, level * rate) * base);
	}

	public static void addExp(ItemStack stack, ModifierInstance modifier, int toAdd) {
		setModifier(stack, modifier.addExp(toAdd));
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
		CurioUtils.addPlayerSlots(player, stack -> Optional.ofNullable(ModifierUtils.getModifier(stack))
				.filter(ModifierInstance::canUpgrade).ifPresent(ins -> list.add(Pair.of(stack, ins))));
		return list;
	}

	public static void addExpToPlayer(Player player, int total) {
		var list = ModifierUtils.getAllOnPlayer(player);
		int n = list.size();
		if (n == 0) return;
		int base = total / n;
		int avail = total - base;
		var r = player.getRandom();
		for (int i = 0; i < n; i++) {
			int a = r.nextInt(n);
			int b = r.nextInt(n);
			var t = list.get(a);
			list.set(a, list.get(b));
			list.set(b, t);
		}
		for (var e : list) {
			int toAdd = base;
			if (avail > 0) {
				toAdd++;
				avail--;
			}
			ModifierUtils.addExp(e.getFirst(), e.getSecond(), toAdd);
		}
	}

}
