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
