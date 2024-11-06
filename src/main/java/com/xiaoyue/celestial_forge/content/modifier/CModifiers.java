package com.xiaoyue.celestial_forge.content.modifier;

import com.xiaoyue.celestial_forge.config.modifier.*;
import com.xiaoyue.celestial_forge.utils.ICurioUtils;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

import static com.xiaoyue.celestial_forge.CelestialForge.MODID;

public class CModifiers implements ModifierProxy {

    public static Map<ResourceLocation, Modifier> modifiers = new HashMap<>();

    public static Modifier NONE = new Modifier.ModifierBuilder(new ResourceLocation(MODID, "none"), "modifier_none", Modifier.ModifierType.ALL).setWeight(0).build();

    static {
        modifiers.put(NONE.name, NONE);
    }

    public static boolean armorPoolIf(ItemStack stack) {
        return ModifierUtils.isArmor(stack);
    }

    public static boolean toolPoolIf(ItemStack stack) {
        return ModifierUtils.isTool(stack);
    }

    public static boolean bowPoolIf(ItemStack stack) {
        return ModifierUtils.isRangedWeapon(stack);
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

    private static Modifier.ModifierBuilder tool(String name) {
        return new Modifier.ModifierBuilder(new ResourceLocation(MODID, name), "modifier_" + name, Modifier.ModifierType.HELD);
    }
    
    private static Modifier.ModifierBuilder ranged(String name) {
        return new Modifier.ModifierBuilder(new ResourceLocation(MODID, name), "modifier_" + name, Modifier.ModifierType.RANGED);
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

    private static void initBowModifiers () {
        for (int index = 0; index < BowConfig.NAMES.get().size(); index++) {
            String name = BowConfig.NAMES.get().get(index);
            String weight = BowConfig.WEIGHTS.get().get(index);
            String attribute = BowConfig.ATTRIBUTES.get().get(index);
            String amount = BowConfig.AMOUNTS.get().get(index);
            String operations_id = BowConfig.OPERATIONS_IDS.get().get(index);
            if (attribute.contains(";")) {
                String[] attributes = attribute.split(";");
                String[] amounts = amount.split(";");
                String[] operations_ids = operations_id.split(";");
                addBow(ranged(name).addModifiers(attributes, ModifierProxy.mods(amounts, operations_ids)).setWeight(Integer.parseInt(weight)).build());
            } else {
                addBow(ranged(name).setWeight(Integer.parseInt(weight)).addModifier(attribute, ModifierProxy.mod(amount, operations_id)).build());
            }
        }
    }

    private static void initAllModifiers () {
        for (int index = 0; index < AllConfig.NAMES.get().size(); index++) {
            String name = AllConfig.NAMES.get().get(index);
            String weight = AllConfig.WEIGHTS.get().get(index);
            String attribute = AllConfig.ATTRIBUTES.get().get(index);
            String amount = AllConfig.AMOUNTS.get().get(index);
            String operations_id = AllConfig.OPERATIONS_IDS.get().get(index);
            if (attribute.contains(";")) {
                String[] attributes = attribute.split(";");
                String[] amounts = amount.split(";");
                String[] operations_ids = operations_id.split(";");
                addAll(all(name).addModifiers(attributes, ModifierProxy.mods(amounts, operations_ids)).setWeight(Integer.parseInt(weight)).build());
            } else {
                addAll(all(name).setWeight(Integer.parseInt(weight)).addModifier(attribute, ModifierProxy.mod(amount, operations_id)).build());
            }
        }
    }

    private static void initToolModifiers () {
        for (int index = 0; index < ToolConfig.NAMES.get().size(); index++) {
            String name = ToolConfig.NAMES.get().get(index);
            String weight = ToolConfig.WEIGHTS.get().get(index);
            String attribute = ToolConfig.ATTRIBUTES.get().get(index);
            String amount = ToolConfig.AMOUNTS.get().get(index);
            String operations_id = ToolConfig.OPERATIONS_IDS.get().get(index);
            if (attribute.contains(";")) {
                String[] attributes = attribute.split(";");
                String[] amounts = amount.split(";");
                String[] operations_ids = operations_id.split(";");
                addTool(tool(name).addModifiers(attributes, ModifierProxy.mods(amounts, operations_ids)).setWeight(Integer.parseInt(weight)).build());
            } else {
                addTool(tool(name).setWeight(Integer.parseInt(weight)).addModifier(attribute, ModifierProxy.mod(amount, operations_id)).build());
            }
        }
    }

    private static void initArmorsModifiers () {
        for (int index = 0; index < ArmorConfig.NAMES.get().size(); index++) {
            String name = ArmorConfig.NAMES.get().get(index);
            String weight = ArmorConfig.WEIGHTS.get().get(index);
            String attribute = ArmorConfig.ATTRIBUTES.get().get(index);
            String amount = ArmorConfig.AMOUNTS.get().get(index);
            String operations_id = ArmorConfig.OPERATIONS_IDS.get().get(index);
            if (attribute.contains(";")) {
                String[] attributes = attribute.split(";");
                String[] amounts = amount.split(";");
                String[] operations_ids = operations_id.split(";");
                addArmor(armor(name).setWeight(Integer.parseInt(weight)).addModifiers(attributes, ModifierProxy.mods(amounts, operations_ids)).build());
            } else {
                addArmor(armor(name).setWeight(Integer.parseInt(weight)).addModifier(attribute, ModifierProxy.mod(amount, operations_id)).build());
            }
        }
    }

    private static void initCuriosModifiers () {
        for (int index = 0; index < CuriosConfig.NAMES.get().size(); index++) {
            String name = CuriosConfig.NAMES.get().get(index);
            String weight = CuriosConfig.WEIGHTS.get().get(index);
            String attribute = CuriosConfig.ATTRIBUTES.get().get(index);
            String amount = CuriosConfig.AMOUNTS.get().get(index);
            String operations_id = CuriosConfig.OPERATIONS_IDS.get().get(index);
            if (attribute.contains(";")) {
                String[] attributes = attribute.split(";");
                String[] amounts = amount.split(";");
                String[] operations_ids = operations_id.split(";");
                addCurio(curio(name).setWeight(Integer.parseInt(weight)).addModifiers(attributes, ModifierProxy.mods(amounts, operations_ids)).build());
            } else {
                addCurio(curio(name).setWeight(Integer.parseInt(weight)).addModifier(attribute, ModifierProxy.mod(amount, operations_id)).build());
            }
        }
    }

    static {
        initBowModifiers();
        initAllModifiers();
        initToolModifiers();
        initArmorsModifiers();
        initCuriosModifiers();
    }
}
