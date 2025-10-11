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
    private final @Nullable ItemLike mate;
    private final int cost;
    private final Component tooltip;

    public ReinforceData(String flag, String mate, int cost, Component tooltip) {
        this.flag = flag;
        this.mate = ForgeRegistries.ITEMS.getValue(new ResourceLocation(mate));
        this.cost = cost;
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
    public int cost() {
        return cost;
    }

    @Override
    public List<Component> tooltip() {
        return List.of(tooltip);
    }
}
