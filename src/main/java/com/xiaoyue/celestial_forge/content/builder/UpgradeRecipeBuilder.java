package com.xiaoyue.celestial_forge.content.builder;

import com.xiaoyue.celestial_forge.content.data.UpgradeRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class UpgradeRecipeBuilder {

	private final int exp;
	private final ArrayList<Ingredient> items;
	private final String altId;

	public UpgradeRecipeBuilder(int exp, ArrayList<Ingredient> items, @Nullable String altId) {
		this.exp = exp;
		this.items = items;
		this.altId = altId;
	}

	public static UpgradeRecipeBuilder of(int exp, Item... items) {
		var list = new ArrayList<Ingredient>();
		String altId = null;
		for (var e : items) {
			list.add(Ingredient.of(e));
			var id = BuiltInRegistries.ITEM.getKey(e);
            var modid = id.getNamespace();
            if (!modid.equals("minecraft")) {
                altId = modid;
            }
        }
		return new UpgradeRecipeBuilder(exp, list, altId);
	}

	public UpgradeRecipe build(ResourceLocation id, int i) {
		return new UpgradeRecipe(id, i, exp, items);
	}

	public ResourceLocation loc(ResourceLocation id, int lv) {
		return ResourceLocation.fromNamespaceAndPath(altId == null ? id.getNamespace() : altId, id.getPath() + "/" + lv);
	}
}
