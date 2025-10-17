package com.xiaoyue.celestial_forge.content.data;

import com.xiaoyue.celestial_forge.content.reinforce.AttributeEntry;
import com.xiaoyue.celestial_forge.content.reinforce.IReinforce;
import com.xiaoyue.celestial_forge.data.CFLang;
import com.xiaoyue.celestial_forge.register.CFFlags;
import dev.xkmc.l2library.serial.config.BaseConfig;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;

@SerialClass
public class AttrReinforce extends BaseConfig implements IReinforce {

    @SerialClass.SerialField
    private String flag;
    @SerialClass.SerialField
    private Item mate;
    @SerialClass.SerialField
    private Item temp;
    @SerialClass.SerialField
    private String tooltip;
    @SerialClass.SerialField
    private List<AttributeEntry> attrs = new ArrayList<>();

    public AttrReinforce(String flag, Item temp, Item mate, String tooltip, List<AttributeEntry> attrs) {
        this.flag = flag;
        this.temp = temp;
        this.mate = mate;
        this.tooltip = tooltip;
        this.attrs = attrs;
    }

    public AttrReinforce() {

    }

    public String flagName() {
        return flag;
    }

    @Override
    public String flag() {
        return CFFlags.flagName(flag);
    }

    @Override
    public Ingredient temp() {
        return Ingredient.of(temp);
    }

    @Override
    public Ingredient mate() {
        return Ingredient.of(mate);
    }

    @Override
    public List<Component> tooltip() {
        if (!tooltip.isEmpty()) {
            return List.of(Component.translatable(tooltip));
        }
        List<Component> list = new ArrayList<>();
        String key = ForgeRegistries.ITEMS.getKey(mate).toString();
        if (attrs.size() > 1) {
            list.add(CFFlags.item(key));
            for (AttributeEntry entry : attrs) {
                list.add(CFLang.ATTR_BONUS.get(CFLang.attr(entry.attr()), CFLang.per(entry.val()))
                        .withStyle(ChatFormatting.GRAY));
            }
        } else {
            list.add(CFFlags.item(key, CFLang.ATTR_BONUS.get(CFLang.attr(attrs.get(0).attr()), CFLang.per(attrs.get(0).val()))
                    .withStyle(ChatFormatting.GRAY)));
        }
        return list;
    }

    public List<AttributeEntry> attrs() {
        return attrs;
    }
}
