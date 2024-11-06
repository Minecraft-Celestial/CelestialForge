package com.xiaoyue.celestial_forge.config.modifier;

import com.google.common.collect.Lists;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class CuriosConfig {
    public static ForgeConfigSpec CONFIG;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> NAMES;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> WEIGHTS;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> ATTRIBUTES;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> AMOUNTS;
    public static ForgeConfigSpec.ConfigValue<List<? extends String>> OPERATIONS_IDS;

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        BUILDER.push("Modifiers for curios");
        BUILDER.comment("You may need to make a resource pack to save the customization-required translation key for the attributes if the mod author didn't do that, or your customization on other mods' attribute may not look well", "A hint on the translation key format: attribute.modxxx.attributexxx, e.g. attribute.minecraft.generic.attack_damage");
        NAMES = BUILDER
                .comment("The name of the modifier")
                .defineList("NAMES", Lists.newArrayList("accurate", "luck", "anger", "rash", "life"), o -> true);
        WEIGHTS = BUILDER
                .comment("The weight of the modifier in the modifiers pool")
                .defineList("WEIGHTS", Lists.newArrayList("250", "125", "150", "250", "150"), o -> true);
        ATTRIBUTES = BUILDER
                .comment("The attribute of the modifier has. One modifier can have multiple attributes. Use ';' to split different attributes")
                .defineList("ATTRIBUTES", Lists.newArrayList("l2damagetracker:crit_rate", "l2damagetracker:crit_rate", "minecraft:generic.attack_damage", "minecraft:generic.attack_speed", "minecraft:generic.max_health"), o -> true);
        AMOUNTS = BUILDER
                .comment("The amount used to calculate the attribute effect. Also can be multiple. Use ';' to split")
                .defineList("AMOUNTS", Lists.newArrayList("0.02", "0.04", "0.04", "0.02", "2"), o -> true);
        OPERATIONS_IDS = BUILDER
                .comment("The operation ID of the attribute calculation. Can be three values: 0,1,2. 0 is ADDITION. 1 is MULTIPLY_BASE. 2 is MULTIPLY_TOTAL. you can refer to the calculation of the attributes already in the game")
                .defineList("OPERATIONS_IDS", Lists.newArrayList("2", "2", "2", "2", "0"), o -> true);
        BUILDER.pop();
        CONFIG = BUILDER.build();
    }
}
