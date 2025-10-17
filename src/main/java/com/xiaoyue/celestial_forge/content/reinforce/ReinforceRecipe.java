package com.xiaoyue.celestial_forge.content.reinforce;

import com.google.gson.JsonObject;
import com.xiaoyue.celestial_forge.data.CFTagGen;
import com.xiaoyue.celestial_forge.register.CFFlags;
import com.xiaoyue.celestial_forge.register.CFRecipes;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class ReinforceRecipe implements SmithingRecipe {
    public ResourceLocation id;

    public ReinforceRecipe(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public boolean isTemplateIngredient(ItemStack stack) {
        boolean flag = false;
        for (IReinforce ref : CFFlags.DATA_MAP.values()) {
            if (ref.temp().test(stack)) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean isBaseIngredient(ItemStack stack) {
        return true;
    }

    @Override
    public boolean isAdditionIngredient(ItemStack stack) {
        boolean flag = false;
        for (IReinforce ref : CFFlags.DATA_MAP.values()) {
            if (ref.mate().test(stack)) {
                flag = true;
            }
        }
        return flag;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return true;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess access) {
        ItemStack temp = container.getItem(0);
        ItemStack input = container.getItem(1);
        ItemStack mate = container.getItem(2);
        for (IReinforce value : CFFlags.DATA_MAP.values()) {
            if (mate.isEmpty() || temp.isEmpty() || mate.is(CFTagGen.REF_BLACK_LIST)) {
                return ItemStack.EMPTY;
            }
            if (value.mate().test(mate) && value.temp().test(temp) && !value.hasFlag(input) && !IReinforce.isReinforced(input)) {
                ItemStack output = input.copy();
                output.getOrCreateTag().putBoolean(IReinforce.itemRefName, true);
                output.getOrCreateTag().putBoolean(value.flag(), true);
                return output;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess access) {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CFRecipes.RS_REINFORCE.get();
    }

    public static class Serial implements RecipeSerializer<ReinforceRecipe> {

        @Override
        public ReinforceRecipe fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
            return new ReinforceRecipe(resourceLocation);
        }

        @Override
        public @Nullable ReinforceRecipe fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
            return new ReinforceRecipe(resourceLocation);
        }

        @Override
        public void toNetwork(FriendlyByteBuf friendlyByteBuf, ReinforceRecipe reinforceRecipe) {

        }
    }
}
