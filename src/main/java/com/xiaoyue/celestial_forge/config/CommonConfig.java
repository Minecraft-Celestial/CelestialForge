package com.xiaoyue.celestial_forge.config;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.xiaoyue.celestial_forge.CelestialForge.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonConfig {

    public static ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> modifier_recipe = builder
            .defineListAllowEmpty("modifier_recipe", List.of("minecraft:netherite_upgrade_smithing_template"), CommonConfig::allow);

    public static ForgeConfigSpec SPEC = builder.build();

    private static boolean allow(Object object) {
        return ForgeRegistries.ITEMS.containsKey(new ResourceLocation((String) object));
    }

    public static Set<Item> items;

    private static void getConfig() {
        items = modifier_recipe.get().stream().map(items ->
                ForgeRegistries.ITEMS.getValue(new ResourceLocation(items))).collect(Collectors.toSet());
    }

    @SubscribeEvent
    public static void onStepUp(FMLCommonSetupEvent event) {
        getConfig();
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Reloading event) {
        getConfig();
    }

    public static void initConfig() {
        String path = "celestial_configs/" + MODID + ".toml";
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC, path);
    }
}
