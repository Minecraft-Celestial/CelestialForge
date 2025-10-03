package com.xiaoyue.celestial_forge.mixin;

import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.extensions.IForgeItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements IForgeItemStack {

	@Override
	public boolean canGrindstoneRepair() {
		ItemStack self = (ItemStack) (Object) this;
		return self.getItem().canGrindstoneRepair(self);
	}

	@Inject(at = @At("HEAD"), method = "canGrindstoneRepair", cancellable = true, remap = false)
	public void celestialForge$canGrindstoneRepair(CallbackInfoReturnable<Boolean> cir) {
		ItemStack self = (ItemStack) (Object) this;
		if (ModifierUtils.getModifier(self) != null) {
			cir.setReturnValue(true);
		}
	}


}
