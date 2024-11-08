package com.xiaoyue.celestial_forge.register;

import com.tterrag.registrate.util.entry.RegistryEntry;
import com.xiaoyue.celestial_core.CelestialCore;
import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.content.item.ModifierBook;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.client.model.generators.ModelFile;

public class CFItems {

    public static final RegistryEntry<ModifierBook> MODIFIER_BOOK;

    public static final RegistryEntry<Block> LARGE_ANVIL;

    static {
        MODIFIER_BOOK = CelestialForge.REGISTRATE.item("modifier_book", p -> new ModifierBook()).register();

        CelestialForge.REGISTRATE.defaultCreativeTab(CelestialCore.TAB.getKey());
        LARGE_ANVIL = CelestialForge.REGISTRATE.block("large_anvil",
                p -> new Block(BlockBehaviour.Properties.copy(Blocks.ANVIL)))
                .blockstate((ctx, pvd) -> pvd.simpleBlock(ctx.get(), pvd.models().getBuilder(ctx.getName())
                        .parent(new ModelFile.UncheckedModelFile(CelestialForge.loc("block/generic/large_anvil")))
                        .texture("0", "block/large_anvil")
                        .texture("1", "block/large_anvil_1")
                ))
                .simpleItem()
                .register();
    }

    public static void register() {
    }
}
