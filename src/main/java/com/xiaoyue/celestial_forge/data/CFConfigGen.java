package com.xiaoyue.celestial_forge.data;

import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.content.data.*;
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
				.put(CelestialForge.loc("hard"), 300, new ModifierEntry(Attributes.ARMOR, 2, 0.04, ADDITION))
				.put(CelestialForge.loc("guarding"), 200, new ModifierEntry(Attributes.ARMOR, 0.05, 0.04, MULTIPLY_BASE))
				.put(CelestialForge.loc("armor"), 100, new ModifierEntry(Attributes.ARMOR_TOUGHNESS, 1, 0.04, ADDITION))
				.put(CelestialForge.loc("protect"), 150, new ModifierEntry(Attributes.ARMOR_TOUGHNESS, 0.05, 0.04, MULTIPLY_BASE))
				.end()
		;
		//TODO add others
	}

	public CFConfigGen(DataGenerator generator) {
		super(generator, "Default modifiers");
	}

	@Override
	public void add(Collector collector) {
		collector.add(CelestialForge.MODIFIER, CelestialForge.loc("default"), CONFIG);
	}
}

/*

bow
        NAMES = BUILDER
                .comment("The name of the modifier")
                .defineList("NAMES", Lists.newArrayList("brisk", "fast", "speed", "agile"), o -> true);
        WEIGHTS = BUILDER
                .comment("The weight of the modifier in the modifiers pool")
                .defineList("WEIGHTS", Lists.newArrayList("220", "200", "150", "120"), o -> true);
        ATTRIBUTES = BUILDER
                .comment("The attribute of the modifier has. One modifier can have multiple attributes. Use ';' to split different attributes")
                .defineList("ATTRIBUTES", Lists.newArrayList("minecraft:generic.movement_speed", "celestial_core:arrow_speed", "minecraft:generic.movement_speed", "celestial_core:arrow_speed"), o -> true);
        AMOUNTS = BUILDER
                .comment("The amount used to calculate the attribute effect. Also can be multiple. Use ';' to split")
                .defineList("AMOUNTS", Lists.newArrayList("0.02", "0.02", "0.05", "0.05"), o -> true);
        OPERATIONS_IDS = BUILDER
                .comment("The op ID of the attribute calculation. Can be three values: 0,1,2. 0 is ADDITION. 1 is MULTIPLY_BASE. 2 is MULTIPLY_TOTAL. you can refer to the calculation of the attributes already in the game")
                .defineList("OPERATIONS_IDS", Lists.newArrayList("1", "1", "1", "1"), o -> true);
        BUILDER.pop();


curios

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
                .comment("The op ID of the attribute calculation. Can be three values: 0,1,2. 0 is ADDITION. 1 is MULTIPLY_BASE. 2 is MULTIPLY_TOTAL. you can refer to the calculation of the attributes already in the game")
                .defineList("OPERATIONS_IDS", Lists.newArrayList("2", "2", "2", "2", "0"), o -> true);
        BUILDER.pop();


tools
                BUILDER.push("Modifiers for tools");
        BUILDER.comment("This configuration file is based on index. it means, 'legendary' -> '30' ->'generic.attack_damage;generic.attack_speed' -> '0.15;0.1' -> '2;2' is the first index, and in it, 'generic.attack_damage' -> '0.15' -> '2', 'generic.attack_speed' -> '0.1' -> '2'", "You may need to make a resource pack to save the customization-required translation key for the attributes if the mod author didn't do that, or your customization on other mods' attribute may not look well", "A hint on the translation key format: attribute.modxxx.attributexxx, e.g. attribute.minecraft.generic.attack_damage");
        NAMES = BUILDER
                .comment("The name of the modifier")
                .defineList("NAMES", Lists.newArrayList("legend", "sharp", "broken", "damaged", "agile", "swift", "sluggish", "slow", "light", "heavy"), o -> true);
        WEIGHTS = BUILDER
                .comment("The weight of the modifier in the modifiers pool")
                .defineList("WEIGHTS", Lists.newArrayList("30", "100", "70", "100", "100", "100", "100", "100", "100", "100"), o -> true);
        ATTRIBUTES = BUILDER
                .comment("The attribute of the modifier has. One modifier can have multiple attributes. Use ';' to split different attributes")
                .defineList("ATTRIBUTES", Lists.newArrayList("minecraft:generic.attack_damage;minecraft:generic.attack_speed", "minecraft:generic.attack_damage", "minecraft:generic.attack_damage", "minecraft:generic.attack_damage", "minecraft:generic.attack_speed;minecraft:generic.movement_speed", "minecraft:generic.attack_speed", "minecraft:generic.attack_speed;minecraft:generic.movement_speed", "minecraft:generic.attack_speed", "minecraft:generic.attack_damage;minecraft:generic.attack_speed", "minecraft:generic.attack_damage;minecraft:generic.attack_speed;minecraft:generic.movement_speed"), o -> true);
        AMOUNTS = BUILDER
                .comment("The amount used to calculate the attribute effect. Also can be multiple. Use ';' to split")
                .defineList("AMOUNTS", Lists.newArrayList("0.15;0.1", "0.1", "-0.2", "-0.1", "0.05;0.1", "0.1", "-0.05;-0.1", "-0.15", "-0.1;0.15", "0.2;-0.15;-0.05"), o -> true);
        OPERATIONS_IDS = BUILDER
                .comment("The op ID of the attribute calculation. Can be three values: 0,1,2. 0 is ADDITION. 1 is MULTIPLY_BASE. 2 is MULTIPLY_TOTAL. you can refer to the calculation of the attributes already in the game")
                .defineList("OPERATIONS_IDS", Lists.newArrayList("2;2", "2", "2", "2", "2;2", "2", "2;2", "2", "2;2", "2;2;2"), o -> true);
        BUILDER.pop();
 */
