package com.xiaoyue.celestial_forge.content.overlay;

import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public record TileTooltip(List<ItemStack> items) implements TooltipComponent {

}
