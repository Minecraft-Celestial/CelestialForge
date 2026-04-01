package com.xiaoyue.celestial_forge.data;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.content.modifier.ModifierHolder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;

public enum CFLang {
	CELESTIAL_MODIFIER("Celestial modifier: %s", 1),
	MODIFIER_BOOK("%s Modifier Book", 1),
	MODIFIER_BOOK_MODIFIER("Apply modifier to equipment on anvil", 0),
	MODIFIER_BOOK_EMPTY("To transfer modifier from equipment on anvil, equipment should be at least Lv.%s", 1),
	MODIFIER_BOOK_STORAGE_LEVEL("Consuming %s fusion on the anvil retains the extraction level", 1),
	APPLICABLE("Applicable to: %s", 1),
	EQUIPPED_TYPE("Armor", 0),
	HELMET_TYPE("Helmet", 0),
	CHESTPLATE_TYPE("Chestplate", 0),
	LEGGINGS_TYPE("Leggings", 0),
	BOOTS_TYPE("Boots", 0),
	TOOLS_TYPE("Tools", 0),
	RANGED_TYPE("Ranged", 0),
	CURIO_TYPE("Curio", 0),
	WEAPON_TYPE("Weapon", 0),
	ALL_TYPE("All", 0),
	TABLE_START("Place equipment to start", 0),
	TABLE_EXP_COST("Exp cost: %s", 1),
	TABLE_HAMMER("Right-click with a hammer to complete", 0),
	TABLE_INVALID_ITEM("Invalid item", 0),
	TABLE_MATERIAL("Material requirement:", 0),
	GRADE_PROGRESS("Current exp %s / %s", 2),
	IS_MAX_LEVEL("Maxed out", 0),
	NEED_UPGRADE("Put it on Forge Table for ascension", 0),
	MODIFIER_LEVEL("Modifier level: %s", 1),
	BREAK_SPEED("Mining speed increased by %s", 1),
	EXTRA_DAMAGE("Deals additional %s Abyssal Damage after attacking", 1),
	DAMAGE_HEAL("After attacking, restores attack damage %s health", 1),
	REF_TEXT("Forge with equipment on the anvil to give special effects to equipment", 0),
	ATTR_BONUS("%s increased by %s", 2),
	UNDEAD_EXTRA_DAMAGE("When attacking an undead creature, damage is increased by %s of the target's health", 1),
	PICK_EXP_BONUS("Increases the experience gained when picking up experience by %s", 1),
	REINFORCE_TITLE("Item Reinforce", 0),
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

	public static MutableComponent getType(CFLang type) {
		return APPLICABLE.get(type.get().withStyle(ChatFormatting.BLUE)).withStyle(ChatFormatting.GRAY);
	}

	public static ChatFormatting getColor(int weight) {
		ChatFormatting color = ChatFormatting.GREEN;
		if (weight < 300 && weight >= 200) {
			color = ChatFormatting.AQUA;
		} else if (weight < 200 && weight >= 100) {
			color = ChatFormatting.DARK_PURPLE;
		} else if (weight < 100 && weight >= 50) {
			color = ChatFormatting.YELLOW;
		} else if (weight < 50) {
			color = ChatFormatting.GOLD;
		}
        return color;
    }

	public static void addLang(RegistrateLangProvider pvd) {
		for (var value : values()) {
			pvd.add(value.id, value.def);
		}
	}

	public static MutableComponent per(double v) {
		return Component.literal((int)Math.round(v * (double)100.0F) + "%").withStyle(ChatFormatting.AQUA);
	}

	public static MutableComponent num(double v) {
		return Component.literal("" + v).withStyle(ChatFormatting.AQUA);
	}

	public static MutableComponent attr(@Nullable Attribute attr) {
		return attr == null ? Component.empty() : Component.translatable(attr.getDescriptionId()).withStyle(ChatFormatting.AQUA);
	}

	public static Component addModifierTypeTip(ModifierHolder modifier) {
		return switch (modifier.type()) {
			case WEAPON -> getType(WEAPON_TYPE);
			case RANGED -> getType(RANGED_TYPE);
            case HELMET -> getType(HELMET_TYPE);
            case CHESTPLATE -> getType(CHESTPLATE_TYPE);
            case LEGGINGS -> getType(LEGGINGS_TYPE);
            case BOOTS -> getType(BOOTS_TYPE);
            case TOOL -> getType(TOOLS_TYPE);
			case ARMOR -> getType(EQUIPPED_TYPE);
			case CURIO -> getType(CURIO_TYPE);
			case ALL -> getType(ALL_TYPE);
		};
	}

}
