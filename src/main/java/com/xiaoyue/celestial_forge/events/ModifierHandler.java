package com.xiaoyue.celestial_forge.events;

import com.xiaoyue.celestial_forge.config.CommonConfig;
import com.xiaoyue.celestial_forge.content.item.ModifierBook;
import com.xiaoyue.celestial_forge.content.modifier.Modifier;
import com.xiaoyue.celestial_forge.register.CModifiers;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

import static com.xiaoyue.celestial_forge.CelestialForge.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModifierHandler {

    @SubscribeEvent
    public static void onLivingChanged(LivingEquipmentChangeEvent event) {
        ItemStack from = event.getFrom();
        ItemStack to = event.getTo();
        LivingEntity entity = event.getEntity();
        EquipmentSlot slot = event.getSlot();
        Modifier fromMod = ModifierUtils.getModifier(from);
        if (fromMod != null) {
            ModifierUtils.removeEquipmentModifier(entity, fromMod, slot);
        }
        Modifier toMod = ModifierUtils.getModifier(to);
        if (toMod == null) {
            toMod = ModifierUtils.rollModifier(to, entity.getRandom());
            if (toMod == null) return;
            ModifierUtils.setModifier(to, toMod);
        }
        ModifierUtils.applyEquipmentModifier(entity, toMod, slot);
    }

    @SubscribeEvent
    public static void onCurioChanged(CurioChangeEvent event) {
        ItemStack from = event.getFrom();
        ItemStack to = event.getTo();
        Modifier fromModifier = ModifierUtils.getModifier(from);
        if (fromModifier != null) {
            ModifierUtils.removeCurioModifier(event.getEntity(), fromModifier, event.getIdentifier(), event.getSlotIndex());
        }
        Modifier toModifier = ModifierUtils.getModifier(to);
        if (toModifier == null) {
            toModifier = ModifierUtils.rollModifier(to, event.getEntity().getRandom());
            if (toModifier == null) return;
            ModifierUtils.setModifier(to, toModifier);
        }
        ModifierUtils.applyCurioModifier(event.getEntity(), toModifier, event.getIdentifier(), event.getSlotIndex());
    }

    @SubscribeEvent
    public static void addTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (ModifierUtils.canHaveModifiers(stack)) {
            Modifier modifier = ModifierUtils.getModifier(stack);
            if (modifier != null) {
                event.getToolTip().addAll(modifier.getInfoLines());
            }
        }
    }

    @SubscribeEvent
    public static void ModifierRecipe(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft().copy();
        ItemStack right = event.getRight();

        if (ModifierUtils.canHaveModifiers(left)) {
            if (right.getItem() instanceof ModifierBook) {
                Modifier modifier = CModifiers.modifiers.get(new ResourceLocation(right.getTag().getString(ModifierUtils.bookTagName)));
                ModifierUtils.setModifier(left, modifier);
                event.setMaterialCost(1);
                event.setOutput(left);
                event.setCost(22);
            }
        }

        if (ModifierUtils.hasModifier(left)) {
            if (CommonConfig.items.contains(right.getItem())) {
                ModifierUtils.prepareReroll(left);
                event.setMaterialCost(1);
                event.setOutput(left);
                event.setCost(1);
            }
        }
    }
}
