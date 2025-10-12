package com.xiaoyue.celestial_forge.content.reinforce;

import com.xiaoyue.celestial_forge.register.CFFlags;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReinforceData implements IReinforce {

    private final String flag;
    private final @Nullable ItemLike temp, mate;
    private final Component tooltip;

    public ReinforceData(String flag, String mate, ItemLike temp, Component tooltip) {
        this.flag = flag;
        this.temp = temp;
        this.mate = ForgeRegistries.ITEMS.getValue(new ResourceLocation(mate));
        this.tooltip = tooltip;
        CFFlags.DATA_MAP.put(flag, this);
    }

    public ReinforceData(String flag, String mate, String temp, Component tooltip) {
        this.flag = flag;
        this.temp = ForgeRegistries.ITEMS.getValue(new ResourceLocation(temp));
        this.mate = ForgeRegistries.ITEMS.getValue(new ResourceLocation(mate));
        this.tooltip = tooltip;
        CFFlags.DATA_MAP.put(flag, this);
    }

    @Override
    public String flag() {
        return flag;
    }

    @Override
    public @Nullable Ingredient mate() {
        return mate == null ? null : Ingredient.of(mate);
    }

    @Override
    public @Nullable Ingredient temp() {
        return temp == null ? null : Ingredient.of(temp);
    }

    @Override
    public List<Component> tooltip() {
        return List.of(tooltip);
    }
}
