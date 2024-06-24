package com.xiaoyue.celestial_forge.register;

import com.xiaoyue.celestial_forge.content.modifier.Modifier;
import com.xiaoyue.celestial_forge.content.modifier.ModifierPool;
import com.xiaoyue.celestial_forge.data.ModifierConfig;
import com.xiaoyue.celestial_forge.data.ModifierData;
import com.xiaoyue.celestial_forge.utils.ICurioUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.*;

import java.util.HashMap;
import java.util.Map;

import static com.xiaoyue.celestial_forge.CelestialForge.MODID;

public class CModifiers {

    public static Map<ResourceLocation, Modifier> modifiers = new HashMap<>();

    public static Modifier NONE = new Modifier.ModifierBuilder(new ResourceLocation(MODID, "none"), "modifier_none", Modifier.ModifierType.ALL).setWeight(0).build();

    static {
        modifiers.put(NONE.name, NONE);
    }
    public static boolean armorPoolIf(ItemStack stack) {
        return stack.getItem() instanceof ArmorItem;
    }

    public static boolean toolPoolIf(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof DiggerItem || item instanceof SwordItem || item instanceof ShieldItem;
    }

    public static boolean bowPoolIf(ItemStack stack) {
        Item item = stack.getItem();
        return item instanceof BowItem || item instanceof CrossbowItem;
    }

    public static boolean curioPoolIf(ItemStack stack) {
        return ICurioUtils.isCurio(stack);
    }

    public static ModifierPool armorPool = new ModifierPool(CModifiers::armorPoolIf);

    public static ModifierPool toolPool = new ModifierPool(CModifiers::toolPoolIf);

    public static ModifierPool bowPool = new ModifierPool(CModifiers::bowPoolIf);

    public static ModifierPool curioPool = new ModifierPool(CModifiers::curioPoolIf);

    private static Modifier.ModifierBuilder armor(String name) {
        return new Modifier.ModifierBuilder(new ResourceLocation(MODID, name), "modifier_" + name, Modifier.ModifierType.EQUIPPED);
    }

    private static Modifier.ModifierBuilder held(String name) {
        return new Modifier.ModifierBuilder(new ResourceLocation(MODID, name), "modifier_" + name, Modifier.ModifierType.HELD);
    }

    private static Modifier.ModifierBuilder curio(String name) {
        return new Modifier.ModifierBuilder(new ResourceLocation(MODID, name), "modifier_" + name, Modifier.ModifierType.CURIO);
    }

    private static Modifier.ModifierBuilder all(String name) {
        return new Modifier.ModifierBuilder(new ResourceLocation(MODID, name), "modifier_" + name, Modifier.ModifierType.ALL);
    }

    private static void addArmor(Modifier modifier) {
        modifiers.put(modifier.name, modifier);
        armorPool.add(modifier);
    }

    private static void addTool(Modifier modifier) {
        modifiers.put(modifier.name, modifier);
        toolPool.add(modifier);
    }

    private static void addBow(Modifier modifier) {
        modifiers.put(modifier.name, modifier);
        bowPool.add(modifier);
    }
    private static void addCurio(Modifier modifier) {
        modifiers.put(modifier.name, modifier);
        curioPool.add(modifier);
    }

    private static void addAll(Modifier modifier) {
        modifiers.put(modifier.name, modifier);
        curioPool.add(modifier);
        armorPool.add(modifier);
        bowPool.add(modifier);
        toolPool.add(modifier);
    }

    private static Modifier.AttributeModifierSupplier modify(double amount, Operation op) {
        return new Modifier.AttributeModifierSupplier(amount, op);
    }

    private static Modifier.AttributeModifierSupplier[] mods(Double[] amounts, AttributeModifier.Operation[] operations) {
        Modifier.AttributeModifierSupplier[] suppliers = new Modifier.AttributeModifierSupplier[amounts.length];
        for (int index = 0; index < amounts.length; index++) {
            suppliers[index] = new Modifier.AttributeModifierSupplier(amounts[index], operations[index]);
        }
        return suppliers;
    }

    public static void initArmorModifiers() {
        for (ModifierData modifier : ModifierConfig.modifiers) {
            if (modifier.type().equals("armor")) {
                addArmor(armor(modifier.name()).setWeight(modifier.weight()).addModifiers(modifier.attributes(), mods(modifier.amounts(), modifier.ops())).build());
            }
        }
    }

    public static void initToolModifiers() {
        for (ModifierData modifier : ModifierConfig.modifiers) {
            if (modifier.type().equals("tool")) {
                addTool(held(modifier.name()).setWeight(modifier.weight()).addModifiers(modifier.attributes(), mods(modifier.amounts(), modifier.ops())).build());
            }
        }
    }

    public static void initBowModifiers() {
        for (ModifierData modifier : ModifierConfig.modifiers) {
            if (modifier.type().equals("bow")) {
                addBow(held(modifier.name()).setWeight(modifier.weight()).addModifiers(modifier.attributes(), mods(modifier.amounts(), modifier.ops())).build());
            }
        }
    }

    public static void initCurioModifiers() {
        for (ModifierData modifier : ModifierConfig.modifiers) {
            if (modifier.type().equals("curio")) {
                addCurio(curio(modifier.name()).setWeight(modifier.weight()).addModifiers(modifier.attributes(), mods(modifier.amounts(), modifier.ops())).build());
            }
        }
    }

    public static void initAllModifiers() {
        for (ModifierData modifier : ModifierConfig.modifiers) {
            if (modifier.type().equals("all")) {
                addAll(all(modifier.name()).setWeight(modifier.weight()).addModifiers(modifier.attributes(), mods(modifier.amounts(), modifier.ops())).build());
            }
        }
    }

    static {
        initArmorModifiers();
        initToolModifiers();
        initBowModifiers();
        initCurioModifiers();
        initAllModifiers();
    }
}
