package com.xiaoyue.celestial_forge.content.data;

import com.xiaoyue.celestial_forge.content.reinforce.AttributeEntry;
import com.xiaoyue.celestial_forge.content.reinforce.IReinforce;
import com.xiaoyue.celestial_forge.data.CFLang;
import com.xiaoyue.celestial_forge.register.CFFlags;
import dev.xkmc.l2core.serial.config.BaseConfig;
import dev.xkmc.l2serial.serialization.marker.SerialClass;
import dev.xkmc.l2serial.serialization.marker.SerialField;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

@SerialClass
public class DataReinforce extends BaseConfig implements IReinforce {

    @SerialField
    private String flag;
    @SerialField
    private Ingredient mate;
    @SerialField
    private Ingredient temp;
    @SerialField
    private String tooltip;
    @SerialField
    private List<ModifierType> types = new ArrayList<>();
    @SerialField
    private List<AttributeEntry> attrs = new ArrayList<>();

    public DataReinforce(String flag, Ingredient temp, Ingredient mate, String tooltip, List<ModifierType> types, List<AttributeEntry> attrs) {
        this.flag = flag;
        this.temp = temp;
        this.mate = mate;
        this.tooltip = tooltip;
        this.types = types;
        this.attrs = attrs;
    }

    public DataReinforce() {

    }

    public String flagName() {
        return flag;
    }

    @Override
    public List<ModifierType> types() {
        return types;
    }

    @Override
    public String flag() {
        return CFFlags.flagName(flag);
    }

    @Override
    public Ingredient temp() {
        return temp;
    }

    @Override
    public Ingredient mate() {
        return mate;
    }

    @Override
    public List<Component> tooltip() {
        if (!tooltip.isEmpty()) {
            return List.of(Component.translatable(tooltip));
        }
        List<Component> list = new ArrayList<>();
        String key = BuiltInRegistries.ITEM.getKey(mate.getItems()[0].getItem()).toString();
        if (attrs.size() > 1) {
            list.add(CFFlags.item(key));
            for (AttributeEntry entry : attrs) {
                if (!entry.operation().equals(AttributeModifier.Operation.ADD_VALUE)) {
                    list.add(CFLang.ATTR_BONUS.get(CFLang.attr(entry.attr()), CFLang.per(entry.val()))
                            .withStyle(ChatFormatting.GRAY));
                } else {
                    list.add(CFLang.ATTR_BONUS.get(CFLang.attr(entry.attr()), CFLang.num(entry.val()))
                            .withStyle(ChatFormatting.GRAY));
                }
            }
        } else {
            AttributeEntry first = attrs.getFirst();
            if (!first.operation().equals(AttributeModifier.Operation.ADD_VALUE)) {
                list.add(CFFlags.item(key, CFLang.ATTR_BONUS.get(CFLang.attr(first.attr()), CFLang.per(first.val()))
                        .withStyle(ChatFormatting.GRAY)));
            } else {
                list.add(CFFlags.item(key, CFLang.ATTR_BONUS.get(CFLang.attr(first.attr()), CFLang.num(first.val()))
                        .withStyle(ChatFormatting.GRAY)));
            }
        }
        return list;
    }

    public List<AttributeEntry> attrs() {
        return attrs;
    }
}
