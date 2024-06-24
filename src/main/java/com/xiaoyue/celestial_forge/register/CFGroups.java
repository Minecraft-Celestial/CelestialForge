package com.xiaoyue.celestial_forge.register;

import com.xiaoyue.celestial_forge.content.item.ModifierBook;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import static com.xiaoyue.celestial_forge.CelestialForge.MODID;
import static com.xiaoyue.celestial_forge.register.CFItems.MODIFIER_BOOK;

public class CFGroups {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<CreativeModeTab> MODIFIER_BOOKS = CREATIVE_TAB.register("modifier_books", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
            .title(Component.translatable("itemGroup.celestial_forge.modifier_books"))
            .icon(() -> MODIFIER_BOOK.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.acceptAll(ModifierBook.getStacksForCreativeTab());
            }).build());
}
