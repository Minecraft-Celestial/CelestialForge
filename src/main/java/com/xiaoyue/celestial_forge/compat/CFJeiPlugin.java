package com.xiaoyue.celestial_forge.compat;

import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.register.CFItems;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

@JeiPlugin
public class CFJeiPlugin implements IModPlugin {

	private static final ResourceLocation ID = CelestialForge.loc("main");

	@Override
	public ResourceLocation getPluginUid() {
		return ID;
	}

	@Override
	public void registerItemSubtypes(ISubtypeRegistration registration) {
		registration.registerSubtypeInterpreter(CFItems.MODIFIER_BOOK.get(), (stack, ctx) ->
				Optional.ofNullable(ModifierUtils.fromBook(stack))
						.map(e -> e.id().toString()).orElse("null")
		);
	}

}
