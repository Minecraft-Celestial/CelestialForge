package com.xiaoyue.celestial_forge.content.modifier;

import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public interface ModifierProxy {

    static Modifier.AttributeModifierSupplier mod(String amount, String operation) {
        return new Modifier.AttributeModifierSupplier(Double.parseDouble(amount), AttributeModifier.Operation.fromValue(Integer.parseInt(operation)));
    }

    static Modifier.AttributeModifierSupplier[] mods(String[] amounts, String[] operations) {
        Modifier.AttributeModifierSupplier[] suppliers = new Modifier.AttributeModifierSupplier[amounts.length];
        for (int index = 0; index < amounts.length; index++) {
            suppliers[index] = new Modifier.AttributeModifierSupplier(Double.parseDouble(amounts[index]), AttributeModifier.Operation.fromValue(Integer.parseInt(operations[index])));
        }
        return suppliers;
    }
}
