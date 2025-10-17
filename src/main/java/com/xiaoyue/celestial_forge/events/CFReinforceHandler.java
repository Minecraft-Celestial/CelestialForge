package com.xiaoyue.celestial_forge.events;

import com.xiaoyue.celestial_core.data.CCDamageTypes;
import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.content.data.AttrReinforce;
import com.xiaoyue.celestial_forge.content.data.ModifierType;
import com.xiaoyue.celestial_forge.content.reinforce.AttributeEntry;
import com.xiaoyue.celestial_forge.data.CFModConfig;
import com.xiaoyue.celestial_forge.register.CFFlags;
import com.xiaoyue.celestial_forge.utils.TypeTestUtils;
import dev.xkmc.l2library.init.events.GeneralEventHandler;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.Locale;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = CelestialForge.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CFReinforceHandler {

    @SubscribeEvent
    public static void onAttrModify(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        CFFlags.DATA_MAP.values().forEach(data -> {
            if (data instanceof AttrReinforce attrData) {
                ModifierType type = TypeTestUtils.getType(stack);
                if (type == null) return;
                if (event.getSlotType().equals(LivingEntity.getEquipmentSlotForItem(stack))) {
                    if (data.hasFlag(stack) && type != ModifierType.CURIO) {
                        for (int i = 0; i < attrData.attrs().size(); i++) {
                            AttributeEntry entry = attrData.attrs().get(i);
                            UUID uuid = MathHelper.getUUIDFromString(data.flag().toLowerCase(Locale.ROOT) + "_" + i);
                            AttributeModifier modifier = new AttributeModifier(uuid, data.flag(), entry.val(), entry.operation());
                            event.addModifier(entry.attr(), modifier);
                        }
                    }
                }
            }
        });
    }

    @SubscribeEvent
    public static void onPickExp(PlayerXpEvent.PickupXp event) {
        float bonus = CFFlags.ECHO_SHARD.getItemsForFlag(event.getEntity()).size() * CFModConfig.COMMON.echoShardPickExpBonus.get().floatValue();
        event.getOrb().value = (int) (event.getOrb().value * (1 + bonus));
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
            float pureStar = CFFlags.PURE_NETHER_STAR.getItemsForFlag(entity).size() * CFModConfig.COMMON.pureStarDamageMultiplier.get().floatValue();
            event.setAmount(event.getAmount() + target.getMaxHealth() * pureStar);
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        LivingEntity target = event.getEntity();
        Entity source = event.getSource().getEntity();
        if (source instanceof Player player) {
            if (player.getAttackStrengthScale(0.5f) >= 0.9f && ModList.get().isLoaded("celestial_core")) {
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
