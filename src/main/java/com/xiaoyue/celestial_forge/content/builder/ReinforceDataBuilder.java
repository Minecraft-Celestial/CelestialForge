package com.xiaoyue.celestial_forge.content.builder;

import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.content.data.DataReinforce;
import com.xiaoyue.celestial_forge.content.data.ModifierType;
import com.xiaoyue.celestial_forge.content.reinforce.AttributeEntry;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;

import java.util.*;

public class ReinforceDataBuilder {

	private final Map<ResourceLocation, DataReinforce> cache = new LinkedHashMap<>();

	public Builder builder(String flag) {
		return new Builder(flag);
	}

	public void build(ConfigDataProvider.Collector collector) {
		for (var entry : cache.entrySet()) {
			collector.add(CelestialForge.REINFORCE, entry.getKey(), entry.getValue());
		}
	}

	public class Builder {

		private final String flag;
		private Ingredient mate, temp;
		private final List<ModifierType> types = new ArrayList<>();
		private String tooltip = "";
		private final List<AttributeEntry> attrs = new ArrayList<>();

		public Builder(String flag) {
			this.flag = flag;
		}

		public Builder mate(ItemLike item) {
			mate = Ingredient.of(item);
			return this;
		}

		public Builder temp(ItemLike item) {
			temp = Ingredient.of(item);
			return this;
		}

		public Builder mate(TagKey<Item> item) {
			mate = Ingredient.of(item);
			return this;
		}

		public Builder temp(TagKey<Item> item) {
			temp = Ingredient.of(item);
			return this;
		}

		public Builder type(ModifierType type) {
			this.types.add(type);
			return this;
		}

		public Builder tooltip(String tooltip) {
			this.tooltip = tooltip;
			return this;
		}

		public Builder attr(Attribute attr, double val, AttributeModifier.Operation operation) {
			this.attrs.add(new AttributeEntry(attr, val, operation));
			return this;
		}

		public ReinforceDataBuilder build() {
			DataReinforce reinforce = new DataReinforce(flag, temp, mate, tooltip, types, attrs);
			cache.put(CelestialForge.loc(reinforce.flagName().toLowerCase(Locale.ROOT)), reinforce);
			return ReinforceDataBuilder.this;
		}
	}
}
