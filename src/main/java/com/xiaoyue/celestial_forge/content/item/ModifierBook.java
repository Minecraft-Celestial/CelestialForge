package com.xiaoyue.celestial_forge.content.item;

import com.xiaoyue.celestial_core.utils.ItemUtils;
import com.xiaoyue.celestial_forge.content.data.ModifierConfig;
import com.xiaoyue.celestial_forge.content.modifier.ModifierHolder;
import com.xiaoyue.celestial_forge.data.CFLang;
import com.xiaoyue.celestial_forge.content.modifier.ModifierInstance;
import com.xiaoyue.celestial_forge.register.CFItems;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;


public class ModifierBook extends Item {
	public ModifierBook() {
		super(new Item.Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
	}

	@Override
	public Component getName(ItemStack stack) {
		var base = super.getName(stack);
		if (!stack.hasTag() || !stack.getTag().contains(ModifierUtils.bookTagName)) return base;
		ModifierHolder mod = ModifierConfig.getAll().byId(new ResourceLocation(stack.getTag().getString(ModifierUtils.bookTagName)));
		if (mod == null) return base;
		return CFLang.MODIFIER_BOOK.get(mod.getFormattedName());
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> list, TooltipFlag tooltipFlag) {
		if (stack.hasTag() && stack.getTag().contains(ModifierUtils.bookTagName)) {
			ModifierHolder mod = ModifierConfig.getAll().byId(new ResourceLocation(stack.getTag().getString(ModifierUtils.bookTagName)));
			if (mod != null) {
				list.addAll(ModifierInstance.of(mod).getInfoLines());
				list.add(CFLang.MODIFIER_BOOK_INFO.get());
				list.add(ModifierUtils.addModifierTypeTip(mod));
            }
		}
	}

	public static List<ItemStack> getStacksForCreativeTab() {
        List<ItemStack> stacks = new ArrayList<>();
		for (ModifierHolder mod : ModifierConfig.getAll().all()) {
			ItemStack stack = new ItemStack(CFItems.MODIFIER_BOOK.get());
			CompoundTag tag = stack.getOrCreateTag();
			tag.putString(ModifierUtils.bookTagName, mod.id().toString());
			stacks.add(stack);
		}
		return stacks;
	}
}
