package com.xiaoyue.celestial_forge.content.block;

import com.xiaoyue.celestial_forge.register.CFItems;
import dev.xkmc.l2modularblock.impl.BlockEntityBlockMethodImpl;
import dev.xkmc.l2modularblock.mult.OnClickBlockMethod;
import dev.xkmc.l2modularblock.type.BlockMethod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class ForgeTableBlock implements OnClickBlockMethod {

	public static final BlockMethod TE = new BlockEntityBlockMethodImpl<>(CFItems.BE_FORGE, ForgeTableBlockEntity.class);

	@Override
	public InteractionResult onClick(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if (level.getBlockEntity(pos) instanceof ForgeTableBlockEntity be) {
			ItemStack stack = player.getItemInHand(hand);
			if (stack.isEmpty()) {
				if (!level.isClientSide()) {
					be.popAllItems(player);
				}
				return InteractionResult.SUCCESS;
			} else if (stack.is(CFItems.HAMMER.get())) {
				if (!level.isClientSide()) {
					be.activate(player);
				}
				return InteractionResult.SUCCESS;
			} else if (be.canAccept(stack)) {
				if (!level.isClientSide()) {
					be.addItem(stack.split(1));
				}
				return InteractionResult.SUCCESS;
			} else return InteractionResult.PASS;
		}
		return InteractionResult.PASS;
	}

}
