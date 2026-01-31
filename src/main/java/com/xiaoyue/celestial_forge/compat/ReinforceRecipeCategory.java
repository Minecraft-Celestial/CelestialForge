package com.xiaoyue.celestial_forge.compat;

import com.xiaoyue.celestial_forge.CelestialForge;
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
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import static com.xiaoyue.celestial_forge.CelestialForge.MODID;

public class ReinforceRecipeCategory implements IRecipeCategory<ReinforceRecipeWrapper> {

	public static final RecipeType<ReinforceRecipeWrapper> RECIPE_TYPE = RecipeType.create(MODID,
			"reinforce", ReinforceRecipeWrapper.class);

	private IDrawable background;
	private IDrawable icon;

	public static final ResourceLocation RECIPE_GUI_VANILLA = CelestialForge.loc("textures/gui/gui_vanilla.png");

	public ReinforceRecipeCategory init(IGuiHelper guiHelper) {
		this.background = guiHelper.createDrawable(RECIPE_GUI_VANILLA, 0, 168, 108, 18);
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

	@SuppressWarnings("all")
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
		ObjectWrapper<Ingredient> temp = new ObjectWrapper<>(wrapper.temp());
		ObjectWrapper<Ingredient> input = new ObjectWrapper<>(wrapper.input());
		ObjectWrapper<Ingredient> mate = new ObjectWrapper<>(wrapper.mate());
		ObjectWrapper<Ingredient> output = new ObjectWrapper<>(wrapper.result());
		if (!focus.isEmpty()) {
			focus.getItemStackFocuses(RecipeIngredientRole.CATALYST).findFirst().ifPresent(item -> {
				ItemStack stack = item.getTypedValue().getIngredient().copy();
				if (wrapper.temp().test(stack)) {
					temp.setObject(Ingredient.of(stack));
				}
				if (wrapper.mate().test(stack)) {
					mate.setObject(Ingredient.of(stack));
				}
			});
			focus.getItemStackFocuses(RecipeIngredientRole.INPUT).findFirst().ifPresent(item -> {
				ItemStack stack = item.getTypedValue().getIngredient().copy();
				if (wrapper.input().test(stack)) {
					stack.getOrCreateTag().putBoolean(IReinforce.itemReinforceName, true);
					stack.getOrCreateTag().putBoolean(wrapper.output().flag(), true);
					output.setObject(Ingredient.of(stack));
				}
				output.setObject(output.getObject());
			});
		}
		builder.addSlot(RecipeIngredientRole.CATALYST, 1, 1)
				.addIngredients(temp.getObject());
		builder.addSlot(RecipeIngredientRole.INPUT, 19, 1)
				.addIngredients(input.getObject());
		builder.addSlot(RecipeIngredientRole.CATALYST, 37, 1)
				.addIngredients(mate.getObject());
		builder.addSlot(RecipeIngredientRole.RENDER_ONLY, 91, 1)
				.addIngredients(output.getObject());
	}

	public static class ObjectWrapper<T> {
		private T object;

		public ObjectWrapper(T object) {
			this.object = object;
		}

		public void setObject(T newObject) {
			this.object = newObject;
		}

		public T getObject() {
			return object;
		}
	}
}
