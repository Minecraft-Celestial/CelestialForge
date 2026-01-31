package com.xiaoyue.celestial_forge.compat;

import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.register.CFFlags;
import com.xiaoyue.celestial_forge.register.CFItems;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.*;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@JeiPlugin
public class CFJeiPlugin implements IModPlugin {

	private static final ResourceLocation ID = CelestialForge.loc("main");
	private static final ReinforceRecipeCategory REINFORCE = new ReinforceRecipeCategory();

	@Override
	public ResourceLocation getPluginUid() {
		return ID;
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {
		IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
		registration.addRecipeCategories(REINFORCE.init(guiHelper));
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
		registration.addRecipeCatalyst(Items.SMITHING_TABLE.getDefaultInstance(), REINFORCE.getRecipeType());
	}

	private boolean checkMat(Ingredient ingredient) {
		if (ingredient.isEmpty()) return false;
		for (ItemStack stack : ingredient.getItems()) {
			if (stack.isEmpty()) return false;
		}
		return true;
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		List<ReinforceRecipeWrapper> list = new ArrayList<>();
		CFFlags.DATA_MAP.values().forEach(ref -> {
			if (checkMat(ref.temp()) && checkMat(ref.mate())) {
				list.add(new ReinforceRecipeWrapper(ref.temp(), ref.mate(), ref));
			}
		});
		registration.addRecipes(REINFORCE.getRecipeType(), list);
	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registration) {
		registration.addRecipeClickArea(SmithingScreen.class, 68, 49, 22, 15, REINFORCE.getRecipeType());
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		registration.registerSubtypeInterpreter(CFItems.MODIFIER_BOOK.get(), (stack, ctx) ->
				Optional.ofNullable(ModifierUtils.fromBook(stack))
						.map(e -> e.id().toString()).orElse("null")
		);
	}

}
