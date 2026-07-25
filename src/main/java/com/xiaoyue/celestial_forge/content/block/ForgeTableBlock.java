package com.xiaoyue.celestial_forge.content.block;

import com.xiaoyue.celestial_forge.register.CFItems;
import dev.xkmc.l2modularblock.impl.BlockEntityBlockMethodImpl;
import dev.xkmc.l2modularblock.mult.UseItemOnBlockMethod;
import dev.xkmc.l2modularblock.type.BlockMethod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ForgeTableBlock implements UseItemOnBlockMethod {

	public static final BlockMethod TE = new BlockEntityBlockMethodImpl<>(CFItems.BE_FORGE, ForgeTableBlockEntity.class);

	@Override
	public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult result) {
		if (level.getBlockEntity(pos) instanceof ForgeTableBlockEntity be) {
			if (stack.isEmpty()) {
				if (!level.isClientSide()) {
					be.popAllItems(player);
				}
				return ItemInteractionResult.SUCCESS;
			} else if (stack.is(CFItems.HAMMER.get())) {
				if (!level.isClientSide()) {
					be.activate(player);
				}
				return ItemInteractionResult.SUCCESS;
			} else if (be.canAccept(stack)) {
				if (!level.isClientSide()) {
					be.addItem(stack.split(1));
				}
				return ItemInteractionResult.SUCCESS;
			} else return ItemInteractionResult.FAIL;
		}
		return ItemInteractionResult.FAIL;
	}
}
