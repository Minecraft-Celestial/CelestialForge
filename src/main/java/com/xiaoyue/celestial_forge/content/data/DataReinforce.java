package com.xiaoyue.celestial_forge.content.data;

import com.xiaoyue.celestial_forge.content.reinforce.AttributeEntry;
import com.xiaoyue.celestial_forge.content.reinforce.IReinforce;
import com.xiaoyue.celestial_forge.data.CFLang;
import com.xiaoyue.celestial_forge.register.CFFlags;
import dev.xkmc.l2library.serial.config.BaseConfig;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

@SerialClass
public class DataReinforce extends BaseConfig implements IReinforce {

	@SerialClass.SerialField
	private String flag;
	@SerialClass.SerialField
	private Ingredient mate;
	@SerialClass.SerialField
	private Ingredient temp;
	@SerialClass.SerialField
	private String tooltip;
	@SerialClass.SerialField
	private List<ModifierType> types = new ArrayList<>();
	@SerialClass.SerialField
	private List<AttributeEntry> attrs = new ArrayList<>();

	public DataReinforce(String flag, Ingredient temp, Ingredient mate, String tooltip, List<ModifierType> types, List<AttributeEntry> attrs) {
		this.flag = flag;
		this.temp = temp;
		this.mate = mate;
		this.tooltip = tooltip;
		this.types = types;
		this.attrs = attrs;
	}

	public DataReinforce() {

	}

	public String flagName() {
		return flag;
	}

	@Override
	public List<ModifierType> types() {
		return types;
	}

	@Override
	public String flag() {
		return CFFlags.flagName(flag);
	}

	@Override
	public Ingredient temp() {
		return temp;
	}

	@Override
	public Ingredient mate() {
		return mate;
	}

	@Override
	public List<Component> tooltip() {
		if (!tooltip.isEmpty()) {
			return List.of(Component.translatable(tooltip));
		}
		List<Component> list = new ArrayList<>();
		String key = ForgeRegistries.ITEMS.getKey(mate.getItems()[0].getItem()).toString();
		if (attrs.size() > 1) {
			list.add(CFFlags.item(key));
			for (AttributeEntry entry : attrs) {
				list.add(CFLang.ATTR_BONUS.get(CFLang.attr(entry.attr()), CFLang.per(entry.val()))
						.withStyle(ChatFormatting.GRAY));
			}
		} else {
			list.add(CFFlags.item(key, CFLang.ATTR_BONUS.get(CFLang.attr(attrs.get(0).attr()), CFLang.per(attrs.get(0).val()))
					.withStyle(ChatFormatting.GRAY)));
		}
		return list;
	}

	public List<AttributeEntry> attrs() {
		return attrs;
	}
}
