package com.xiaoyue.celestial_forge;

import com.xiaoyue.celestial_forge.config.CommonConfig;
import com.xiaoyue.celestial_forge.register.CFGroups;
import com.xiaoyue.celestial_forge.register.CFItems;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(CelestialForge.MODID)
public class CelestialForge
{
    public static final String MODID = "celestial_forge";

    public CelestialForge()
    {
       IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

       CFItems.ITEM.register(modEventBus);
       CFGroups.CREATIVE_TAB.register(modEventBus);

       CommonConfig.initConfig();

       MinecraftForge.EVENT_BUS.register(this);
    }
}
