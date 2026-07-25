package com.xiaoyue.celestial_forge.content.reinforce;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.xiaoyue.celestial_forge.content.component.ReinforceData;
import com.xiaoyue.celestial_forge.data.CFTagGen;
import com.xiaoyue.celestial_forge.register.CFFlags;
import com.xiaoyue.celestial_forge.register.CFObjects;
import com.xiaoyue.celestial_forge.register.CFRecipes;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

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
                break;
            }
        }
        return flag;
    }

    @Override
    public boolean isBaseIngredient(ItemStack stack) {
        boolean flag = false;
        for (IReinforce ref : CFFlags.DATA_MAP.values()) {
            if (ref.isInput(stack)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public boolean isAdditionIngredient(ItemStack stack) {
        boolean flag = false;
        for (IReinforce ref : CFFlags.DATA_MAP.values()) {
            if (ref.mate().test(stack)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public boolean matches(SmithingRecipeInput input, Level level) {
        for (IReinforce reinforce : CFFlags.DATA_MAP.values()) {
            if (!reinforce.temp().test(input.getItem(0))) continue;
            if (!reinforce.mate().test(input.getItem(2))) continue;
            return !input.getItem(1).isEmpty();
        }
        return false;
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput input, HolderLookup.Provider provider) {
        ItemStack temp = input.getItem(0);
        ItemStack mainInput = input.getItem(1);
        ItemStack mate = input.getItem(2);
        List<IReinforce> list = new ArrayList<>();
        for (IReinforce value : CFFlags.DATA_MAP.values()) {
            if (value.isInput(mainInput)) {
                list.add(value);
            }
        }
        for (IReinforce value : list) {
            if (mate.is(CFTagGen.REINFORCE_BLACK_LIST)) {
                return ItemStack.EMPTY;
            }
            if (value.mate().test(mate) && value.temp().test(temp)) {
                if (!value.hasFlag(mainInput)) {
                    ItemStack output = mainInput.copy();
                    ReinforceData data = new ReinforceData(output.getDescriptionId());
                    output.set(CFObjects.REINFORCED_DATA, data);
                    return output;
                }
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CFRecipes.RS_REINFORCE.get();
    }

    public ResourceLocation getId() {
        return id;
    }

    public static class Serial implements RecipeSerializer<ReinforceRecipe> {

        @Override
        public MapCodec<ReinforceRecipe> codec() {
            return RecordCodecBuilder.mapCodec(ins -> ins.group(
                    ResourceLocation.CODEC.fieldOf("type").forGetter(ReinforceRecipe::getId)
            ).apply(ins, ReinforceRecipe::new));
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, ReinforceRecipe> streamCodec() {
            return StreamCodec.of((buf, recipe) -> buf.writeResourceLocation(recipe.getId()),
                    buf -> new ReinforceRecipe(buf.readResourceLocation()));
        }
    }
}
