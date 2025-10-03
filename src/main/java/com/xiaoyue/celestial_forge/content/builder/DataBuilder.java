package com.xiaoyue.celestial_forge.content.builder;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.content.data.*;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.*;

public class DataBuilder {

	private final Map<ResourceLocation, UpgradeRecipe> costs = new LinkedHashMap<>();
	private final Map<ResourceLocation, LevelingConfig> levels = new LinkedHashMap<>();
	private final Map<ResourceLocation, ModifierData> modifiers = new LinkedHashMap<>();

	public TypeBuilder put(ModifierType type, UpgradeRecipeBuilder start) {
		return new TypeBuilder(type, start);
	}

	public void build(ConfigDataProvider.Collector collector) {
		for (var ent : costs.entrySet()) {
			collector.add(CelestialForge.COST, ent.getKey(), ent.getValue());
		}
		for (var ent : levels.entrySet()) {
			collector.add(CelestialForge.LEVELING, ent.getKey(), ent.getValue());
		}
		for (var ent : modifiers.entrySet()) {
			collector.add(CelestialForge.MODIFIER, ent.getKey(), ent.getValue());
		}
	}

	public class TypeBuilder {

		private final ModifierType type;

		public TypeBuilder(ModifierType type, UpgradeRecipeBuilder gate) {
			this.type = type;
			var id = CelestialForge.loc("start/" + type.name().toLowerCase(Locale.ROOT));
			costs.put(id, gate.build(id, 0));
		}

		public PoolBuilder put(ResourceLocation id, UpgradeRecipeBuilder... builders) {
			return put(id, 125, 0.1, builders);
		}

		public PoolBuilder put(ResourceLocation id, int baseCost, double expCost, UpgradeRecipeBuilder... builders) {
			int lv = 10;
			for (var e : builders) {
				var ans = e.build(id, lv);
				costs.put(e.loc(id, lv), ans);
				lv += 10;
			}
			return new PoolBuilder(new LevelingConfig(baseCost, expCost, id));
		}

		public DataBuilder end() {
			return DataBuilder.this;
		}


		public class PoolBuilder {

			private final ResourceLocation level;

			public PoolBuilder(LevelingConfig config) {
				this.level = config.upgrades();
				levels.put(level, config);
			}

			public PoolBuilder put(ResourceLocation id, int weight, Attribute attr, double base, AttributeModifier.Operation op) {
				return put(id, weight, new ModifierEntry(attr, base, op));
			}

			public PoolBuilder put(ResourceLocation id, int weight, ModifierEntry... entries) {
				CelestialForge.REGISTRATE.addRawLang("modifier." + id.getNamespace() + "." + id.getPath(), RegistrateLangProvider.toEnglishName(id.getPath()));
				modifiers.put(id, new ModifierData(type, weight, new ArrayList<>(List.of(entries)), level));
				return this;
			}

			public TypeBuilder end() {
				return TypeBuilder.this;
			}

		}


	}


}
