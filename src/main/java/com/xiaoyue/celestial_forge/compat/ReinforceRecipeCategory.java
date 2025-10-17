package com.xiaoyue.celestial_forge.compat;

import com.xiaoyue.celestial_forge.content.reinforce.IReinforce;
import com.xiaoyue.celestial_forge.data.CFLang;
import com.xiaoyue.celestial_forge.register.CFItems;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.common.Constants;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.atomic.AtomicReference;

import static com.xiaoyue.celestial_forge.CelestialForge.MODID;

public class ReinforceRecipeCategory implements IRecipeCategory<ReinforceRecipeWrapper> {

    public static final RecipeType<ReinforceRecipeWrapper> RECIPE_TYPE = RecipeType.create(MODID,
            "reinforce", ReinforceRecipeWrapper.class);

    private IDrawable background;
    private IDrawable icon;

    public ReinforceRecipeCategory init(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(Constants.RECIPE_GUI_VANILLA, 0, 168, 108, 18);
        this.icon = guiHelper.createDrawableItemStack(CFItems.HAMMER.asStack());
        return this;
    }

    @Override
    public RecipeType<ReinforceRecipeWrapper> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return CFLang.REINFORCE_TITLE.get();
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ReinforceRecipeWrapper wrapper, IFocusGroup focus) {
        AtomicReference<Ingredient> input = new AtomicReference<>(wrapper.input());
        focus.getItemStackFocuses(RecipeIngredientRole.INPUT).findFirst().ifPresent(item -> {
            ItemStack stack = item.getTypedValue().getIngredient().copy();
            stack.getOrCreateTag().putBoolean(IReinforce.itemRefName, true);
            stack.getOrCreateTag().putBoolean(wrapper.output().flag(), true);
            input.set(Ingredient.of(stack));
        });
        builder.addSlot(RecipeIngredientRole.INPUT, 1, 1)
                .addIngredients(wrapper.temp());
        builder.addSlot(RecipeIngredientRole.INPUT, 19, 1)
                .addIngredients(wrapper.input());
        builder.addSlot(RecipeIngredientRole.INPUT, 37, 1)
                .addIngredients(wrapper.mate());
        builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 91, 1)
                .addIngredients(input.get());
    }
}
