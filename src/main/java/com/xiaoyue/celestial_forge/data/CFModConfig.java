package com.xiaoyue.celestial_forge.data;

import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_invoker.invoker.config.wrapper.ConfigWrapper;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

public class CFModConfig {

	public static class Common extends ConfigWrapper {

		public final ModConfigSpec.DoubleValue echoShardPickExpBonus;
		public final ModConfigSpec.DoubleValue earthCoreMiningSpeed;
		public final ModConfigSpec.DoubleValue voidEssenceExtraDamage;
		public final ModConfigSpec.DoubleValue deathEssenceDamageHeal;
		public final ModConfigSpec.DoubleValue pureStarDamageFactor;

		Common(Builder builder) {
			setCelestial();
			builder.push("reinforce", "Reinforce");
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

	public static class Server extends ConfigWrapper {

		public final ModConfigSpec.IntValue modifierBookRecipeCost;
		public final ModConfigSpec.IntValue modifierBookCraftCost;
		public final ModConfigSpec.DoubleValue modifierMaxLevelRate;
		public final ModConfigSpec.IntValue modifierMaxLevelBase;
		public final ModConfigSpec.IntValue grindstoneRemovalPriorityLevel;
		public final ModConfigSpec.IntValue modifierToBookLevel;
		public final ModConfigSpec.ConfigValue<String> bookReinforcementMate;

		public final ModConfigSpec.BooleanValue enableWeaponForging;
		public final ModConfigSpec.BooleanValue enableRangedForging;
		public final ModConfigSpec.BooleanValue enableArmorForging;
		public final ModConfigSpec.BooleanValue enableToolForging;
		public final ModConfigSpec.BooleanValue enableCurioForging;
		public final ModConfigSpec.BooleanValue enableModifierUpgrades;



		Server(Builder builder) {
			setCelestial();
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
			bookReinforcementMate = builder
					.comment("Modifier book reinforcement material")
					.define("bookReinforcementMate", BuiltInRegistries.ITEM.getKey(Items.NETHER_STAR).toString());
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
		}
	}

	public static final Server SERVER = CelestialForge.EXTRA.initConfig(ModConfig.Type.SERVER, Server::new);
	public static final Common COMMON = CelestialForge.EXTRA.initConfig(ModConfig.Type.COMMON, Common::new);

	public static void init() {
	}

}
