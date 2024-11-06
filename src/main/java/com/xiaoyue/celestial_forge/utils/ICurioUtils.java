package com.xiaoyue.celestial_forge.utils;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class ICurioUtils {

    public static boolean isCurio(ItemStack itemStack) {
        if (ModList.get().isLoaded("curios")) {
            return itemStack.getItem() instanceof ICurioItem || !CuriosApi.getItemStackSlots(itemStack).keySet().isEmpty();
        }
        return false;
    }
}
