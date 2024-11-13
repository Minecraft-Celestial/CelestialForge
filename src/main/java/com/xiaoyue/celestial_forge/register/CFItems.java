package com.xiaoyue.celestial_forge.register;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.ItemEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.content.block.ForgeTableBlock;
import com.xiaoyue.celestial_forge.content.block.ForgeTableBlockEntity;
import com.xiaoyue.celestial_forge.content.block.ForgeTableRenderer;
import com.xiaoyue.celestial_forge.content.item.ModifierBook;
import dev.xkmc.l2modularblock.DelegateBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;

public class CFItems {

	public static final ItemEntry<Item> HAMMER;
	public static final RegistryEntry<DelegateBlock> FORGE_TABLE;
	public static final BlockEntityEntry<ForgeTableBlockEntity> BE_FORGE;
	public static final RegistryEntry<ModifierBook> MODIFIER_BOOK;

	static {
		HAMMER = CelestialForge.REGISTRATE.item("hammer", p -> new Item(new Item.Properties()))
				.model((ctx, pvd) -> pvd.handheld(ctx, pvd.modLoc("item/" + ctx.getName())))
				.register();

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

		MODIFIER_BOOK = CelestialForge.REGISTRATE.item("modifier_book", p -> new ModifierBook()).register();
	}

	public static void register() {
	}
}
