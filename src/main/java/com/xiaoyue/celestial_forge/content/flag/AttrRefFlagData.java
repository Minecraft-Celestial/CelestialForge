package com.xiaoyue.celestial_forge.content.flag;

import com.xiaoyue.celestial_forge.content.data.ModifierType;
import com.xiaoyue.celestial_forge.data.CFLang;
import com.xiaoyue.celestial_forge.register.CFFlags;
import com.xiaoyue.celestial_forge.utils.TypeTestUtils;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
import java.util.function.BiConsumer;

public class AttrRefFlagData extends RefFlagData {

    public final @Nullable Attribute attr;
    public final double val;
    public final int op;

    public AttrRefFlagData(String flag, String mate, int cost, Component tooltip, @Nullable Attribute attr, double val, int op) {
        super(flag, mate, cost, tooltip);
        this.attr = attr;
        this.val = val;
        this.op = op;
    }

    public static AttrRefFlagData mul(String flag, String mate, int cost, @Nullable Attribute attr, double val) {
        return new AttrRefFlagData(flag, mate, cost, CFFlags.item(mate, CFLang.ATTR_BONUS.get(CFLang.attr(attr), CFLang.per(val))
                .withStyle(ChatFormatting.GRAY)) , attr, val, 1);
    }

    public void onEvent(ItemAttributeModifierEvent event) {
        ItemStack stack = event.getItemStack();
        ModifierType type = TypeTestUtils.getType(stack);
        if (attr == null || type == null) return;
        if (event.getSlotType().equals(LivingEntity.getEquipmentSlotForItem(stack))) {
            if (hasFlag(stack) && type != ModifierType.CURIO) {
                UUID uuid = MathHelper.getUUIDFromString(flag);
                AttributeModifier modifier = new AttributeModifier(uuid, flag, val, AttributeModifier.Operation.fromValue(op));
                event.addModifier(attr, modifier);
            }
        }
    }

    public void onCurioEvent(ItemStack stack, BiConsumer<Attribute, AttributeModifier> action) {
        ModifierType type = TypeTestUtils.getType(stack);
        if (attr == null || type == null) return;
        if (hasFlag(stack) && type == ModifierType.CURIO) {
            UUID uuid = MathHelper.getUUIDFromString(flag);
            AttributeModifier modifier = new AttributeModifier(uuid, flag, val, AttributeModifier.Operation.fromValue(op));
            action.accept(attr, modifier);
        }
    }
}
