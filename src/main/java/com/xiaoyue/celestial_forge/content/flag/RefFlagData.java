package com.xiaoyue.celestial_forge.content.flag;

import com.xiaoyue.celestial_forge.register.CFFlags;
import com.xiaoyue.celestial_forge.utils.CurioUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RefFlagData {

    public static final String itemRefName = "CelestialForge_ItemReinforce";

    public final String flag;
    public final @Nullable ItemLike mate;
    public final int cost;
    public final List<Component> tooltips;

    public RefFlagData(String flag, String mate, int cost, List<Component> tooltips) {
        this.flag = flag;
        this.mate = ForgeRegistries.ITEMS.getValue(new ResourceLocation(mate));
        this.cost = cost;
        this.tooltips = tooltips;
        CFFlags.DATA_LIST.add(this);
    }

    public RefFlagData(String flag, String mate, int cost, Component tooltip) {
        this.flag = flag;
        this.mate = ForgeRegistries.ITEMS.getValue(new ResourceLocation(mate));
        this.cost = cost;
        this.tooltips = List.of(tooltip);
        CFFlags.DATA_LIST.add(this);
    }

    public static boolean isReinforced(ItemStack stack) {
        if (stack.hasTag()) {
            return stack.getTag().getBoolean(itemRefName);
        }
        return false;
    }

    public boolean hasFlag(ItemStack stack) {
        if (stack.hasTag()) {
            return stack.getTag().getBoolean(flag);
        }
        return false;
    }

    public void onAnvilUpdate(AnvilUpdateEvent event) {
        ItemStack stack = event.getLeft().copy();
        if (mate == null) return;
        if (event.getRight().is(mate.asItem()) && !hasFlag(stack) && !isReinforced(stack)) {
            event.setMaterialCost(1);
            event.setCost(cost);
            stack.getOrCreateTag().putBoolean(itemRefName, true);
            stack.getOrCreateTag().putBoolean(flag, true);
            event.setOutput(stack);
        }
    }

    public List<ItemStack> getItemsForFlag(LivingEntity entity) {
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
