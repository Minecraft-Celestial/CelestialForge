package com.xiaoyue.celestial_forge.data;

import com.xiaoyue.celestial_core.register.CCAttributes;
import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.content.modifier.ModifierConfig;
import com.xiaoyue.celestial_forge.content.modifier.ModifierEntry;
import com.xiaoyue.celestial_forge.content.modifier.ModifierType;
import dev.xkmc.l2damagetracker.init.L2DamageTracker;
import dev.xkmc.l2library.serial.config.ConfigDataProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.entity.ai.attributes.Attributes;

import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.ADDITION;
import static net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation.MULTIPLY_BASE;

public class CFConfigGen extends ConfigDataProvider {

	private static final ModifierConfig CONFIG;

	static {
		CONFIG = new ModifierConfig()
				// generic
				.put(ModifierType.ARMOR, CelestialForge.loc("hard"), 300, Attributes.ARMOR, 1, ADDITION)
				.put(ModifierType.ARMOR, CelestialForge.loc("guarding"), 200, Attributes.ARMOR, 2, ADDITION)
				.put(ModifierType.ARMOR, CelestialForge.loc("armor"), 100, Attributes.ARMOR, 4, ADDITION)
				.put(ModifierType.ARMOR, CelestialForge.loc("protect"), 150, Attributes.ARMOR_TOUGHNESS, 2, ADDITION)
				.put(ModifierType.TOOL, CelestialForge.loc("sawtooth"), 300, Attributes.ATTACK_DAMAGE, 1, ADDITION)
				.put(ModifierType.TOOL, CelestialForge.loc("spine"), 200, Attributes.ATTACK_DAMAGE, 2, ADDITION)
				.put(ModifierType.TOOL, CelestialForge.loc("dangerous"), 100, Attributes.ARMOR, 4, ADDITION)
				.put(ModifierType.RANGED, CelestialForge.loc("strength"), 300, L2DamageTracker.BOW_STRENGTH.get(), 0.02, MULTIPLY_BASE)
				.put(ModifierType.RANGED, CelestialForge.loc("fatal"), 150, L2DamageTracker.BOW_STRENGTH.get(), 0.04, MULTIPLY_BASE)
				.put(ModifierType.WEAPON, CelestialForge.loc("accurate"), 300, L2DamageTracker.CRIT_RATE.get(), 0.02, ADDITION)
				.put(ModifierType.WEAPON, CelestialForge.loc("luck"), 150, L2DamageTracker.BOW_STRENGTH.get(), 0.04, MULTIPLY_BASE)
				.put(ModifierType.WEAPON, CelestialForge.loc("senior"), 300, L2DamageTracker.CRIT_DMG.get(), 0.02, ADDITION)
				.put(ModifierType.WEAPON, CelestialForge.loc("devil"), 150, L2DamageTracker.CRIT_DMG.get(), 0.04, ADDITION)
				.put(ModifierType.CURIO, CelestialForge.loc("rash"), 300, Attributes.ATTACK_SPEED, 0.02, MULTIPLY_BASE)
				.put(ModifierType.CURIO, CelestialForge.loc("brave"), 150, Attributes.ATTACK_SPEED, 0.04, MULTIPLY_BASE)
				.put(ModifierType.ALL, CelestialForge.loc("light"), 300, Attributes.MOVEMENT_SPEED, 0.02, MULTIPLY_BASE)
				.put(ModifierType.ALL, CelestialForge.loc("fast"), 150, Attributes.MOVEMENT_SPEED, 0.04, MULTIPLY_BASE)
				// special
				.put(ModifierType.WEAPON, CelestialForge.loc("rude"), 75,
						new ModifierEntry(L2DamageTracker.CRIT_RATE.get(), -0.05, ADDITION),
						new ModifierEntry(L2DamageTracker.CRIT_DMG.get(), 0.1, ADDITION))
				.put(ModifierType.ARMOR, CelestialForge.loc("solid"), 50,
						new ModifierEntry(Attributes.ARMOR, 2, ADDITION),
						new ModifierEntry(Attributes.ARMOR_TOUGHNESS, 2, ADDITION),
						new ModifierEntry(Attributes.KNOCKBACK_RESISTANCE, 0.1, ADDITION))
				.put(ModifierType.CURIO, CelestialForge.loc("life"), 75,
						new ModifierEntry(Attributes.MAX_HEALTH, 2, ADDITION),
						new ModifierEntry(CCAttributes.REPLY_POWER.get(), 0.05, MULTIPLY_BASE))
				.put(ModifierType.CURIO, CelestialForge.loc("anger"), 100,
						new ModifierEntry(Attributes.ATTACK_KNOCKBACK, 1, ADDITION),
						new ModifierEntry(CCAttributes.ARROW_KNOCK.get(), 1, ADDITION))
				.put(ModifierType.RANGED, CelestialForge.loc("unreal"), 50,
						new ModifierEntry(L2DamageTracker.BOW_STRENGTH.get(), 0.1, MULTIPLY_BASE),
						new ModifierEntry(L2DamageTracker.CRIT_DMG.get(), 0.1, ADDITION),
						new ModifierEntry(CCAttributes.ARROW_SPEED.get(), 0.05, MULTIPLY_BASE))
				.put(ModifierType.TOOL, CelestialForge.loc("insane"), 100,
						new ModifierEntry(Attributes.ATTACK_DAMAGE, -0.05, MULTIPLY_BASE),
						new ModifierEntry(Attributes.ATTACK_SPEED, 0.1, MULTIPLY_BASE))
				.put(ModifierType.TOOL, CelestialForge.loc("legend"), 50,
						new ModifierEntry(Attributes.ATTACK_DAMAGE, 0.1, MULTIPLY_BASE),
						new ModifierEntry(Attributes.ATTACK_SPEED, 0.1, MULTIPLY_BASE),
						new ModifierEntry(L2DamageTracker.CRIT_RATE.get(), 0.05, ADDITION));
	}

	public CFConfigGen(DataGenerator generator) {
		super(generator, "Default modifiers");
	}

	@Override
	public void add(Collector collector) {
		collector.add(CelestialForge.MODIFIER, CelestialForge.loc("default"), CONFIG);
	}
}
