package com.xiaoyue.celestial_forge.content.reinforce;

import com.xiaoyue.celestial_forge.data.CFTagGen;
import com.xiaoyue.celestial_forge.utils.CurioUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.event.AnvilUpdateEvent;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public interface IReinforce {

    String itemRefName = "CelestialForge_ItemReinforce";

    String flag();

    default @Nullable Ingredient mate() {
        return null;
    }

    int cost();

    List<Component> tooltip();

    static boolean isReinforced(ItemStack stack) {
        if (stack.hasTag()) {
            return stack.getTag().getBoolean(itemRefName);
        }
        return false;
    }

    default boolean hasFlag(ItemStack stack) {
        if (stack.hasTag()) {
            return stack.getTag().getBoolean(flag());
        }
        return false;
    }

    default void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack stack = event.getLeft().copy();
        ItemStack right = event.getRight();
        Ingredient mate = mate();
        if (mate == null || right.isEmpty() || right.is(CFTagGen.REF_BLACK_LIST)) return;
        if (mate.test(right) && !hasFlag(stack) && !isReinforced(stack)) {
            event.setMaterialCost(1);
            event.setCost(cost());
            stack.getOrCreateTag().putBoolean(itemRefName, true);
            stack.getOrCreateTag().putBoolean(flag(), true);
            event.setOutput(stack);
        }
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
