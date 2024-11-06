package com.xiaoyue.celestial_forge.register;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.content.item.ModifierBook;

public class CFItems {

    public static final RegistryEntry<ModifierBook> MODIFIER_BOOK;

    static {
        MODIFIER_BOOK = CelestialForge.REGISTRATE.item("modifier_book", p -> new ModifierBook()).register();
    }

    public static void register() {
    }
}
