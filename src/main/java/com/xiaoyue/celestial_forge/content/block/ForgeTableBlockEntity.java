package com.xiaoyue.celestial_forge.content.block;

import com.xiaoyue.celestial_forge.content.data.DataHolder;
import com.xiaoyue.celestial_forge.content.data.UpgradeRecipe;
import com.xiaoyue.celestial_forge.content.overlay.InfoTile;
import com.xiaoyue.celestial_forge.content.overlay.TileTooltip;
import com.xiaoyue.celestial_forge.data.CFLang;
import com.xiaoyue.celestial_forge.register.CFItems;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import com.xiaoyue.celestial_forge.utils.TypeTestUtils;
import dev.xkmc.l2library.base.tile.BaseBlockEntity;
import dev.xkmc.l2library.base.tile.BaseContainerListener;
import dev.xkmc.l2modularblock.tile_api.BlockContainer;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@SerialClass
public class ForgeTableBlockEntity extends BaseBlockEntity
		implements BaseContainerListener, BlockContainer, InfoTile, IItemHandler {

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

	@Nullable
	public UpgradeRecipe getRecipe(ItemStack stack) {
		if (level == null) return null;
		if (stack.isEmpty()) return null;
		var type = TypeTestUtils.getType(stack, level.isClientSide());
		if (type == null) return null;
		var ins = ModifierUtils.getModifier(stack);
		if (ins == null) return DataHolder.getStart(type);
		if (!ins.needUpgrade()) return null;
		return ins.getNextUpgrade();
	}

	public boolean canAccept(ItemStack stack) {
		ItemStack main = get(0);
		if (main.isEmpty()) {
			return getRecipe(stack) != null;
		}
		var recipe = getRecipe(main);
		if (recipe == null) return false;
		for (int i = 0; i < recipe.items.size(); i++) {
			if (get(i + 1).isEmpty() && recipe.items.get(i).test(stack)) {
				return true;
			}
		}
		return false;
	}

	public void addItem(ItemStack stack) {
		if (get(0).isEmpty())
			container.setItem(0, stack);
		var recipe = getRecipe(get(0));
		if (recipe == null) return;
		for (int i = 0; i < recipe.items.size(); i++) {
			if (get(i + 1).isEmpty() && recipe.items.get(i).test(stack)) {
				container.setItem(i + 1, stack);
				break;
			}
		}
	}

	@Override
	public TileTooltip getImage() {
		var recipe = getRecipe(get(0));
		if (level == null || recipe == null)
			return null;
		long index = level.getGameTime() / 20;
		List<ItemStack> list = new ArrayList<>();
		for (int i = 0; i < recipe.items.size(); i++) {
			var e = recipe.items.get(i);
			if (get(i + 1).isEmpty()) {
				var arr = e.getItems();
				list.add(arr[(int) (index % arr.length)]);
			}
		}
		LocalPlayer player = Minecraft.getInstance().player;
		boolean exp = player != null && (player.experienceLevel >= recipe.exp || player.getAbilities().instabuild);
		if (exp && list.isEmpty()) list.add(CFItems.HAMMER.asStack());
		if (list.isEmpty()) return null;
		return new TileTooltip(list);
	}

	@Override
	public List<Component> lines() {
		ItemStack main = get(0);
		if (main.isEmpty()) {
			return List.of(CFLang.TABLE_START.get());
		}
		UpgradeRecipe recipe = getRecipe(main);
		if (recipe == null) {
			return List.of(CFLang.TABLE_INVALID_ITEM.get());
		}
		int valid = 0;
		for (int i = 0; i < recipe.items.size(); i++) {
			if (!get(i + 1).isEmpty()) {
				valid++;
			}
		}
		LocalPlayer player = Minecraft.getInstance().player;
		boolean exp = player != null && (player.experienceLevel >= recipe.exp || player.getAbilities().instabuild);
		List<Component> list = new ArrayList<>();
		list.add(CFLang.TABLE_EXP_COST.get(Component.literal("" + recipe.exp))
				.withStyle(exp ? ChatFormatting.AQUA : ChatFormatting.RED));
		if (valid == recipe.items.size()) {
			if (exp) {
				list.add(CFLang.TABLE_HAMMER.get());
			}
			return list;
		} else {
			list.add(CFLang.TABLE_MATERIAL.get());
		}
		return list;
	}

	public void activate(Player player) {
		if (level == null || level.isClientSide()) return;
		var main = get(0);
		if (main.isEmpty()) return;
		UpgradeRecipe recipe = getRecipe(main);
		if (recipe == null) return;
		if (!player.getAbilities().instabuild && player.experienceLevel < recipe.exp) return;
		for (int i = 0; i < recipe.items.size(); i++) {
			if (get(i + 1).isEmpty()) {
				return;
			}
		}
		player.giveExperienceLevels(-recipe.exp);
		var ins = ModifierUtils.getModifier(main);
		if (ins == null) {
			ins = ModifierUtils.rollModifier(main, level.getRandom());
			if (ins == null) return;
		} else {
			ins = ins.upgrade();
		}
		main = main.copy();
		ModifierUtils.setModifier(main, ins);
		container.clearContent();
		Block.popResource(level, getBlockPos().above(), main);
		level.levelEvent(LevelEvent.SOUND_ANVIL_USED, getBlockPos(), 0);
	}

	@Override
	public int getSlots() {
		return 1;
	}

	@Override
	public @NotNull ItemStack getStackInSlot(int slot) {
		return ItemStack.EMPTY;
	}

	@Override
	public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
		if (!canAccept(stack)) return stack;
		ItemStack ans = stack.copy();
		ItemStack toInsert = ans.split(1);
		if (!simulate) addItem(toInsert);
		return ans;
	}

	@Override
	public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
		return ItemStack.EMPTY;
	}

	@Override
	public int getSlotLimit(int slot) {
		return 1;
	}

	@Override
	public boolean isItemValid(int slot, @NotNull ItemStack stack) {
		return canAccept(stack);
	}

	@Override
	public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == ForgeCapabilities.ITEM_HANDLER) {
			return LazyOptional.of(() -> this).cast();
		}
		return super.getCapability(cap, side);
	}

}
