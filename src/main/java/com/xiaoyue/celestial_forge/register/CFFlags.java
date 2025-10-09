package com.xiaoyue.celestial_forge.register;

import com.xiaoyue.celestial_forge.content.flag.AttrRefFlagData;
import com.xiaoyue.celestial_forge.content.flag.RefFlagData;
import com.xiaoyue.celestial_forge.data.CFLang;
import com.xiaoyue.celestial_forge.data.CFModConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class CFFlags {

    public static final List<RefFlagData> DATA_LIST = new ArrayList<>();

    public static final RefFlagData EARTH_CORE = new RefFlagData("CelestialForge_EarthCore", CCore("earth_core"), 3,
            item(CCore("earth_core"), CFLang.BREAK_SPEED.get(CFLang.per(CFModConfig.COMMON.earthCoreMiningSpeed.get()))));

    public static final RefFlagData VOID_ESSENCE = new RefFlagData("CelestialForge_VoidEssence", CCore("void_essence"), 6,
            item(CCore("void_essence"), CFLang.EXTRA_DAMAGE.get(CFLang.num(CFModConfig.COMMON.voidEssenceExtraDamage.get()))));

    public static final RefFlagData DEATH_ESSENCE = new RefFlagData("CelestialForge_DeathEssence", CCore("death_essence"), 5,
            item(CCore("death_essence"), CFLang.DAMAGE_HEAL.get(CFLang.per(CFModConfig.COMMON.deathEssenceDamageHeal.get()))));

    public static final RefFlagData PURE_STAR = new RefFlagData("CelestialForge_PureStar", CCore("pure_star"), 5,
            item(CCore("pure_star"), CFLang.UNDEAD_EXTRA_DAMAGE.get(CFLang.per(CFModConfig.COMMON.pureStarDamageMultiplier.get()))));

    public static final AttrRefFlagData SOARING_WINGS = AttrRefFlagData.mul("CelestialForge_SoaringWings", CCore("soaring_wings"), 8,
            Attributes.MOVEMENT_SPEED, CFModConfig.COMMON.soaringWingsMovementSpeedBonus.get());

    public static final AttrRefFlagData HEART_FRAGMENT = AttrRefFlagData.mul("CelestialForge_HeartFragment", CCore("heart_fragment"), 7,
            getAttr(CCore("regen_rate")), CFModConfig.COMMON.heartFragmentRegenRateBonus.get());

    @Nullable
    public static Attribute getAttr(String id) {
        return ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(id));
    }

    public static MutableComponent item(String like, MutableComponent text) {
        Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(like));
        if (item == null) {
            return Component.empty();
        }
        UnaryOperator<Style> style = item.getDefaultInstance().getRarity().getStyleModifier();
        MutableComponent name = brackets(item.getDescription().copy().withStyle(style));
        return name.append(text.withStyle(ChatFormatting.GRAY));
    }

    private static MutableComponent brackets(MutableComponent text) {
        return Component.literal("☆").append(text).append(Component.literal("☆ ")).withStyle(ChatFormatting.GRAY);
    }

    public static String CCore(String id) {
        return "celestial_core:" + id;
    }

    public static void register() {

    }
}
