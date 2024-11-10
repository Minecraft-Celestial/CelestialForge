package com.xiaoyue.celestial_forge.data;


import com.xiaoyue.celestial_core.register.CCAttributes;
import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.content.builder.DataBuilder;
import com.xiaoyue.celestial_forge.content.builder.UpgradeRecipeBuilder;
import com.xiaoyue.celestial_forge.content.data.ModifierEntry;
import com.xiaoyue.celestial_forge.content.data.ModifierType;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;

import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION;
import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.MULTIPLY_BASE;

public class CFConfigGen extends ConfigDataProvider {

	private static final DataBuilder CONFIG;

	static {
		CONFIG = new DataBuilder()
				.put(ModifierType.ARMOR, UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT))
				.put(CelestialForge.loc("armor_basic"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND),
						UpgradeRecipeBuilder.of(12, Items.TURTLE_HELMET),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP)
				)
				.put(CelestialForge.loc("hard"), 300, Attributes.ARMOR, 2, ADDITION)
				.put(CelestialForge.loc("guarding"), 200, Attributes.ARMOR, 0.05, MULTIPLY_BASE)
				.put(CelestialForge.loc("armor"), 100, Attributes.ARMOR_TOUGHNESS, 1, ADDITION)
				.put(CelestialForge.loc("protect"), 150, Attributes.ARMOR_TOUGHNESS, 0.05, MULTIPLY_BASE)
				.end()

				.put(CelestialForge.loc("armor_advanced"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND),
						UpgradeRecipeBuilder.of(12, Items.TURTLE_HELMET),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP)
				)
				.put(CelestialForge.loc("solid"), 50,
						new ModifierEntry(Attributes.ARMOR, 2, ADDITION),
						new ModifierEntry(Attributes.ARMOR_TOUGHNESS, 2, ADDITION),
						new ModifierEntry(Attributes.KNOCKBACK_RESISTANCE, 0.1, ADDITION))
				.end()
				.end()

				.put(ModifierType.TOOL, UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT))
				.put(CelestialForge.loc("tool_basic"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND),
						UpgradeRecipeBuilder.of(12, Items.CRYING_OBSIDIAN),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP)
				)
				.put(CelestialForge.loc("sawtooth"), 300, Attributes.ATTACK_DAMAGE, 1, ADDITION)
				.put(CelestialForge.loc("spine"), 200, Attributes.ATTACK_DAMAGE, 2, ADDITION)
				.put(CelestialForge.loc("dangerous"), 100, Attributes.ARMOR, 4, ADDITION)
				.end()

				.put(CelestialForge.loc("tool_advanced"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND),
						UpgradeRecipeBuilder.of(12, Items.CRYING_OBSIDIAN),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP)
				)
				.put(CelestialForge.loc("insane"), 100,
						new ModifierEntry(Attributes.ATTACK_DAMAGE, -0.05, MULTIPLY_BASE),
						new ModifierEntry(Attributes.ATTACK_SPEED, 0.1, MULTIPLY_BASE))
				.put(CelestialForge.loc("legend"), 50,
						new ModifierEntry(Attributes.ATTACK_DAMAGE, 0.1, MULTIPLY_BASE),
						new ModifierEntry(Attributes.ATTACK_SPEED, 0.1, MULTIPLY_BASE),
						new ModifierEntry(L2DamageTracker.CRIT_RATE.get(), 0.05, ADDITION))
				.end()
				.end()

				.put(ModifierType.RANGED, UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT))
				.put(CelestialForge.loc("ranged_basic"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND),
						UpgradeRecipeBuilder.of(12, Items.BLAZE_ROD),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP)
				)
				.put(CelestialForge.loc("strength"), 300, L2DamageTracker.BOW_STRENGTH.get(), 0.02, MULTIPLY_BASE)
				.put(CelestialForge.loc("fatal"), 150, L2DamageTracker.BOW_STRENGTH.get(), 0.04, MULTIPLY_BASE)
				.end()

				.put(CelestialForge.loc("ranged_advanced"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND),
						UpgradeRecipeBuilder.of(12, Items.BLAZE_ROD),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP)
				)
				.put(CelestialForge.loc("unreal"), 50,
						new ModifierEntry(L2DamageTracker.BOW_STRENGTH.get(), 0.1, MULTIPLY_BASE),
						new ModifierEntry(L2DamageTracker.CRIT_DMG.get(), 0.1, ADDITION),
						new ModifierEntry(CCAttributes.ARROW_SPEED.get(), 0.05, MULTIPLY_BASE))
				.end().end()

				.put(ModifierType.WEAPON, UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT))
				.put(CelestialForge.loc("weapon_basic"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND),
						UpgradeRecipeBuilder.of(12, Items.ECHO_SHARD),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP)
				)
				.put(CelestialForge.loc("accurate"), 300, L2DamageTracker.CRIT_RATE.get(), 0.02, ADDITION)
				.put(CelestialForge.loc("luck"), 150, L2DamageTracker.BOW_STRENGTH.get(), 0.04, MULTIPLY_BASE)
				.put(CelestialForge.loc("senior"), 300, L2DamageTracker.CRIT_DMG.get(), 0.02, ADDITION)
				.put(CelestialForge.loc("devil"), 150, L2DamageTracker.CRIT_DMG.get(), 0.04, ADDITION)
				.end()

				.put(CelestialForge.loc("weapon_advanced"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND),
						UpgradeRecipeBuilder.of(12, Items.ECHO_SHARD),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP)
				)
				.put(CelestialForge.loc("rude"), 75,
						new ModifierEntry(L2DamageTracker.CRIT_RATE.get(), -0.05, ADDITION),
						new ModifierEntry(L2DamageTracker.CRIT_DMG.get(), 0.1, ADDITION))
				.end()
				.end()

				.put(ModifierType.CURIO, UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT))
				.put(CelestialForge.loc("curio_basic"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND),
						UpgradeRecipeBuilder.of(12, Items.EMERALD_BLOCK),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP)
				)
				.put(CelestialForge.loc("rash"), 300, Attributes.ATTACK_SPEED, 0.02, MULTIPLY_BASE)
				.put(CelestialForge.loc("brave"), 150, Attributes.ATTACK_SPEED, 0.04, MULTIPLY_BASE)
				.end()

				.put(CelestialForge.loc("curio_advanced"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND),
						UpgradeRecipeBuilder.of(12, Items.EMERALD_BLOCK),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP)
				)
				.put(CelestialForge.loc("life"), 75,
						new ModifierEntry(Attributes.MAX_HEALTH, 2, ADDITION),
						new ModifierEntry(CCAttributes.REPLY_POWER.get(), 0.05, MULTIPLY_BASE))
				.put(CelestialForge.loc("anger"), 100,
						new ModifierEntry(Attributes.ATTACK_KNOCKBACK, 1, ADDITION),
						new ModifierEntry(CCAttributes.ARROW_KNOCK.get(), 1, ADDITION))
				.end()
				.end()

				.put(ModifierType.ALL, UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT))
				.put(CelestialForge.loc("general_basic"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND),
						UpgradeRecipeBuilder.of(12, Items.RABBIT_FOOT),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP)
				)
				.put(CelestialForge.loc("light"), 300, Attributes.MOVEMENT_SPEED, 0.02, MULTIPLY_BASE)
				.put(CelestialForge.loc("fast"), 150, Attributes.MOVEMENT_SPEED, 0.04, MULTIPLY_BASE)
				.end()
				.end();

	}

	public CFConfigGen(DataGenerator generator) {
		super(generator, "CelestialForge config provider");
	}

	@Override
	public void add(Collector collector) {
		CONFIG.build(collector);
	}

}
