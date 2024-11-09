package com.xiaoyue.celestial_forge.content.data;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.content.modifier.ModifierHolder;
import com.xiaoyue.celestial_forge.content.modifier.ModifierPool;
import com.xiaoyue.celestial_forge.content.registry.*;
import dev.xkmc.l2library.serial.config.BaseConfig;
import dev.xkmc.l2library.serial.config.CollectType;
import dev.xkmc.l2library.serial.config.ConfigCollect;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.jetbrains.annotations.Nullable;

import java.util.*;

@SerialClass
public class ModifierConfig extends BaseConfig {

	public static ModifierConfig getAll() {
		return CelestialForge.MODIFIER.getMerged();
	}

	@SerialClass.SerialField
	@ConfigCollect(CollectType.MAP_OVERWRITE)
	public final LinkedHashMap<ModifierType, DataModifierType> map = new LinkedHashMap<>();

	private final Map<ModifierType, ModifierPool> byTypePool = new LinkedHashMap<>();
	private final Map<ResourceLocation, ModifierHolder> byIdCache = new LinkedHashMap<>();

	@Override
	protected void postMerge() {
		Multimap<ModifierType, ModifierHolder> byTypeCache = LinkedHashMultimap.create();
		byTypePool.clear();
		byIdCache.clear();
		for (var typeData : map.entrySet()) {
			var type = typeData.getKey();
			for (var pool : typeData.getValue().pools()) {
				var gate = pool.gate();
				for (var entry : pool.entries().entrySet()) {
					var id = entry.getKey();
					var val = entry.getValue();
					var old = byIdCache.get(id);
					if (old != null) {
						CelestialForge.LOGGER.warn("id {} of type {} already exists. Ignore same modifier of type {}", id, old.type().name(), type.name());
					} else {
						var holder = new ModifierHolder(type, id, val, gate);
						byIdCache.put(id, holder);
						byTypeCache.put(type, holder);
					}
				}
			}
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

	public UpgradeRecipe getStart(ModifierType type) {
		return map.get(type).start();
	}

	public ModifierPool byType(ModifierType type) {
		if (type == ModifierType.ALL) throw new IllegalStateException("byType cannot be called for ALL");
		return byTypePool.get(type);
	}

	@Nullable
	public ModifierHolder byId(ResourceLocation type) {
		return byIdCache.get(type);
	}

	public Collection<ModifierHolder> all() {
		return byIdCache.values();
	}

	public TypeBuilder put(ModifierType type, UpgradeRecipe start) {
		return new TypeBuilder(type, start);
	}

	public class TypeBuilder {

		private final DataModifierType data;

		public TypeBuilder(ModifierType type, UpgradeRecipe gate) {
			data = map.computeIfAbsent(type, (k) -> new DataModifierType(gate, new ArrayList<>()));
		}

		public PoolBuilder put(LevelingConfig gate) {
			return new PoolBuilder(gate);
		}

		public ModifierConfig end() {
			return ModifierConfig.this;
		}


		public class PoolBuilder {

			private final DataModifierSet pool;

			public PoolBuilder(LevelingConfig gate) {
				pool = new DataModifierSet(new LinkedHashMap<>(), gate);
				data.pools().add(pool);
			}

			public PoolBuilder put(ResourceLocation id, int weight, Attribute attr, double base, AttributeModifier.Operation op) {
				return put(id, weight, new ModifierEntry(attr, base, op));
			}

			public PoolBuilder put(ResourceLocation id, int weight, ModifierEntry... entries) {
				CelestialForge.REGISTRATE.addRawLang("modifier." + id.getNamespace() + "." + id.getPath(), RegistrateLangProvider.toEnglishName(id.getPath()));
				pool.entries().put(id, new ModifierData(weight, new ArrayList<>(List.of(entries))));
				return this;
			}

			public TypeBuilder end() {
				return TypeBuilder.this;
			}

		}


	}

}
