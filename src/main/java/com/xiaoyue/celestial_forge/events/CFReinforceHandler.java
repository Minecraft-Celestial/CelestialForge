package com.xiaoyue.celestial_forge.events;

import com.xiaoyue.celestial_core.data.CCDamageTypes;
import com.xiaoyue.celestial_forge.content.flag.AttrRefFlagData;
import com.xiaoyue.celestial_forge.data.CFLang;
import com.xiaoyue.celestial_forge.data.CFModConfig;
import com.xiaoyue.celestial_forge.register.CFFlags;
import dev.xkmc.l2library.init.events.GeneralEventHandler;
import net.minecraft.ChatFormatting;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

public class CFReinforceHandler {

    public static void register() {
        if (ModList.get().isLoaded("celestial_core")) MinecraftForge.EVENT_BUS.register(CFReinforceHandler.class);
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        CFFlags.DATA_LIST.forEach(data -> {
            if (data.mate == null) return;
            if (event.getItemStack().is(data.mate.asItem())) {
                event.getToolTip().add(CFLang.REF_TEXT.get().withStyle(ChatFormatting.GRAY));
            }
        });
    }

    @SubscribeEvent
    public static void onAttrModify(ItemAttributeModifierEvent event) {
        CFFlags.DATA_LIST.forEach(data -> {
            if (data instanceof AttrRefFlagData attrData) {
                attrData.onEvent(event);
            }
        });
    }

    @SubscribeEvent
    public static void onAnvilUpdate(AnvilUpdateEvent event) {
        CFFlags.DATA_LIST.forEach(flag -> flag.onAnvilUpdate(event));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerBreak(PlayerEvent.BreakSpeed event) {
        float bonus = CFFlags.EARTH_CORE.getItemsForFlag(event.getEntity()).size() * CFModConfig.COMMON.earthCoreMiningSpeed.get().floatValue();
        event.setNewSpeed(event.getOriginalSpeed() * (1 + bonus));
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity target = event.getEntity();
        Entity source = event.getSource().getEntity();
        if (source instanceof LivingEntity entity) {
            float pureStar = CFFlags.PURE_STAR.getItemsForFlag(entity).size() * CFModConfig.COMMON.pureStarDamageMultiplier.get().floatValue();
            event.setAmount(event.getAmount() + target.getMaxHealth() * pureStar);
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity target = event.getEntity();
        Entity source = event.getSource().getEntity();
        if (source instanceof Player player) {
            if (player.getAttackStrengthScale(0.5f) >= 0.9f) {
                float voidEssence = CFFlags.VOID_ESSENCE.getItemsForFlag(player).size() * CFModConfig.COMMON.voidEssenceExtraDamage.get().floatValue();
                GeneralEventHandler.schedule(() -> target.hurt(CCDamageTypes.abyss(player), voidEssence));
            }
        }
        if (source instanceof LivingEntity entity) {
            float deathEssence = CFFlags.DEATH_ESSENCE.getItemsForFlag(entity).size() * CFModConfig.COMMON.deathEssenceDamageHeal.get().floatValue();
            entity.heal(deathEssence * event.getAmount());
        }
    }
}
