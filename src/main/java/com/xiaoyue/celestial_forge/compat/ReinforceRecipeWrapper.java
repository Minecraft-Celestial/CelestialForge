package com.xiaoyue.celestial_forge.compat;

import com.xiaoyue.celestial_forge.content.reinforce.IReinforce;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

public record ReinforceRecipeWrapper(Ingredient temp, Ingredient mate, IReinforce output) {

    public Ingredient input() {
        return Ingredient.of(ForgeRegistries.ITEMS.getValues().stream().map(Item::getDefaultInstance));
    }
}
