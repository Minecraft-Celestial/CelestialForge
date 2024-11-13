package com.xiaoyue.celestial_forge.data;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.xiaoyue.celestial_forge.CelestialForge;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.Locale;

public enum CFLang {
    MODIFIER_BOOK("%s Modifier Book", 1),
    MODIFIER_BOOK_INFO("§7Use 22 levels of experience on the anvil to change the quality of the item", 0),
    EQUIPPED_TYPE("§7Applicable to: §9Armor", 0),
    TOOLS_TYPE("§7Applicable to: §9Tools", 0),
    RANGED_TYPE("§7Applicable to: §9Ranged", 0),
    CURIO_TYPE("§7Applicable to: §9Curio", 0),
    WEAPON_TYPE("§7Applicable to: §9Weapon", 0),
    ALL_TYPE("§7Applicable to: §9All", 0),
    TABLE_LINES_1("Place equipment and provide materials", 0),
    TABLE_LINES_2("to get entries or upgrade existing ones", 0),
    TABLE_LINES_3("Follow the picture prompts to place the material", 0),
    TABLE_LINES_4("And prepare enough experience to upgrade the entry", 0),
    TABLE_LINES_5("This upgrade is expected to consume %s XP", 1),
    TABLE_LINES_6("Right-click with a hammer to complete the forge", 0),
    TABLE_LINES_7("This item cannot hold modifier", 0),
    ;

    final String id;
    final String def;
    final int count;

    CFLang(String def, int count) {
        this.id = CelestialForge.MODID + ".tooltip." + name().toLowerCase(Locale.ROOT);
        this.def = def;
        this.count = count;
    }

    public MutableComponent get(Object... objs) {
        if (objs.length != this.count) {
            throw new IllegalArgumentException("for " + name() + ": expect " + count + " parameters, got " + objs.length);
        } else {
            return Component.translatable(id, objs);
        }
    }

    public static MutableComponent num(int v) {
        return Component.literal(v + "").withStyle(ChatFormatting.AQUA);
    }

    public static void addLang(RegistrateLangProvider pvd) {
        for (var value : values()) {
            pvd.add(value.id, value.def);
        }
    }
}
