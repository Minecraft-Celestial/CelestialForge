package com.xiaoyue.celestial_forge.compat;

import com.xiaoyue.celestial_forge.content.reinforce.IReinforce;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

public record ReinforceRecipeWrapper(Ingredient temp, Ingredient mate, IReinforce output) {

    public Ingredient input() {
        List<ItemStack> list = new ArrayList<>();
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (output.isInput(item.getDefaultInstance())) {
                list.add(item.getDefaultInstance());
            }
        }
        return Ingredient.of(list.stream());
    }

    public Ingredient result() {
        List<ItemStack> list = new ArrayList<>();
        for (ItemStack stack : input().getItems()) {
            stack.getOrCreateTag().putBoolean(IReinforce.itemRefName, true);
            stack.getOrCreateTag().putBoolean(output().flag(), true);
            list.add(stack);
        }
        return Ingredient.of(list.stream());
    }


}
