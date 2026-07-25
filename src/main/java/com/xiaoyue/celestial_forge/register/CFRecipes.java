package com.xiaoyue.celestial_forge.register;

import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.content.reinforce.ReinforceRecipe;
import com.xiaoyue.celestial_invoker.content.common.registrar.NeoForgeRegister;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.function.Supplier;

public class CFRecipes {

    public static final NeoForgeRegister<RecipeType<?>> TYPE = CelestialForge.EXTRA.neoforgeRegister(BuiltInRegistries.RECIPE_TYPE);
    public static final NeoForgeRegister<RecipeSerializer<?>> SERIALIZER = CelestialForge.EXTRA.neoforgeRegister(BuiltInRegistries.RECIPE_SERIALIZER);

    public static final Supplier<RecipeType<ReinforceRecipe>> RT_REINFORCE = TYPE.object("reinforce", RecipeType::simple);
    public static final Supplier<RecipeSerializer<ReinforceRecipe>> RS_REINFORCE = SERIALIZER.object("reinforce", ReinforceRecipe.Serial::new);

    public static void register() {
    }
}
