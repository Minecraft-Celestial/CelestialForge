package com.xiaoyue.celestial_forge.register;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.content.reinforce.ReinforceRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class CFRecipes {

    public static final RegistryEntry<RecipeType<ReinforceRecipe>> RT_REINFORCE;
    public static final RegistryEntry<RecipeSerializer<ReinforceRecipe>> RS_REINFORCE;

    static {
        RT_REINFORCE = CelestialForge.REGISTRATE.recipe("reinforce");
        RS_REINFORCE = CelestialForge.REGISTRATE.simple("reinforce", Registries.RECIPE_SERIALIZER, ReinforceRecipe.Serial::new);
    }

    public static void register() {

    }
}
