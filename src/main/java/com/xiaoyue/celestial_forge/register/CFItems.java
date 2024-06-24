package com.xiaoyue.celestial_forge.register;

import com.xiaoyue.celestial_forge.content.item.ModifierBook;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.xiaoyue.celestial_forge.CelestialForge.MODID;

public class CFItems {

    public static final DeferredRegister<Item> ITEM = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    public static final RegistryObject<Item> MODIFIER_BOOK = ITEM.register("modifier_book", ModifierBook::new);

}
