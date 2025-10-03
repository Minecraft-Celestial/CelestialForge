package com.xiaoyue.celestial_forge.content.data;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.content.modifier.LevelingData;
import com.xiaoyue.celestial_forge.content.modifier.ModifierHolder;
import com.xiaoyue.celestial_forge.content.modifier.ModifierPool;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class DataHolder {

	private static DataHolder INS;

	public static void rebuild() {
		INS = new DataHolder();
	}

	@Nullable
	public static ModifierHolder byId(ResourceLocation id) {
		return INS == null ? null : INS.byIdCache.get(id);
	}

	public static ModifierPool byType(ModifierType type) {
		if (INS == null) throw new IllegalStateException("Cannot invoke byType before data is loaded");
		if (type == ModifierType.ALL) throw new IllegalStateException("byType cannot be called for ALL");
		return INS.byTypePool.get(type);
	}

	@Nullable
	public static UpgradeRecipe getStart(ModifierType type) {
		return INS == null ? null : INS.recipes.get(CelestialForge.loc("start/" + type.name().toLowerCase(Locale.ROOT)));
	}

	public static Collection<ModifierHolder> all() {
		return INS == null ? List.of() : INS.byIdCache.values();
	}

	private final Map<ResourceLocation, UpgradeRecipe> recipes = new LinkedHashMap<>();
	private final Map<ModifierType, ModifierPool> byTypePool = new LinkedHashMap<>();
	private final Map<ResourceLocation, ModifierHolder> byIdCache = new LinkedHashMap<>();

	private DataHolder() {
		// store upgrade recipes
		for (var e : CelestialForge.COST.getAll()) {
			ResourceLocation id = e.level == 0 ? e.target() : e.target().withSuffix("/" + e.level);
			recipes.put(id, e);
		}
		// compute sub categories, resolving duplicate
		Multimap<ResourceLocation, UpgradeRecipe> subRecipes = LinkedHashMultimap.create();
		for (var e : recipes.values()) {
			if (e.level > 0) {
				subRecipes.put(e.target(), e);
			}
		}
		// construct leveling
		Map<ResourceLocation, LevelingData> leveling = new LinkedHashMap<>();
		for (var e : CelestialForge.LEVELING.getAll()) {
			var list = subRecipes.get(e.upgrades());
			var map = new TreeMap<Integer, UpgradeRecipe>();
			for (var c : list) map.put(c.level, c);
			leveling.put(e.getID(), new LevelingData(e.baseCost(), e.expCost(), map));
		}
		Multimap<ModifierType, ModifierHolder> byTypeCache = LinkedHashMultimap.create();
		byTypePool.clear();
		byIdCache.clear();
		for (var e : CelestialForge.MODIFIER.getAll()) {
			var type = e.type();
			var lv = leveling.get(e.leveling());
			if (lv == null) {
				CelestialForge.LOGGER.error("Leveling data {} does not exist", e.leveling());
				continue;
			}
			var holder = new ModifierHolder(type, e.getID(), e, lv);
			byIdCache.put(e.getID(), holder);
			byTypeCache.put(type, holder);
		}
		var all = byTypeCache.get(ModifierType.ALL);
		for (var e : ModifierType.values()) {
			if (e == ModifierType.ALL) continue;
			var list = new ArrayList<ModifierHolder>();
			list.addAll(byTypeCache.get(e));
			list.addAll(all);
			byTypePool.put(e, ModifierPool.construct(e, list));
		}
	}

}
