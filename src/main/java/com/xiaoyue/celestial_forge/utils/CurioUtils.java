package com.xiaoyue.celestial_forge.utils;

import com.xiaoyue.celestial_forge.data.CFModConfig;
import com.xiaoyue.celestial_forge.data.CFTagGen;
import com.xiaoyue.celestial_forge.events.CuriosHandler;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Consumer;

public class CurioUtils {

	private static boolean loaded() {
		return ModList.get().isLoaded("curios");
	}

	public static void addPlayerSlots(Player player, Consumer<ItemStack> action) {
		if (!loaded()) return;
		var opt = CuriosApi.getCuriosInventory(player).resolve();
		if (opt.isPresent()) {
			for (var a : opt.get().getCurios().values()) {
				int n = a.getSlots();
				for (int i = 0; i < n; i++) {
					ItemStack stack = a.getStacks().getStackInSlot(i);
					action.accept(stack);
				}
			}
		}
	}

	public static boolean isCurio(ItemStack stack, boolean isClient) {
		if (!loaded()) return false;
		if (!CFModConfig.COMMON.enableCurioForging.get()) return false;
		if (stack.is(CFTagGen.CURIO_MODIFIABLE)) return true;
		var map = CuriosApi.getItemStackSlots(stack, isClient);
		if (map.isEmpty()) return false;
		for (var e : map.values()) {
			if (e.useNativeGui()) return true;
		}
		return false;
	}

	public static void register() {
		if (loaded()) MinecraftForge.EVENT_BUS.register(CuriosHandler.class);
	}
}
