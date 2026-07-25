package com.xiaoyue.celestial_forge.content.reinforce;

import com.xiaoyue.celestial_forge.content.component.ReinforceData;
import com.xiaoyue.celestial_forge.content.data.ModifierType;
import com.xiaoyue.celestial_forge.utils.CurioUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public interface IReinforce {

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

    default boolean hasFlag(ItemStack stack) {
        String name = ReinforceData.getOrCreate(stack).name();
        return name.equals(flag());
    }

    default void postItemsFlag(LivingEntity entity, BiConsumer<ItemStack, Integer> cons) {
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
        if (!list.isEmpty()) {
            list.forEach(stack -> cons.accept(stack, list.size()));
        }
    }
}
