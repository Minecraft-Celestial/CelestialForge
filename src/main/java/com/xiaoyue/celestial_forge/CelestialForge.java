package com.xiaoyue.celestial_forge;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.xiaoyue.celestial_forge.config.CommonConfig;
import com.xiaoyue.celestial_forge.content.item.ModifierBook;
import com.xiaoyue.celestial_forge.register.CFItems;
import dev.xkmc.l2library.base.L2Registrate;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

@Mod(CelestialForge.MODID)
public class CelestialForge {

    public static final String MODID = "celestial_forge";
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final L2Registrate REGISTRATE = new L2Registrate(MODID);

    public static final RegistryEntry<CreativeModeTab> MODIFIER_TAB = REGISTRATE.buildModCreativeTab(
            "modifier_books", "Celestial Forge Modifier Book", e ->
                    e.icon(() -> CFItems.MODIFIER_BOOK.get().getDefaultInstance())
                            .displayItems((parameters, output) ->
                                    output.acceptAll(ModifierBook.getStacksForCreativeTab())).build());

    public CelestialForge() {
        CFItems.register();

        CommonConfig.initConfig();
        CommonConfig.initModifierAllConfig();
    }
}
