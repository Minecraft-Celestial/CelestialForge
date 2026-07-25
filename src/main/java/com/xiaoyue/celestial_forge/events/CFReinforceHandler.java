package com.xiaoyue.celestial_forge.events;

import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.content.data.DataReinforce;
import com.xiaoyue.celestial_forge.content.data.ModifierType;
import com.xiaoyue.celestial_forge.content.reinforce.AttributeEntry;
import com.xiaoyue.celestial_forge.data.CFModConfig;
import com.xiaoyue.celestial_forge.register.CFFlags;
import com.xiaoyue.celestial_forge.utils.TypeTestUtils;
import com.xiaoyue.celestial_invoker.content.common.Bindings;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;

@EventBusSubscriber(modid = CelestialForge.MODID)
public class CFReinforceHandler {

    @SubscribeEvent
    public static void onAttrModify(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        EquipmentSlot slot = Bindings.getSlot(stack);
        CFFlags.DATA_MAP.values().forEach(data -> {
            if (data instanceof DataReinforce attrData) {
                ModifierType type = TypeTestUtils.getType(stack);
                if (type == null) return;
                if (data.hasFlag(stack) && type != ModifierType.CURIO) {
                    for (int i = 0; i < attrData.attrs().size(); i++) {
                        AttributeEntry entry = attrData.attrs().get(i);
                        AttributeModifier modifier = new AttributeModifier(ResourceLocation.parse(data.flag()), entry.val(), entry.operation());
                        event.addModifier(entry.attr(), modifier, EquipmentSlotGroup.bySlot(slot));
                    }
                }
            }
        });
    }

    @SubscribeEvent
    public static void onPickExp(PlayerXpEvent.PickupXp event) {
        CFFlags.ECHO_SHARD.postItemsFlag(event.getEntity(), (stack, size) -> {
            float config = CFModConfig.COMMON.echoShardPickExpBonus.get().floatValue();
            event.getOrb().value = (int) (event.getOrb().value * (1 + size * config));
        });
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onPlayerBreak(PlayerEvent.BreakSpeed event) {
        CFFlags.EARTH_CORE.postItemsFlag(event.getEntity(), (stack, size) -> {
            float config = CFModConfig.COMMON.earthCoreMiningSpeed.get().floatValue();
            event.setNewSpeed(event.getOriginalSpeed() * (1 + size * config));
        });
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingIncomingDamageEvent event) {
        LivingEntity target = event.getEntity();
        Entity source = event.getSource().getEntity();
        if (source instanceof LivingEntity attacker && target.getType().is(EntityTypeTags.UNDEAD)) {
            CFFlags.PURE_NETHER_STAR.postItemsFlag(attacker, (stack, size) -> {
                float config = CFModConfig.COMMON.pureStarDamageFactor.get().floatValue();
                event.setAmount(event.getAmount() + target.getMaxHealth() * size * config);
            });
        }
    }

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent.Pre event) {
        LivingEntity target = event.getEntity();
        Entity attacker = event.getSource().getEntity();
        if (attacker instanceof Player player) {
            CFFlags.VOID_ESSENCE.postItemsFlag(player, (stack, size) -> {
                if (player.getLastHurtMobTimestamp() <= 1 && player.getAttackStrengthScale(0.5f) > 0.9f && ModList.get().isLoaded("celestial_core")) {
                    float config = CFModConfig.COMMON.voidEssenceExtraDamage.get().floatValue();
                    // GeneralEv.schedule(() -> target.hurt(CCDamageTypes.abyss(player), size * config));
                }
            });
        }
        if (attacker instanceof LivingEntity entity) {
            CFFlags.DEATH_ESSENCE.postItemsFlag(entity, (stack, size) -> {
                float config = CFModConfig.COMMON.deathEssenceDamageHeal.get().floatValue();
                entity.heal(event.getNewDamage() * size * config);
            });
        }
    }
}
