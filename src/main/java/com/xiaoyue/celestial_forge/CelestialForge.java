package com.xiaoyue.celestial_forge;

import com.mojang.logging.LogUtils;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.xiaoyue.celestial_forge.content.data.*;
import com.xiaoyue.celestial_forge.content.item.ModifierBook;
import com.xiaoyue.celestial_forge.data.*;
import com.xiaoyue.celestial_forge.register.CFItems;
import com.xiaoyue.celestial_forge.register.CFRecipes;
import com.xiaoyue.celestial_forge.utils.CurioUtils;
import com.xiaoyue.celestial_forge.utils.TypeTestUtils;
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
	public static final PacketHandlerWithConfig HANDLER = new PacketHandlerWithConfig(loc("main"), 1);

	public static final ConfigTypeEntry<UpgradeRecipe> COST = new ConfigTypeEntry<>(HANDLER, "cost", UpgradeRecipe.class);
	public static final ConfigTypeEntry<LevelingConfig> LEVELING = new ConfigTypeEntry<>(HANDLER, "leveling", LevelingConfig.class);
	public static final ConfigTypeEntry<ModifierData> MODIFIER = new ConfigTypeEntry<>(HANDLER, "modifier", ModifierData.class);

	public static final ConfigTypeEntry<DataReinforce> REINFORCE = new ConfigTypeEntry<>(HANDLER, "reinforce", DataReinforce.class);

	public static final RegistryEntry<CreativeModeTab> MODIFIER_TAB = REGISTRATE.buildModCreativeTab(
			"tab", "Celestial Forge Tab", e -> e.icon(() -> CFItems.MODIFIER_BOOK.get().getDefaultInstance())
					.displayItems((parameters, output) -> output.acceptAll(ModifierBook.getStacksForCreativeTab())).build());

	public CelestialForge() {
		CFItems.register();
		CFRecipes.register();
		CFModConfig.initConfig();
		CurioUtils.register();
		REGISTRATE.addDataGenerator(ProviderType.ITEM_TAGS, CFTagGen::onItemTagGen);
		REGISTRATE.addDataGenerator(ProviderType.LANG, CFLang::addLang);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, CFRecipeGen::onRecipeGen);
		HANDLER.addAfterReloadListener(ModifierDataHolder::rebuild);
		HANDLER.addAfterReloadListener(ReinforceDataHolder::rebuild);
		HANDLER.addAfterReloadListener(TypeTestUtils::clearCache);
	}

	public static ResourceLocation loc(String id) {
		return new ResourceLocation(MODID, id);
	}

	@SubscribeEvent(priority = EventPriority.HIGH)
	public static void gatherData(GatherDataEvent event) {
		event.getGenerator().addProvider(event.includeServer(), new CFConfigGen(event.getGenerator()));
	}

}
