package com.xiaoyue.celestial_forge.content.block;

import com.xiaoyue.celestial_forge.content.overlay.InfoTile;
import com.xiaoyue.celestial_forge.content.overlay.TileTooltip;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import dev.xkmc.l2library.base.tile.BaseBlockEntity;
import dev.xkmc.l2library.base.tile.BaseContainerListener;
import dev.xkmc.l2modularblock.tile_api.BlockContainer;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

@SerialClass
public class ForgeTableBlockEntity extends BaseBlockEntity implements BaseContainerListener, BlockContainer, InfoTile {

	@SerialClass.SerialField
	private final ForgeTableBlockContainer container = new ForgeTableBlockContainer()
			.add(this).setMax(1);

	public ForgeTableBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public List<Container> getContainers() {
		return List.of(container);
	}

	@Override
	public void notifyTile() {
		sync();
		setChanged();
	}

	public ItemStack get(int i) {
		return container.getItem(i);
	}

	public void popAllItems(Player player) {
		if (level == null || level.isClientSide()) return;
		var pos = getBlockPos().above();
		for (ItemStack stack : container.getAsList()) {
			Block.popResource(level, pos, stack.copy());
		}
		container.clearContent();
	}

	public boolean canAccept(ItemStack stack) {
		if (container.isEmpty()) {
			var ins = ModifierUtils.getModifier(stack);
			if (ins != null) return ins.needUpgrade();
			return ModifierUtils.getType(stack) != null;
		}
		//TODO check if stack is part of the recipe
		return false;
	}

	public void addItem(ItemStack stack) {
		container.addItem(stack);
	}

	@Override
	public TileTooltip getImage() {
		return null;//TODO
		// new TileTooltip(List.of());
	}

	@Override
	public List<Component> lines() {
		return List.of();//TODO
	}
}