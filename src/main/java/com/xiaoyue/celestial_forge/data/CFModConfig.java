package com.xiaoyue.celestial_forge.data;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

import static com.xiaoyue.celestial_forge.CelestialForge.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CFModConfig {

	public static class Common {

		public final ForgeConfigSpec.IntValue modifierBookRecipeCost;
		public final ForgeConfigSpec.IntValue modifierBookCraftCost;
		public final ForgeConfigSpec.DoubleValue modifierMaxLevelRate;
		public final ForgeConfigSpec.IntValue modifierMaxLevelBase;
		public final ForgeConfigSpec.IntValue grindstoneRemovalPriorityLevel;
		public final ForgeConfigSpec.IntValue modifierToBookLevel;

		public final ForgeConfigSpec.BooleanValue enableWeaponForging;
		public final ForgeConfigSpec.BooleanValue enableRangedForging;
		public final ForgeConfigSpec.BooleanValue enableArmorForging;
		public final ForgeConfigSpec.BooleanValue enableToolForging;
		public final ForgeConfigSpec.BooleanValue enableCurioForging;
		public final ForgeConfigSpec.BooleanValue enableModifierUpgrades;

		public final ForgeConfigSpec.DoubleValue echoShardPickExpBonus;
		public final ForgeConfigSpec.DoubleValue earthCoreMiningSpeed;
		public final ForgeConfigSpec.DoubleValue voidEssenceExtraDamage;
		public final ForgeConfigSpec.DoubleValue deathEssenceDamageHeal;
		public final ForgeConfigSpec.DoubleValue pureStarDamageFactor;

		Common(ForgeConfigSpec.Builder builder) {
			modifierBookRecipeCost = builder
					.comment("Experience level that will be consumed when transferring modifier from modifier book to equipment")
					.defineInRange("modifierBookRecipeCost", 22, 1, 100);
			modifierBookCraftCost = builder
					.comment("Experience level that will be consumed when transferring modifier from equipment to modifier book")
					.defineInRange("modifierBookCraftCost", 22, 1, 100);
			modifierMaxLevelRate = builder
					.comment("Experience cost exponential multiplier for modifier upgrading")
					.defineInRange("modifierMaxLevelRate", 0.1, 0.01, 0.5);
			modifierMaxLevelBase = builder
					.comment("Basic experience required for modifier upgrade")
					.defineInRange("modifierMaxLevelBase", 125, 10, 1000);
			grindstoneRemovalPriorityLevel = builder
					.comment("Max level which grindstone will prioritize removing modifier over enchantments")
					.defineInRange("grindstoneRemovalPriorityLevel", 0, 0, 100);
			modifierToBookLevel = builder
					.comment("Min level which modifier can be transferred to modifier book on anvil")
					.defineInRange("modifierToBookLevel", 30, 0, 100);

			enableWeaponForging = builder
					.comment("Enable forging for type Weapon")
					.define("enableWeaponForging", true);
			enableRangedForging = builder
					.comment("Enable forging for type Ranged Weapon")
					.define("enableRangedForging", true);
			enableArmorForging = builder
					.comment("Enable forging for type Armor")
					.define("enableArmorForging", true);
			enableToolForging = builder
					.comment("Enable forging for type Tool")
					.define("enableToolForging", true);
			enableCurioForging = builder
					.comment("Enable forging for type Curio")
					.define("enableCurioForging", true);
			enableModifierUpgrades = builder
					.comment("Enable modifier to be upgraded")
					.define("enableModifierUpgrades", true);

			builder.push("reinforce");
			echoShardPickExpBonus = builder
					.comment("Echo Shard Reinforce: pick  exp bonus")
					.defineInRange("echoShardPickExpBonus", 0.03, 0.01, 100);
			earthCoreMiningSpeed = builder
					.comment("Earth Core Reinforce: mining speed bonus")
					.defineInRange("earthCoreMiningSpeed", 0.08, 0.01, 100);
			voidEssenceExtraDamage = builder
					.comment("Void Essence Reinforce: extra abyss damage")
					.defineInRange("voidEssenceExtraDamage", 2, 1f, 100000);
			deathEssenceDamageHeal = builder
					.comment("Death Essence Reinforce: The amount of health regenerated after attacking")
					.defineInRange("deathEssenceDamageHeal", 0.05, 0.01, 100);
			pureStarDamageFactor = builder
					.comment("Pure Star Reinforce: percentage damage attached when attacking undead creatures")
					.defineInRange("pureStarDamageFactor", 0.01, 0.01, 1);
			builder.pop();
		}

	}

	public static ForgeConfigSpec COMMON_SPEC;
	public static Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> common = new ForgeConfigSpec.Builder().configure(Common::new);
		COMMON_SPEC = common.getRight();
		COMMON = common.getLeft();
	}

	public static void initConfig() {
		String path = "celestial_configs/" + MODID + "-common.toml";
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CFModConfig.COMMON_SPEC, path);

	}

}
