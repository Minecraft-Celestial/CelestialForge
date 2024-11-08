package com.xiaoyue.celestial_forge.content.data;

import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;

public record UpgradeRecipe(int exp, ArrayList<Ingredient> list) {
}
