package com.xiaoyue.celestial_forge.events;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientHandler {

    @SubscribeEvent
    public static void addReloadLis(AddReloadListenerEvent event) {
    }
}
