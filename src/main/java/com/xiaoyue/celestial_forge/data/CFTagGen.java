package com.xiaoyue.celestial_forge.data;

import com.tterrag.registrate.providers.RegistrateItemTagsProvider;
import com.xiaoyue.celestial_forge.CelestialForge;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

public class CFTagGen {

	public static final TagKey<Item> BLACK_LIST = ItemTags.create(CelestialForge.loc("black_list"));

	public static final TagKey<Item> TOOL_MODIFIABLE = ItemTags.create(CelestialForge.loc("tool_modifiable"));
	public static final TagKey<Item> RANGED_MODIFIABLE = ItemTags.create(CelestialForge.loc("ranged_modifiable"));
	public static final TagKey<Item> WEAPON_MODIFIABLE = ItemTags.create(CelestialForge.loc("weapon_modifiable"));
	public static final TagKey<Item> ARMOR_MODIFIABLE = ItemTags.create(CelestialForge.loc("armor_modifiable"));
	public static final TagKey<Item> CURIO_MODIFIABLE = ItemTags.create(CelestialForge.loc("curio_modifiable"));

	@SuppressWarnings("unchecked")
	public static void onItemTagGen(RegistrateItemTagsProvider pvd) {
		pvd.addTag(WEAPON_MODIFIABLE).add(Items.TRIDENT)
				.addTags(ItemTags.SWORDS, ItemTags.AXES);
		pvd.addTag(TOOL_MODIFIABLE)
				.addTags(ItemTags.PICKAXES, ItemTags.AXES, ItemTags.SHOVELS, ItemTags.HOES, Tags.Items.TOOLS_FISHING_RODS);
		pvd.addTag(ARMOR_MODIFIABLE)
				.addTags(Tags.Items.ARMORS);
		pvd.addTag(RANGED_MODIFIABLE)
				.addTags(Tags.Items.TOOLS_BOWS, Tags.Items.TOOLS_CROSSBOWS);
		pvd.addTag(CURIO_MODIFIABLE);
	}
}
