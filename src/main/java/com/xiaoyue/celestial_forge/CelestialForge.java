package com.xiaoyue.celestial_forge;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.xiaoyue.celestial_forge.content.data.ModifierConfig;
import com.xiaoyue.celestial_forge.content.item.ModifierBook;
import com.xiaoyue.celestial_forge.data.CFConfigGen;
import com.xiaoyue.celestial_forge.data.CFLang;
import com.xiaoyue.celestial_forge.data.CFTagGen;
import com.xiaoyue.celestial_forge.data.CommonConfig;
import com.xiaoyue.celestial_forge.register.CFItems;
import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.serial.config.ConfigTypeEntry;
import dev.xkmc.l2library.serial.config.PacketHandlerWithConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;

import static com.xiaoyue.celestial_forge.CelestialForge.MODID;

@Mod(CelestialForge.MODID)
@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CelestialForge {

	public static final String MODID = "celestial_forge";
	public static final Logger LOGGER = LogUtils.getLogger();
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);
	public static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(
			loc("main"), 1
	);

	public static final ConfigTypeEntry<ModifierConfig> MODIFIER = new ConfigTypeEntry<>(HANDLER, "modifier", ModifierConfig.class);

	public static final RegistryEntry<CreativeModeTab> MODIFIER_TAB = REGISTRATE.buildModCreativeTab(
			"modifier_books", "Celestial Forge Modifier Book", e ->
					e.icon(() -> CFItems.MODIFIER_BOOK.get().getDefaultInstance())
							.displayItems((parameters, output) ->
									output.acceptAll(ModifierBook.getStacksForCreativeTab())).build());

	public CelestialForge() {
		CFItems.register();
		CommonConfig.initConfig();
	}

	public static ResourceLocation loc(String id) {
		return new ResourceLocation(MODID, id);
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void gatherData(GatherDataEvent event) {
		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, CFTagGen::onItemTagGen);
		REGISTRATE.addDataGenerator(ProviderType.LANG, CFLang::addLang);
		event.getGenerator().addProvider(event.includeServer(), new CFConfigGen(event.getGenerator()));
	}
}
