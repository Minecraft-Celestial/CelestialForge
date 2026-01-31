package com.xiaoyue.celestial_forge.content.reinforce;

import com.xiaoyue.celestial_forge.content.data.ModifierType;
import com.xiaoyue.celestial_forge.utils.CurioUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;

public interface IReinforce {

	String itemReinforceName = "CelestialForge_ItemReinforce";

	String flag();

	default Ingredient temp() {
		return Ingredient.EMPTY;
	}

	default Ingredient mate() {
		return Ingredient.EMPTY;
	}

	List<Component> tooltip();

	List<ModifierType> types();

	default boolean isInput(ItemStack item) {
		boolean flag = false;
		for (ModifierType type : types()) {
			if (type.equals(ModifierType.ALL)) {
				flag = true;
				break;
			}
			if (type.test(item, ServerLifecycleHooks.getCurrentServer() == null)) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	static boolean isReinforced(ItemStack stack) {
		if (stack.hasTag()) {
			return stack.getTag().getBoolean(itemReinforceName);
		}
		return false;
	}

	default boolean hasFlag(ItemStack stack) {
		if (stack.hasTag()) {
			return stack.getTag().getBoolean(flag());
		}
		return false;
	}

	default List<ItemStack> getItemsForFlag(LivingEntity entity) {
		List<ItemStack> list = new ArrayList<>();
		for (EquipmentSlot slot : EquipmentSlot.values()) {
			ItemStack stack = entity.getItemBySlot(slot);
			if (stack.isEmpty() || !hasFlag(stack)) continue;
			list.add(stack);
		}
		if (entity instanceof Player player) {
			CurioUtils.addPlayerSlots(player, stack -> {
				if (!stack.isEmpty() && hasFlag(stack)) {
					list.add(stack);
				}
			});
		}
		return list;
	}
}
