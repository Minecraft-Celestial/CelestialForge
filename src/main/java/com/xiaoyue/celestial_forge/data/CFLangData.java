package com.xiaoyue.celestial_forge.data;

import com.tterrag.registrate.providers.RegistrateLangProvider;

public class CFLangData {

	public static void onLang(RegistrateLangProvider pvd) {
		pvd.add("base.celestial_forge.modifier_book", "%1$s Modifier Book");
		pvd.add("celestial_forge.tooltip.type.equipped", "§7Applicable to: §9Armor");
		pvd.add("celestial_forge.tooltip.type.tool", "§7Applicable to: §9Tools");
		pvd.add("celestial_forge.tooltip.type.ranged", "§7Applicable to: §9Ranged");
		pvd.add("celestial_forge.tooltip.type.curio", "§7Applicable to: §9Accessories");
		pvd.add("celestial_forge.tooltip.type.all", "§7Applicable to: §9All");
		pvd.add("celestial_forge.tooltip.modifier_book", "§7Use 22 levels of experience on the anvil to change the quality of the item");
	}

}
