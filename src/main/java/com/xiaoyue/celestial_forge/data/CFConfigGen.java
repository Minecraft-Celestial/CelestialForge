package com.xiaoyue.celestial_forge.data;

import com.xiaoyue.celestial_core.register.CCAttributes;
import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.content.data.*;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION;
import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.MULTIPLY_BASE;

public class CFConfigGen extends ConfigDataProvider {

	private static final ModifierConfig CONFIG;

	static {
		CONFIG = new ModifierConfig()
				.put(ModifierType.ARMOR, new LevelGater(new ArrayList<>(List.of(
						new UpgradeRecipe(3, new ArrayList<>(List.of(Ingredient.of(Items.COPPER_INGOT)))),
						new UpgradeRecipe(5, new ArrayList<>(List.of(Ingredient.of(Items.IRON_INGOT)))),
						new UpgradeRecipe(8, new ArrayList<>(List.of(Ingredient.of(Items.DIAMOND)))),
						new UpgradeRecipe(12, new ArrayList<>(List.of(Ingredient.of(Items.TURTLE_HELMET)))),
						new UpgradeRecipe(17, new ArrayList<>(List.of(Ingredient.of(Items.NETHERITE_SCRAP))))
				))))
				.put(CelestialForge.loc("hard"), 300, Attributes.ARMOR, 2, ADDITION)
				.put(CelestialForge.loc("guarding"), 200, Attributes.ARMOR, 0.05, MULTIPLY_BASE)
				.put(CelestialForge.loc("armor"), 100, Attributes.ARMOR_TOUGHNESS, 1, ADDITION)
				.put(CelestialForge.loc("protect"), 150, Attributes.ARMOR_TOUGHNESS, 0.05, MULTIPLY_BASE)
				.put(CelestialForge.loc("solid"), 50,
						new ModifierEntry(Attributes.ARMOR, 2, ADDITION),
						new ModifierEntry(Attributes.ARMOR_TOUGHNESS, 2, ADDITION),
						new ModifierEntry(Attributes.KNOCKBACK_RESISTANCE, 0.1, ADDITION))
				.end()

				.put(ModifierType.TOOL, new LevelGater(new ArrayList<>(List.of(
						new UpgradeRecipe(3, new ArrayList<>(List.of(Ingredient.of(Items.COPPER_INGOT)))),
						new UpgradeRecipe(5, new ArrayList<>(List.of(Ingredient.of(Items.IRON_INGOT)))),
						new UpgradeRecipe(8, new ArrayList<>(List.of(Ingredient.of(Items.DIAMOND)))),
						new UpgradeRecipe(12, new ArrayList<>(List.of(Ingredient.of(Items.CRYING_OBSIDIAN)))),
						new UpgradeRecipe(17, new ArrayList<>(List.of(Ingredient.of(Items.NETHERITE_SCRAP))))
				))))
				.put(CelestialForge.loc("sawtooth"), 300, Attributes.ATTACK_DAMAGE, 1, ADDITION)
				.put(CelestialForge.loc("spine"), 200, Attributes.ATTACK_DAMAGE, 2, ADDITION)
				.put(CelestialForge.loc("dangerous"), 100, Attributes.ARMOR, 4, ADDITION)
				.put(CelestialForge.loc("insane"), 100,
						new ModifierEntry(Attributes.ATTACK_DAMAGE, -0.05, MULTIPLY_BASE),
						new ModifierEntry(Attributes.ATTACK_SPEED, 0.1, MULTIPLY_BASE))
				.put(CelestialForge.loc("legend"), 50,
						new ModifierEntry(Attributes.ATTACK_DAMAGE, 0.1, MULTIPLY_BASE),
						new ModifierEntry(Attributes.ATTACK_SPEED, 0.1, MULTIPLY_BASE),
						new ModifierEntry(L2DamageTracker.CRIT_RATE.get(), 0.05, ADDITION))
				.end()

				.put(ModifierType.RANGED, new LevelGater(new ArrayList<>(List.of(
						new UpgradeRecipe(3, new ArrayList<>(List.of(Ingredient.of(Items.COPPER_INGOT)))),
						new UpgradeRecipe(5, new ArrayList<>(List.of(Ingredient.of(Items.IRON_INGOT)))),
						new UpgradeRecipe(8, new ArrayList<>(List.of(Ingredient.of(Items.DIAMOND)))),
						new UpgradeRecipe(12, new ArrayList<>(List.of(Ingredient.of(Items.BLAZE_ROD)))),
						new UpgradeRecipe(17, new ArrayList<>(List.of(Ingredient.of(Items.NETHERITE_SCRAP))))
				))))
				.put(CelestialForge.loc("strength"), 300, L2DamageTracker.BOW_STRENGTH.get(), 0.02, MULTIPLY_BASE)
				.put(CelestialForge.loc("fatal"), 150, L2DamageTracker.BOW_STRENGTH.get(), 0.04, MULTIPLY_BASE)
				.put(CelestialForge.loc("unreal"), 50,
						new ModifierEntry(L2DamageTracker.BOW_STRENGTH.get(), 0.1, MULTIPLY_BASE),
						new ModifierEntry(L2DamageTracker.CRIT_DMG.get(), 0.1, ADDITION),
						new ModifierEntry(CCAttributes.ARROW_SPEED.get(), 0.05, MULTIPLY_BASE))
				.end()

				.put(ModifierType.WEAPON, new LevelGater(new ArrayList<>(List.of(
						new UpgradeRecipe(3, new ArrayList<>(List.of(Ingredient.of(Items.COPPER_INGOT)))),
						new UpgradeRecipe(5, new ArrayList<>(List.of(Ingredient.of(Items.IRON_INGOT)))),
						new UpgradeRecipe(8, new ArrayList<>(List.of(Ingredient.of(Items.DIAMOND)))),
						new UpgradeRecipe(12, new ArrayList<>(List.of(Ingredient.of(Items.ECHO_SHARD)))),
						new UpgradeRecipe(17, new ArrayList<>(List.of(Ingredient.of(Items.NETHERITE_SCRAP))))
				))))
				.put(CelestialForge.loc("accurate"), 300, L2DamageTracker.CRIT_RATE.get(), 0.02, ADDITION)
				.put(CelestialForge.loc("luck"), 150, L2DamageTracker.BOW_STRENGTH.get(), 0.04, MULTIPLY_BASE)
				.put(CelestialForge.loc("senior"), 300, L2DamageTracker.CRIT_DMG.get(), 0.02, ADDITION)
				.put(CelestialForge.loc("devil"), 150, L2DamageTracker.CRIT_DMG.get(), 0.04, ADDITION)
				.put(CelestialForge.loc("rude"), 75,
						new ModifierEntry(L2DamageTracker.CRIT_RATE.get(), -0.05, ADDITION),
						new ModifierEntry(L2DamageTracker.CRIT_DMG.get(), 0.1, ADDITION))
				.end()

				.put(ModifierType.CURIO, new LevelGater(new ArrayList<>(List.of(
						new UpgradeRecipe(3, new ArrayList<>(List.of(Ingredient.of(Items.COPPER_INGOT)))),
						new UpgradeRecipe(5, new ArrayList<>(List.of(Ingredient.of(Items.IRON_INGOT)))),
						new UpgradeRecipe(8, new ArrayList<>(List.of(Ingredient.of(Items.DIAMOND)))),
						new UpgradeRecipe(12, new ArrayList<>(List.of(Ingredient.of(Items.EMERALD_BLOCK)))),
						new UpgradeRecipe(17, new ArrayList<>(List.of(Ingredient.of(Items.NETHERITE_SCRAP))))
				))))
				.put(CelestialForge.loc("rash"), 300, Attributes.ATTACK_SPEED, 0.02, MULTIPLY_BASE)
				.put(CelestialForge.loc("brave"), 150, Attributes.ATTACK_SPEED, 0.04, MULTIPLY_BASE)
				.put(CelestialForge.loc("life"), 75,
						new ModifierEntry(Attributes.MAX_HEALTH, 2, ADDITION),
						new ModifierEntry(CCAttributes.REPLY_POWER.get(), 0.05, MULTIPLY_BASE))
				.put(CelestialForge.loc("anger"), 100,
						new ModifierEntry(Attributes.ATTACK_KNOCKBACK, 1, ADDITION),
						new ModifierEntry(CCAttributes.ARROW_KNOCK.get(), 1, ADDITION))
				.end()

				.put(ModifierType.ALL, new LevelGater(new ArrayList<>(List.of(
						new UpgradeRecipe(3, new ArrayList<>(List.of(Ingredient.of(Items.COPPER_INGOT)))),
						new UpgradeRecipe(5, new ArrayList<>(List.of(Ingredient.of(Items.IRON_INGOT)))),
						new UpgradeRecipe(8, new ArrayList<>(List.of(Ingredient.of(Items.DIAMOND)))),
						new UpgradeRecipe(12, new ArrayList<>(List.of(Ingredient.of(Items.RABBIT_FOOT)))),
						new UpgradeRecipe(17, new ArrayList<>(List.of(Ingredient.of(Items.NETHERITE_SCRAP))))
				))))
				.put(CelestialForge.loc("light"), 300, Attributes.MOVEMENT_SPEED, 0.02, MULTIPLY_BASE)
				.put(CelestialForge.loc("fast"), 150, Attributes.MOVEMENT_SPEED, 0.04, MULTIPLY_BASE)
				.end();

	}

	public CFConfigGen(DataGenerator generator) {
		super(generator, "Default modifiers");
	}

	@Override
	public void add(Collector collector) {
		collector.add(CelestialForge.MODIFIER, CelestialForge.loc("default"), CONFIG);
	}
}
