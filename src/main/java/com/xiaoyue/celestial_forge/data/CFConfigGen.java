package com.xiaoyue.celestial_forge.data;

import com.xiaoyue.celestial_core.CelestialCore;
import com.xiaoyue.celestial_core.register.CCAttributes;
import com.xiaoyue.celestial_core.register.CCItems;
import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.content.builder.ModifierDataBuilder;
import com.xiaoyue.celestial_forge.content.builder.ReinforceDataBuilder;
import com.xiaoyue.celestial_forge.content.builder.UpgradeRecipeBuilder;
import com.xiaoyue.celestial_forge.content.data.ModifierEntry;
import com.xiaoyue.celestial_forge.content.data.ModifierType;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeMod;

import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.*;

public class CFConfigGen extends ConfigDataProvider {

	private static final ModifierDataBuilder MODIFIER_CONFIG;
	private static final ReinforceDataBuilder REINFORCE_CONFIG;

	static {
		MODIFIER_CONFIG = new ModifierDataBuilder()
				.put(ModifierType.ARMOR, UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT, Items.IRON_INGOT, Items.GOLD_NUGGET))
				.put(CelestialForge.loc("armor_basic"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT, Items.LEATHER, Items.BRICK),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT, Items.IRON_BOOTS, Items.REDSTONE, Items.BOOK),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND, Items.GOLD_INGOT, Items.OBSERVER, Items.COPPER_BLOCK),
						UpgradeRecipeBuilder.of(12, Items.TURTLE_HELMET, Items.ENCHANTED_BOOK, Items.CRYING_OBSIDIAN, Items.SCUTE, Items.GOLDEN_CHESTPLATE),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP, Items.EMERALD, Items.DIAMOND_HELMET, Items.RABBIT_HIDE, Items.PRISMARINE),
						UpgradeRecipeBuilder.of(23, Items.NETHERITE_INGOT, CCItems.TREASURE_FRAGMENT.get(), CCItems.VIRTUAL_GOLD_NUGGET.get()),
						UpgradeRecipeBuilder.of(30, CCItems.DEATH_ESSENCE.get(), CCItems.FIRE_ESSENCE.asItem(), Items.MAGMA_BLOCK, Items.END_STONE),
						UpgradeRecipeBuilder.of(38, Items.SHULKER_SHELL, CCItems.VIRTUAL_GOLD_INGOT.get(), CCItems.OCEAN_ESSENCE.get(), CCItems.WARDEN_SCLERITE.get()),
						UpgradeRecipeBuilder.of(47, Items.NETHERITE_SHOVEL, CCItems.SHULKER_SCRAP.get(), CCItems.LIGHT_FRAGMENT.get(), Items.DRAGON_BREATH),
						UpgradeRecipeBuilder.of(57, CCItems.HEART_FRAGMENT.get(), CCItems.EARTH_CORE.get(), Items.END_ROD, Items.TOTEM_OF_UNDYING)
				)
				.put(CelestialForge.loc("hard"), 300, Attributes.ARMOR, 2, ADDITION)
				.put(CelestialForge.loc("guarding"), 200, Attributes.ARMOR, 0.05, MULTIPLY_BASE)
				.put(CelestialForge.loc("armor"), 100, Attributes.ARMOR_TOUGHNESS, 1, ADDITION)
				.put(CelestialForge.loc("protect"), 150, Attributes.ARMOR_TOUGHNESS, 0.05, MULTIPLY_BASE)
				.put(CelestialCore.loc("regenerate"), 150, CCAttributes.REPLY_POWER.get(), 0.05, MULTIPLY_BASE)
				.end()

				.put(CelestialForge.loc("armor_advanced"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.LEATHER, Items.BRICK),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT, Items.IRON_INGOT, Items.IRON_LEGGINGS, Items.REDSTONE, Items.BOOK),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND, Items.DIAMOND, Items.GOLD_INGOT, Items.OBSERVER, Items.COPPER_BLOCK),
						UpgradeRecipeBuilder.of(12, Items.TURTLE_HELMET, Items.EXPERIENCE_BOTTLE, Items.ENCHANTED_BOOK, Items.CRYING_OBSIDIAN, Items.SCUTE, Items.GOLDEN_CHESTPLATE),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP, Items.NETHERITE_SCRAP, Items.EMERALD, Items.DIAMOND_HELMET, Items.RABBIT_HIDE, Items.PRISMARINE, Items.EXPERIENCE_BOTTLE),
						UpgradeRecipeBuilder.of(23, Items.NETHERITE_INGOT, CCItems.TREASURE_FRAGMENT.get(), CCItems.VIRTUAL_GOLD_NUGGET.get()),
						UpgradeRecipeBuilder.of(30, CCItems.DEATH_ESSENCE.get(), CCItems.FIRE_ESSENCE.asItem(), Items.MAGMA_BLOCK, Items.END_STONE),
						UpgradeRecipeBuilder.of(38, Items.SHULKER_SHELL, CCItems.VIRTUAL_GOLD_INGOT.get(), CCItems.OCEAN_ESSENCE.get(), CCItems.WARDEN_SCLERITE.get()),
						UpgradeRecipeBuilder.of(47, Items.NETHERITE_SHOVEL, CCItems.SHULKER_SCRAP.get(), CCItems.MIDNIGHT_FRAGMENT.get(), Items.DRAGON_BREATH),
						UpgradeRecipeBuilder.of(57, CCItems.HEART_FRAGMENT.get(), CCItems.EARTH_CORE.get(), Items.END_ROD, Items.TOTEM_OF_UNDYING)
				)
				.put(CelestialForge.loc("solid"), 50,
						new ModifierEntry(Attributes.ARMOR, 2, ADDITION),
						new ModifierEntry(Attributes.ARMOR_TOUGHNESS, 2, ADDITION),
						new ModifierEntry(Attributes.KNOCKBACK_RESISTANCE, 0.1, ADDITION))
				.end()
				.end()

				.put(ModifierType.TOOL, UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT, Items.IRON_INGOT, Items.DEEPSLATE))
				.put(CelestialForge.loc("tool_basic"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT, Items.GOLD_INGOT, Items.COAL),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT, Items.GOLDEN_SHOVEL, Items.LAPIS_LAZULI, Items.PAPER),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND, Items.EMERALD, Items.BLAZE_ROD, Items.AMETHYST_SHARD),
						UpgradeRecipeBuilder.of(12, Items.CRYING_OBSIDIAN, Items.GOLDEN_PICKAXE, Items.EXPERIENCE_BOTTLE, Items.ENCHANTED_BOOK),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP, Items.DIAMOND_HOE, Items.EMERALD, Items.TRIDENT, Items.NAUTILUS_SHELL, Items.NETHER_BRICK),
						UpgradeRecipeBuilder.of(23, Items.NETHERITE_INGOT, CCItems.TREASURE_FRAGMENT.get(), CCItems.VIRTUAL_GOLD_NUGGET.get()),
						UpgradeRecipeBuilder.of(30, CCItems.DEATH_ESSENCE.get(), CCItems.FIRE_ESSENCE.asItem(), Items.MAGMA_BLOCK, Items.END_STONE),
						UpgradeRecipeBuilder.of(38, Items.SHULKER_SHELL, CCItems.VIRTUAL_GOLD_INGOT.get(), CCItems.GUARDIAN_SPIKE.get(), CCItems.WARDEN_SCLERITE.get()),
						UpgradeRecipeBuilder.of(47, Items.NETHERITE_SHOVEL, CCItems.SHULKER_SCRAP.get(), CCItems.LIGHT_FRAGMENT.get(), Items.DRAGON_BREATH),
						UpgradeRecipeBuilder.of(57, CCItems.GUARDIAN_OCEAN_INGOT.get(), CCItems.EARTH_CORE.get(), Items.END_ROD, Items.TOTEM_OF_UNDYING)
				)
				.put(CelestialForge.loc("luck"), 100, Attributes.LUCK, 1, ADDITION)
				.end()

				.put(CelestialForge.loc("tool_advanced"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.GOLD_INGOT, Items.COAL),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT, Items.GOLD_INGOT, Items.GOLDEN_AXE, Items.LAPIS_LAZULI, Items.PAPER),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND, Items.DIAMOND, Items.EMERALD, Items.BLAZE_ROD, Items.AMETHYST_SHARD),
						UpgradeRecipeBuilder.of(12, Items.CRYING_OBSIDIAN, Items.GOLDEN_APPLE, Items.GOLDEN_PICKAXE, Items.EXPERIENCE_BOTTLE, Items.ENCHANTED_BOOK),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP, Items.NETHERITE_SCRAP, Items.DIAMOND_HOE, Items.EMERALD, Items.TRIDENT, Items.NAUTILUS_SHELL, Items.NETHER_BRICK),
						UpgradeRecipeBuilder.of(23, Items.NETHERITE_INGOT, CCItems.TREASURE_FRAGMENT.get(), CCItems.VIRTUAL_GOLD_NUGGET.get()),
						UpgradeRecipeBuilder.of(30, CCItems.DEATH_ESSENCE.get(), CCItems.FIRE_ESSENCE.asItem(), Items.MAGMA_BLOCK, Items.END_STONE),
						UpgradeRecipeBuilder.of(38, Items.SHULKER_SHELL, CCItems.VIRTUAL_GOLD_INGOT.get(), CCItems.OCEAN_ESSENCE.get(), CCItems.WARDEN_SCLERITE.get()),
						UpgradeRecipeBuilder.of(47, Items.NETHERITE_SHOVEL, CCItems.SHULKER_SCRAP.get(), CCItems.MIDNIGHT_FRAGMENT.get(), Items.DRAGON_BREATH),
						UpgradeRecipeBuilder.of(57, CCItems.HEART_FRAGMENT.get(), CCItems.EARTH_CORE.get(), Items.END_ROD, Items.TOTEM_OF_UNDYING)
				)
				.end()
				.end()

				.put(ModifierType.RANGED, UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT, Items.FLINT, Items.VINE))
				.put(CelestialForge.loc("ranged_basic"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT, Items.BONE, Items.STRING),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT, Items.GOLD_INGOT, Items.BOW, Items.FEATHER, Items.QUARTZ),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND, Items.FIREWORK_STAR, Items.AMETHYST_SHARD, Items.CROSSBOW),
						UpgradeRecipeBuilder.of(12, Items.BLAZE_ROD, Items.ENCHANTED_BOOK, Items.SPYGLASS, Items.WITHER_SKELETON_SKULL, Items.COMPASS),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP, Items.NETHERITE_SCRAP, Items.TRIDENT, Items.EXPERIENCE_BOTTLE, Items.RECOVERY_COMPASS, Items.GHAST_TEAR),
						UpgradeRecipeBuilder.of(23, Items.NETHERITE_INGOT, CCItems.TREASURE_FRAGMENT.get(), CCItems.VIRTUAL_GOLD_NUGGET.get()),
						UpgradeRecipeBuilder.of(30, CCItems.DEATH_ESSENCE.get(), CCItems.FIRE_ESSENCE.asItem(), Items.MAGMA_BLOCK, Items.END_STONE),
						UpgradeRecipeBuilder.of(38, Items.SHULKER_SHELL, CCItems.VIRTUAL_GOLD_INGOT.get(), CCItems.OCEAN_ESSENCE.get(), CCItems.WARDEN_SCLERITE.get()),
						UpgradeRecipeBuilder.of(47, Items.NETHERITE_SHOVEL, CCItems.SHULKER_SCRAP.get(), CCItems.LIGHT_FRAGMENT.get(), Items.DRAGON_BREATH),
						UpgradeRecipeBuilder.of(57, CCItems.SOARING_WINGS.get(), CCItems.EARTH_CORE.get(), Items.END_ROD, Items.TOTEM_OF_UNDYING)
				)
				.put(CelestialForge.loc("strength"), 150, L2DamageTracker.BOW_STRENGTH.get(), 0.05, ADDITION)
				.put(CelestialForge.loc("fatal"), 150, L2DamageTracker.BOW_STRENGTH.get(), 0.05, MULTIPLY_BASE)
				.end()

				.put(CelestialForge.loc("ranged_advanced"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.BOW, Items.STRING),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT, Items.PAPER, Items.GOLD_INGOT, Items.CROSSBOW, Items.FEATHER, Items.QUARTZ),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND, Items.REDSTONE, Items.FIREWORK_STAR, Items.AMETHYST_SHARD, Items.CROSSBOW),
						UpgradeRecipeBuilder.of(12, Items.BLAZE_ROD, Items.BLAZE_POWDER, Items.ENCHANTED_BOOK, Items.SPYGLASS, Items.WITHER_SKELETON_SKULL, Items.COMPASS),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP, Items.NETHERITE_SCRAP, Items.TRIDENT, Items.EXPERIENCE_BOTTLE, Items.RECOVERY_COMPASS, Items.GHAST_TEAR),
						UpgradeRecipeBuilder.of(23, Items.NETHERITE_INGOT, CCItems.TREASURE_FRAGMENT.get(), CCItems.VIRTUAL_GOLD_NUGGET.get()),
						UpgradeRecipeBuilder.of(30, CCItems.DEATH_ESSENCE.get(), CCItems.FIRE_ESSENCE.asItem(), Items.DIAMOND_BLOCK, Items.END_STONE),
						UpgradeRecipeBuilder.of(38, Items.SHULKER_SHELL, CCItems.VIRTUAL_GOLD_INGOT.get(), CCItems.OCEAN_ESSENCE.get(), CCItems.WARDEN_SCLERITE.get()),
						UpgradeRecipeBuilder.of(47, Items.NETHERITE_SHOVEL, CCItems.SHULKER_SCRAP.get(), CCItems.LIGHT_FRAGMENT.get(), Items.DRAGON_BREATH),
						UpgradeRecipeBuilder.of(57, CCItems.SOARING_WINGS.get(), CCItems.EARTH_CORE.get(), Items.END_ROD, Items.TOTEM_OF_UNDYING)
				)
				.put(CelestialForge.loc("unreal"), 50,
						new ModifierEntry(L2DamageTracker.BOW_STRENGTH.get(), 0.1, MULTIPLY_BASE),
						new ModifierEntry(L2DamageTracker.CRIT_DMG.get(), 0.1, ADDITION),
						new ModifierEntry(Attributes.MOVEMENT_SPEED, 0.05, MULTIPLY_BASE))
				.end().end()

				.put(ModifierType.WEAPON, UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT, Items.IRON_INGOT, Items.FLINT))
				.put(CelestialForge.loc("weapon_basic"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT, Items.IRON_SWORD, Items.FLINT),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT, Items.GOLDEN_SWORD, Items.GUNPOWDER, Items.EMERALD),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND, Items.TNT, Items.ANVIL, Items.AMETHYST_SHARD, Items.SKELETON_SKULL),
						UpgradeRecipeBuilder.of(12, Items.ECHO_SHARD, Items.ENCHANTED_BOOK, Items.GOLDEN_APPLE, Items.MAGMA_CREAM, Items.LAVA_BUCKET),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP, Items.PHANTOM_MEMBRANE, Items.EMERALD, Items.DIAMOND_SWORD, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
						UpgradeRecipeBuilder.of(23, Items.NETHERITE_INGOT, CCItems.TREASURE_FRAGMENT.get(), CCItems.VIRTUAL_GOLD_NUGGET.get()),
						UpgradeRecipeBuilder.of(30, CCItems.DEATH_ESSENCE.get(), CCItems.FIRE_ESSENCE.asItem(), Items.MAGMA_BLOCK, Items.END_STONE),
						UpgradeRecipeBuilder.of(38, Items.SHULKER_SHELL, CCItems.VIRTUAL_GOLD_INGOT.get(), CCItems.OCEAN_ESSENCE.get(), CCItems.WARDEN_SCLERITE.get()),
						UpgradeRecipeBuilder.of(47, Items.NETHERITE_SHOVEL, CCItems.SHULKER_SCRAP.get(), CCItems.LIGHT_FRAGMENT.get(), Items.DRAGON_BREATH),
						UpgradeRecipeBuilder.of(57, CCItems.HEART_FRAGMENT.get(), CCItems.EARTH_CORE.get(), Items.END_ROD, Items.TOTEM_OF_UNDYING)
				)
				.put(CelestialForge.loc("accurate"), 200, L2DamageTracker.CRIT_RATE.get(), 0.02, ADDITION)
				.put(CelestialForge.loc("senior"), 200, L2DamageTracker.CRIT_DMG.get(), 0.02, ADDITION)
				.put(CelestialForge.loc("devil"), 150, L2DamageTracker.CRIT_DMG.get(), 0.05, MULTIPLY_BASE)
				.put(CelestialForge.loc("sawtooth"), 300, Attributes.ATTACK_DAMAGE, 1, ADDITION)
				.put(CelestialForge.loc("spine"), 200, Attributes.ATTACK_DAMAGE, 0.05, MULTIPLY_BASE)
				.end()

				.put(CelestialForge.loc("weapon_advanced"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.IRON_SWORD, Items.FLINT),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT, Items.BOOK, Items.GOLDEN_SWORD, Items.GUNPOWDER, Items.LAPIS_LAZULI),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND, Items.EMERALD, Items.TNT, Items.ANVIL, Items.AMETHYST_SHARD, Items.WITHER_SKELETON_SKULL),
						UpgradeRecipeBuilder.of(12, Items.ECHO_SHARD, Items.EXPERIENCE_BOTTLE, Items.ENCHANTED_BOOK, Items.GOLDEN_APPLE, Items.MAGMA_CREAM, Items.LAVA_BUCKET),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP, Items.GHAST_TEAR, Items.PHANTOM_MEMBRANE, Items.EMERALD, Items.DIAMOND_SWORD, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE),
						UpgradeRecipeBuilder.of(23, Items.NETHERITE_INGOT, CCItems.TREASURE_FRAGMENT.get(), CCItems.VIRTUAL_GOLD_NUGGET.get()),
						UpgradeRecipeBuilder.of(30, CCItems.DEATH_ESSENCE.get(), CCItems.FIRE_ESSENCE.asItem(), Items.MAGMA_BLOCK, Items.END_STONE),
						UpgradeRecipeBuilder.of(38, Items.SHULKER_SHELL, CCItems.VIRTUAL_GOLD_INGOT.get(), CCItems.OCEAN_ESSENCE.get(), CCItems.WARDEN_SCLERITE.get()),
						UpgradeRecipeBuilder.of(47, Items.NETHERITE_SHOVEL, CCItems.SHULKER_SCRAP.get(), CCItems.LIGHT_FRAGMENT.get(), Items.DRAGON_BREATH),
						UpgradeRecipeBuilder.of(57, CCItems.HEART_FRAGMENT.get(), CCItems.EARTH_CORE.get(), Items.END_ROD, Items.NETHER_STAR)
				)
				.put(CelestialForge.loc("rude"), 75,
						new ModifierEntry(L2DamageTracker.CRIT_RATE.get(), -0.05, ADDITION),
						new ModifierEntry(L2DamageTracker.CRIT_DMG.get(), 0.1, ADDITION))
				.put(CelestialForge.loc("insane"), 100,
						new ModifierEntry(Attributes.ATTACK_DAMAGE, -0.05, MULTIPLY_BASE),
						new ModifierEntry(Attributes.ATTACK_SPEED, 0.1, MULTIPLY_BASE))
				.put(CelestialForge.loc("legend"), 50,
						new ModifierEntry(Attributes.ATTACK_DAMAGE, 0.1, MULTIPLY_BASE),
						new ModifierEntry(Attributes.ATTACK_SPEED, 0.1, MULTIPLY_BASE),
						new ModifierEntry(L2DamageTracker.CRIT_RATE.get(), 0.05, ADDITION))
				.end()
				.end()

				.put(ModifierType.CURIO, UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT, Items.LAPIS_LAZULI, Items.REDSTONE))
				.put(CelestialForge.loc("curio_basic"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT, Items.IRON_INGOT, Items.PAPER),
						UpgradeRecipeBuilder.of(5, Items.IRON_AXE, Items.GOLD_INGOT, Items.BOOK, Items.ENDER_PEARL),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND, Items.SMITHING_TABLE, Items.EMERALD, Items.REDSTONE_BLOCK),
						UpgradeRecipeBuilder.of(12, Items.EMERALD_BLOCK, Items.ENCHANTED_BOOK, Items.COD_BUCKET, Items.EXPERIENCE_BOTTLE, Items.GLOW_INK_SAC),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP, Items.GHAST_TEAR, Items.AMETHYST_BLOCK, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, Items.HEART_OF_THE_SEA),
						UpgradeRecipeBuilder.of(23, Items.NETHERITE_INGOT, CCItems.TREASURE_FRAGMENT.get(), CCItems.VIRTUAL_GOLD_NUGGET.get()),
						UpgradeRecipeBuilder.of(30, CCItems.DEATH_ESSENCE.get(), CCItems.FIRE_ESSENCE.asItem(), Items.MAGMA_BLOCK, Items.END_STONE),
						UpgradeRecipeBuilder.of(38, Items.SHULKER_SHELL, CCItems.VIRTUAL_GOLD_INGOT.get(), CCItems.OCEAN_ESSENCE.get(), CCItems.WARDEN_SCLERITE.get()),
						UpgradeRecipeBuilder.of(47, Items.NETHERITE_SHOVEL, CCItems.SHULKER_SCRAP.get(), CCItems.LIGHT_FRAGMENT.get(), Items.DRAGON_BREATH),
						UpgradeRecipeBuilder.of(57, CCItems.HEART_FRAGMENT.get(), CCItems.EARTH_CORE.get(), Items.END_ROD, Items.TOTEM_OF_UNDYING)
				)
				.put(CelestialForge.loc("rash"), 200, Attributes.ATTACK_SPEED, 0.2, ADDITION)
				.put(CelestialForge.loc("brave"), 150, Attributes.ATTACK_SPEED, 0.05, MULTIPLY_BASE)
				.put(CelestialForge.loc("dangerous"), 150, L2DamageTracker.EXPLOSION_FACTOR.get(), 0.05, MULTIPLY_BASE)
				.put(CelestialForge.loc("magic_energy"), 150, L2DamageTracker.MAGIC_FACTOR.get(), 0.05, MULTIPLY_BASE)
				.put(CelestialForge.loc("red_hot"), 150, L2DamageTracker.FIRE_FACTOR.get(), 0.05, MULTIPLY_BASE)
				.end()

				.put(CelestialForge.loc("curio_advanced"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT, Items.COPPER_INGOT, Items.IRON_INGOT, Items.PAPER),
						UpgradeRecipeBuilder.of(5, Items.IRON_AXE, Items.REDSTONE, Items.GOLD_INGOT, Items.BOOK, Items.ENDER_PEARL),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND, Items.GOLD_BLOCK, Items.SMITHING_TABLE, Items.EMERALD, Items.REDSTONE_BLOCK, Items.GOLD_BLOCK),
						UpgradeRecipeBuilder.of(12, Items.EMERALD_BLOCK, Items.LAPIS_BLOCK, Items.ENCHANTED_BOOK, Items.COD_BUCKET, Items.EXPERIENCE_BOTTLE, Items.GLOW_INK_SAC),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP, Items.ENDER_EYE, Items.GHAST_TEAR, Items.AMETHYST_BLOCK, Items.NETHERITE_UPGRADE_SMITHING_TEMPLATE, Items.HEART_OF_THE_SEA),
						UpgradeRecipeBuilder.of(23, Items.NETHERITE_INGOT, CCItems.TREASURE_FRAGMENT.get(), CCItems.VIRTUAL_GOLD_NUGGET.get()),
						UpgradeRecipeBuilder.of(30, CCItems.DEATH_ESSENCE.get(), CCItems.FIRE_ESSENCE.asItem(), Items.MAGMA_BLOCK, Items.END_STONE),
						UpgradeRecipeBuilder.of(38, Items.SHULKER_SHELL, CCItems.VIRTUAL_GOLD_INGOT.get(), CCItems.OCEAN_ESSENCE.get(), CCItems.WARDEN_SCLERITE.get()),
						UpgradeRecipeBuilder.of(47, Items.NETHERITE_SHOVEL, CCItems.SHULKER_SCRAP.get(), CCItems.LIGHT_FRAGMENT.get(), Items.DRAGON_BREATH),
						UpgradeRecipeBuilder.of(57, CCItems.HEART_FRAGMENT.get(), CCItems.EARTH_CORE.get(), Items.END_ROD, Items.TOTEM_OF_UNDYING)
				)
				.put(CelestialForge.loc("life"), 75,
						new ModifierEntry(Attributes.MAX_HEALTH, 2, ADDITION),
						new ModifierEntry(L2DamageTracker.ABSORB.get(), 2, ADDITION))
				.put(CelestialForge.loc("anger"), 100,
						new ModifierEntry(Attributes.ATTACK_KNOCKBACK, 1, ADDITION),
						new ModifierEntry(L2DamageTracker.REDUCTION.get(), -0.05, MULTIPLY_TOTAL))
				.end()
				.end()

				.put(ModifierType.ALL, UpgradeRecipeBuilder.of(3, Items.BEDROCK))
				.put(CelestialForge.loc("general_basic"),
						UpgradeRecipeBuilder.of(3, Items.COPPER_INGOT, Items.IRON_DOOR, Items.CLAY),
						UpgradeRecipeBuilder.of(5, Items.IRON_INGOT, Items.GOLD_BLOCK, Items.GLOWSTONE_DUST),
						UpgradeRecipeBuilder.of(8, Items.DIAMOND, Items.EXPERIENCE_BOTTLE, Items.EMERALD, Items.OBSERVER),
						UpgradeRecipeBuilder.of(12, Items.RABBIT_FOOT, Items.ENCHANTED_BOOK, Items.EMERALD_BLOCK, Items.HONEYCOMB, Items.ENCHANTING_TABLE),
						UpgradeRecipeBuilder.of(17, Items.NETHERITE_SCRAP, Items.DISC_FRAGMENT_5, Items.GOLD_BLOCK, Items.GLISTERING_MELON_SLICE, Items.PHANTOM_MEMBRANE),
						UpgradeRecipeBuilder.of(23, Items.NETHERITE_INGOT, CCItems.TREASURE_FRAGMENT.get(), CCItems.VIRTUAL_GOLD_NUGGET.get()),
						UpgradeRecipeBuilder.of(30, CCItems.DEATH_ESSENCE.get(), CCItems.FIRE_ESSENCE.asItem(), Items.MAGMA_BLOCK, Items.END_STONE),
						UpgradeRecipeBuilder.of(38, Items.SHULKER_SHELL, CCItems.VIRTUAL_GOLD_INGOT.get(), CCItems.OCEAN_ESSENCE.get(), CCItems.WARDEN_SCLERITE.get()),
						UpgradeRecipeBuilder.of(47, Items.NETHERITE_SHOVEL, CCItems.SHULKER_SCRAP.get(), CCItems.LIGHT_FRAGMENT.get(), Items.DRAGON_BREATH),
						UpgradeRecipeBuilder.of(57, CCItems.HEART_FRAGMENT.get(), CCItems.EARTH_CORE.get(), Items.END_ROD, Items.TOTEM_OF_UNDYING)
				)
				.put(CelestialForge.loc("fast"), 150, Attributes.MOVEMENT_SPEED, 0.05, MULTIPLY_BASE)
				.end()
				.end();

		REINFORCE_CONFIG = new ReinforceDataBuilder()
				.builder("soaring_wings").mate(CCItems.SOARING_WINGS).temp(Items.FEATHER)
				.attr(Attributes.MOVEMENT_SPEED, 0.05, MULTIPLY_BASE).build()
				.builder("heart_fragment").mate(CCItems.HEART_FRAGMENT).temp(Items.EMERALD)
				.attr(CCAttributes.REPLY_POWER.get(), 0.06, MULTIPLY_BASE).build()
				.builder("heart_of_the_sea").mate(Items.HEART_OF_THE_SEA).temp(Items.LAPIS_LAZULI)
				.attr(ForgeMod.SWIM_SPEED.get(), 0.04, MULTIPLY_BASE).build()
				.builder("dragon_head").mate(Items.DRAGON_HEAD).temp(Items.CHORUS_FRUIT)
				.attr(Attributes.ATTACK_DAMAGE, 0.05, MULTIPLY_BASE).build()
				.builder("scute").mate(Items.SCUTE).temp(Items.SAND)
				.attr(Attributes.ARMOR_TOUGHNESS, 0.03, MULTIPLY_BASE).build();
	}

	public CFConfigGen(DataGenerator generator) {
		super(generator, "CelestialForge config provider");
	}

	@Override
	public void add(Collector collector) {
		MODIFIER_CONFIG.build(collector);
		REINFORCE_CONFIG.build(collector);
	}

}
