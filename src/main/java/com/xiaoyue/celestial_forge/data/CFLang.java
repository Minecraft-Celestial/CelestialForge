package com.xiaoyue.celestial_forge.data;

import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.xiaoyue.celestial_forge.CelestialForge;
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

    public static void addLang(RegistrateLangProvider pvd) {
        for (var value : values()) {
            pvd.add(value.id, value.def);
        }
    }
}
