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
		public final ForgeConfigSpec.DoubleValue modifierMaxLevelRate;
		public final ForgeConfigSpec.IntValue modifierMaxLevelBase;

		Common(ForgeConfigSpec.Builder builder) {
			modifierBookRecipeCost = builder
					.comment("Experience level that will be consumed when attaching modifier to the modifier book")
					.defineInRange("modifierBookRecipeCost", 22, 1, 100);
			modifierMaxLevelRate = builder
					.comment("Experience cost exponential multiplier for modifier upgrading")
					.defineInRange("modifierMaxLevelRate", 0.1, 0.01, 0.5);
			modifierMaxLevelBase = builder
					.comment("Basic experience required for modifier upgrade")
					.defineInRange("modifierMaxLevelBase", 125, 10, 1000);
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
