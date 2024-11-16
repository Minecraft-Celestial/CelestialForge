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

		Common(ForgeConfigSpec.Builder builder) {

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
