package com.xiaoyue.celestial_forge.data;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.content.modifier.ModifierHolder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.Locale;

public enum CFLang {
	CELESTIAL_MODIFIER("Celestial modifier: %s", 1),
	MODIFIER_BOOK("%s Modifier Book", 1),
	MODIFIER_BOOK_MODIFIER("Apply modifier to equipment on anvil", 0),
	MODIFIER_BOOK_EMPTY("To transfer modifier from equipment on anvil, equipment should be at least Lv.%s", 1),
	EQUIPPED_TYPE("§7Applicable to: §9Armor", 0),
	TOOLS_TYPE("§7Applicable to: §9Tools", 0),
	RANGED_TYPE("§7Applicable to: §9Ranged", 0),
	CURIO_TYPE("§7Applicable to: §9Curio", 0),
	WEAPON_TYPE("§7Applicable to: §9Weapon", 0),
	ALL_TYPE("§7Applicable to: §9All", 0),
	TABLE_START("Place equipment to start", 0),
	TABLE_EXP_COST("Exp cost: %s", 1),
	TABLE_HAMMER("Right-click with a hammer to complete", 0),
	TABLE_INVALID_ITEM("Invalid item", 0),
	TABLE_MATERIAL("Material requirement:", 0),
	GRADE_PROGRESS("Current exp %s / %s", 2),
	IS_MAX_LEVEL("Maxed out", 0),
	NEED_UPGRADE("Put it on Forge Table for ascension", 0),
	MODIFIER_LEVEL("Modifier level: %s", 1),
	;

	final String id;
	final String def;
	final int count;

	CFLang(String def, int count) {
		this.id = CelestialForge.MODID + ".tooltip." + name().toLowerCase(Locale.ROOT);
		this.def = def;
		this.count = count;
	}

	public MutableComponent get(Object... objs) {
		if (objs.length != this.count) {
			throw new IllegalArgumentException("for " + name() + ": expect " + count + " parameters, got " + objs.length);
		} else {
			return Component.translatable(id, objs);
		}
	}

	public static void addLang(RegistrateLangProvider pvd) {
		for (var value : values()) {
			pvd.add(value.id, value.def);
		}
	}

	public static Component addModifierTypeTip(ModifierHolder modifier) {
		return switch (modifier.type()) {
			case WEAPON -> WEAPON_TYPE.get();
			case RANGED -> RANGED_TYPE.get();
			case TOOL -> TOOLS_TYPE.get();
			case ARMOR -> EQUIPPED_TYPE.get();
			case CURIO -> CURIO_TYPE.get();
			case ALL -> ALL_TYPE.get();
		};
	}

}
