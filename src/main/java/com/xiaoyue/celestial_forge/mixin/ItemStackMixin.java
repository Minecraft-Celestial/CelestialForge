package com.xiaoyue.celestial_forge.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.xiaoyue.celestial_forge.content.reinforce.ReinforceData;
import com.xiaoyue.celestial_forge.register.CFFlags;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.common.extensions.IForgeItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

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

	@Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;appendEnchantmentNames(Ljava/util/List;Lnet/minecraft/nbt/ListTag;)V"), method = "getTooltipLines")
	public void celestialForge$addText(Player player, TooltipFlag flag, CallbackInfoReturnable<List<Component>> cir, @Local List<Component> list) {
		ItemStack self = (ItemStack) (Object) this;
		CFFlags.DATA_MAP.values().forEach(data -> {
			if (data.hasFlag(self)) {
				list.add(Component.empty());
				list.addAll(data.tooltip());
			}
		});
		if (self.isEnchanted() && self.getOrCreateTag().getBoolean(ReinforceData.itemReinforceName)) {
			list.add(Component.empty());
		}
	}
}
