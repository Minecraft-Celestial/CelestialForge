package com.xiaoyue.celestial_forge.utils;

import com.xiaoyue.celestial_forge.data.CFTagGen;
import dev.xkmc.l2library.util.Proxy;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class ICurioUtils {

	public static boolean isCurio(ItemStack stack) {
		if (ModList.get().isLoaded("curios") && Proxy.getClientPlayer() != null) {
			return stack.getItem() instanceof ICurioItem || !CuriosApi.getItemStackSlots(stack, Proxy.getClientPlayer()).values().isEmpty() || stack.is(CFTagGen.CURIO_MODIFIABLE);
		}
		return false;
	}
}
