package com.xiaoyue.celestial_forge.content.item;

import com.xiaoyue.celestial_forge.content.component.ModifierData;
import com.xiaoyue.celestial_forge.content.data.ModifierDataHolder;
import com.xiaoyue.celestial_forge.content.modifier.ModifierHolder;
import com.xiaoyue.celestial_forge.content.modifier.ModifierInstance;
import com.xiaoyue.celestial_forge.data.CFLang;
import com.xiaoyue.celestial_forge.data.CFModConfig;
import com.xiaoyue.celestial_forge.register.CFItems;
import com.xiaoyue.celestial_forge.register.CFObjects;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;

import java.util.ArrayList;
import java.util.List;


public class ModifierBook extends Item {

	public ModifierBook() {
		super(new Properties().stacksTo(1).rarity(Rarity.UNCOMMON));
	}

	@Override
	public Component getName(ItemStack stack) {
		var mod = ModifierUtils.fromBook(stack);
		if (mod == null) return super.getName(stack);
		return CFLang.MODIFIER_BOOK.get(mod.getFormattedName());
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag tooltipFlag) {
		ModifierData data = ModifierData.getOrCreate(stack);
		var mod = ModifierUtils.fromBook(stack);
		if (mod != null) {
			list.addAll(ModifierInstance.of(mod).getInfoLines());
			list.add(CFLang.MODIFIER_BOOK_MODIFIER.get().withStyle(ChatFormatting.GRAY));
			list.add(CFLang.addModifierTypeTip(mod));
		} else {
			int minLv = CFModConfig.SERVER.modifierToBookLevel.get();
			list.add(CFLang.MODIFIER_BOOK_EMPTY.get(
					Component.literal("" + minLv).withStyle(ChatFormatting.AQUA)
			).withStyle(ChatFormatting.GRAY));
			if (!data.reinforced()) {
				list.add(CFLang.MODIFIER_BOOK_STORAGE_LEVEL.get(BuiltInRegistries.ITEM.get(
								ResourceLocation.parse(CFModConfig.SERVER.bookReinforcementMate.get())).getDescription().copy()
						.withStyle(ChatFormatting.AQUA)).withStyle(ChatFormatting.GRAY));
			}
		}
		if (data.reinforced()) {
			list.add(CFLang.MODIFIER_LEVEL.get(CFLang.num(data.level())).withStyle(ChatFormatting.GRAY));
		}
	}

	public static List<ItemStack> getStacksForCreativeTab() {
		List<ItemStack> stacks = new ArrayList<>();
		for (ModifierHolder mod : ModifierDataHolder.all()) {
			ItemStack stack = new ItemStack(CFItems.MODIFIER_BOOK.get());
			ModifierData data = new ModifierData("", 0, 0, mod.id().toString(), false);
			stack.set(CFObjects.MODIFIER_DATA, data);
			stacks.add(stack);
		}
		return stacks;
	}
}
