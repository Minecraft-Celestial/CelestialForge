package com.xiaoyue.celestial_forge.content.modifier;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.xiaoyue.celestial_forge.CelestialForge;
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
	@ConfigCollect(CollectType.MAP_COLLECT)
	public final LinkedHashMap<ModifierType, LinkedHashMap<ResourceLocation, ModifierData>> map = new LinkedHashMap<>();

	private final Map<ModifierType, ModifierPool> byTypePool = new LinkedHashMap<>();
	private final Map<ResourceLocation, ModifierHolder> byIdCache = new LinkedHashMap<>();

	@SerialClass.OnInject
	public void onInject() {
		Multimap<ModifierType, ModifierHolder> byTypeCache = LinkedHashMultimap.create();
		byTypePool.clear();
		byIdCache.clear();
		for (var e1 : map.entrySet()) {
			var type = e1.getKey();
			for (var e2 : e1.getValue().entrySet()) {
				var id = e2.getKey();
				var val = e2.getValue();
				var old = byIdCache.get(id);
				if (old != null) {
					CelestialForge.LOGGER.warn("id {} of type {} already exists. Ignore same modifier of type {}", id, old.type().name(), type.name());
				} else {
					var holder = new ModifierHolder(type, id, val);
					byIdCache.put(id, holder);
					byTypeCache.put(type, holder);
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

	public ModifierConfig put(ModifierType type, ResourceLocation id, int weight, Attribute attr, double val, AttributeModifier.Operation op) {
		return put(type, id, weight, new ModifierEntry(attr, val, op));
	}


	public ModifierConfig put(ModifierType type, ResourceLocation id, int weight, ModifierEntry... entries) {
		CelestialForge.REGISTRATE.addRawLang("modifier." + id.getNamespace() + "." + id.getPath(), RegistrateLangProvider.toEnglishName(id.getPath()));
		map.computeIfAbsent(type, (k) -> new LinkedHashMap<>()).put(id, new ModifierData(weight, new ArrayList<>(List.of(entries))));
		return this;
	}
}
