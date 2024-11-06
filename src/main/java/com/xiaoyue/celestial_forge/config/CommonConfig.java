package com.xiaoyue.celestial_forge.config;

import com.xiaoyue.celestial_forge.config.modifier.*;
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
    public static void onReload(ModConfigEvent.Reloading event) {
        getConfig();
    }

    public static void initConfig() {
        String path = "celestial_configs/" + MODID + ".toml";
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CommonConfig.SPEC, path);
    }

    public static void initModifierAllConfig( ) {
        ModLoadingContext context = ModLoadingContext.get();
        context.registerConfig(ModConfig.Type.COMMON, AllConfig.CONFIG, "celestial_configs/celestial_modifier/" + MODID + ".all.toml");
        context.registerConfig(ModConfig.Type.COMMON, ArmorConfig.CONFIG, "celestial_configs/celestial_modifier/" + MODID + ".armor.toml");
        context.registerConfig(ModConfig.Type.COMMON, BowConfig.CONFIG, "celestial_configs/celestial_modifier/" + MODID + ".bow.toml");
        context.registerConfig(ModConfig.Type.COMMON, CuriosConfig.CONFIG, "celestial_configs/celestial_modifier/" + MODID + ".curio.toml");
        context.registerConfig(ModConfig.Type.COMMON, ToolConfig.CONFIG, "celestial_configs/celestial_modifier/" + MODID + ".tool.toml");
    }
}
