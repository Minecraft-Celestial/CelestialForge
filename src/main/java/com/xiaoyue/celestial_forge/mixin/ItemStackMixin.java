package com.xiaoyue.celestial_forge.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.xiaoyue.celestial_forge.content.component.ReinforceData;
import com.xiaoyue.celestial_forge.register.CFFlags;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.neoforge.common.extensions.IItemStackExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements IItemStackExtension {

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

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;addToTooltip(Lnet/minecraft/core/component/DataComponentType;Lnet/minecraft/world/item/Item$TooltipContext;Ljava/util/function/Consumer;Lnet/minecraft/world/item/TooltipFlag;)V", ordinal = 3), method = "getTooltipLines")
	public void celestialForge$addText(Item.TooltipContext tooltipContext, Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir, @Local List<Component> list) {
		ItemStack self = (ItemStack) (Object) this;
		CFFlags.DATA_MAP.values().forEach(data -> {
			if (data.hasFlag(self)) {
				list.add(Component.empty());
				list.addAll(data.tooltip());
			}
		});
		if (self.isEnchanted() && !ReinforceData.getOrCreate(self).name().isEmpty()) {
			list.add(Component.empty());
		}
	}
}
