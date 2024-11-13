package com.xiaoyue.celestial_forge.data;

import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.xiaoyue.celestial_forge.CelestialForge;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class CFTagGen {

	public static final TagKey<Item> BLACK_LIST = ItemTags.create(CelestialForge.loc("black_list"));

	public static final TagKey<Item> TOOL_MODIFIABLE = ItemTags.create(CelestialForge.loc("tool_modifiable"));
	public static final TagKey<Item> RANGED_MODIFIABLE = ItemTags.create(CelestialForge.loc("ranged_modifiable"));
	public static final TagKey<Item> WEAPON_MODIFIABLE = ItemTags.create(CelestialForge.loc("weapon_modifiable"));
	public static final TagKey<Item> ARMOR_MODIFIABLE = ItemTags.create(CelestialForge.loc("armor_modifiable"));
	public static final TagKey<Item> CURIO_MODIFIABLE = ItemTags.create(CelestialForge.loc("curio_modifiable"));

	public static void onItemTagGen(RegistrateItemTagsProvider pvd) {
		pvd.addTag(WEAPON_MODIFIABLE).add(Items.TRIDENT);
	}
}
