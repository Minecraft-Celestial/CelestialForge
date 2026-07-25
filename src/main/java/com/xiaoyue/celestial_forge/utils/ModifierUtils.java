package com.xiaoyue.celestial_forge.utils;

import com.mojang.datafixers.util.Pair;
import com.xiaoyue.celestial_forge.content.component.ModifierData;
import com.xiaoyue.celestial_forge.content.data.ModifierDataHolder;
import com.xiaoyue.celestial_forge.content.modifier.ModifierHolder;
import com.xiaoyue.celestial_forge.content.modifier.ModifierInstance;
import com.xiaoyue.celestial_forge.data.CFModConfig;
import com.xiaoyue.celestial_forge.register.CFItems;
import com.xiaoyue.celestial_forge.register.CFObjects;
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

	@Nullable
	public static ModifierInstance rollModifier(ItemStack stack, RandomSource random) {
		var type = TypeTestUtils.getType(stack, false);
		if (type == null) return null;
		var ins = ModifierDataHolder.byType(type).roll(random);
		return ins == null ? null : ModifierInstance.of(ins);
	}

	public static void removeModifier(ItemStack stack) {
		stack.remove(CFObjects.MODIFIER_DATA);
	}

	public static void setModifier(ItemStack stack, ModifierInstance modifier) {
		ModifierData data = new ModifierData(modifier.holder().id().toString(), modifier.level(), modifier.exp(), "", false);
		stack.set(CFObjects.MODIFIER_DATA, data);
	}

	@Nullable
	public static ModifierInstance getModifier(ItemStack stack) {
		if (stack.isEmpty()) return null;
		if (!TypeTestUtils.mightHaveModifiers(stack)) return null;
		ModifierData data = ModifierData.getOrCreate(stack);
		if (data.name().isEmpty()) return null;
		int level = data.level();
		int exp = data.exp();
		var ans = ModifierDataHolder.byId(ResourceLocation.parse(data.name()));
		return ans == null ? null : new ModifierInstance(ans, level, exp);
	}

	@Nullable
	public static ModifierHolder fromBook(ItemStack right) {
		ModifierData data = ModifierData.getOrCreate(right);
		return ModifierDataHolder.byId(ResourceLocation.parse(data.book()));
	}

	public static ItemStack bookOf(ModifierHolder mod, int lv, boolean reinforced) {
		ItemStack stack = new ItemStack(CFItems.MODIFIER_BOOK.get());
		ModifierData data = new ModifierData("", lv, 0, mod.id().toString(), reinforced);
		stack.set(CFObjects.MODIFIER_DATA, data);
		return stack;
	}

	public static int getMaxExp(int level) {
		double rate = CFModConfig.SERVER.modifierMaxLevelRate.get();
		int base = CFModConfig.SERVER.modifierMaxLevelBase.get();
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
