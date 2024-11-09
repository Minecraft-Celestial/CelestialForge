package com.xiaoyue.celestial_forge.register;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.content.block.ForgeTableBlock;
import com.xiaoyue.celestial_forge.content.block.ForgeTableBlockEntity;
import com.xiaoyue.celestial_forge.content.block.ForgeTableRenderer;
import com.xiaoyue.celestial_forge.content.item.ModifierBook;
import com.xiaoyue.celestial_forge.content.registry.LevelingConfig;
import com.xiaoyue.celestial_forge.content.registry.ModifierData;
import com.xiaoyue.celestial_forge.content.registry.UpgradeRecipe;
import dev.xkmc.l2modularblock.DelegateBlock;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;

public class CFItems {

	public static final ResourceKey<Registry<UpgradeRecipe>> UPGRADE =
			CelestialForge.REGISTRATE.makeDatapackRegistry("upgrade", UpgradeRecipe.CODEC, UpgradeRecipe.CODEC);
	public static final ResourceKey<Registry<LevelingConfig>> LEVELING =
			CelestialForge.REGISTRATE.makeDatapackRegistry("leveling", LevelingConfig.CODEC, LevelingConfig.CODEC);
	public static final ResourceKey<Registry<ModifierData>> MODIFIER =
			CelestialForge.REGISTRATE.makeDatapackRegistry("modifier", ModifierData.CODEC, ModifierData.CODEC);

	public static final RegistryEntry<ModifierBook> MODIFIER_BOOK;
	public static final RegistryEntry<DelegateBlock> FORGE_TABLE;
	public static final BlockEntityEntry<ForgeTableBlockEntity> BE_FORGE;

	static {
		MODIFIER_BOOK = CelestialForge.REGISTRATE.item("modifier_book", p -> new ModifierBook()).register();

		FORGE_TABLE = CelestialForge.REGISTRATE.block("forge_table",
						p -> DelegateBlock.newBaseBlock(p, new ForgeTableBlock(), ForgeTableBlock.TE))
				.initialProperties(() -> Blocks.ANVIL)
				.blockstate((ctx, pvd) -> pvd.simpleBlock(ctx.get(), pvd.models().cubeBottomTop(
						ctx.getName(), CelestialForge.loc("block/forge_table_side"),
						CelestialForge.loc("block/forge_table_bottom"),
						CelestialForge.loc("block/forge_table_top"))
				))
				.tag(BlockTags.MINEABLE_WITH_PICKAXE)
				.simpleItem()
				.register();

		BE_FORGE = CelestialForge.REGISTRATE.blockEntity("forge_table", ForgeTableBlockEntity::new)
				.renderer(() -> ForgeTableRenderer::new)
				.validBlock(FORGE_TABLE).register();
	}

	public static void register() {
	}
}
