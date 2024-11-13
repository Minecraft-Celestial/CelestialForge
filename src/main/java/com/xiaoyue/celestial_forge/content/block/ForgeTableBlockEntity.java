package com.xiaoyue.celestial_forge.content.block;

import com.xiaoyue.celestial_forge.content.data.DataHolder;
import com.xiaoyue.celestial_forge.content.data.UpgradeRecipe;
import com.xiaoyue.celestial_forge.content.modifier.ModifierInstance;
import com.xiaoyue.celestial_forge.content.overlay.InfoTile;
import com.xiaoyue.celestial_forge.content.overlay.TileTooltip;
import com.xiaoyue.celestial_forge.data.CFLang;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import dev.xkmc.l2library.base.tile.BaseBlockEntity;
import dev.xkmc.l2library.base.tile.BaseContainerListener;
import dev.xkmc.l2modularblock.tile_api.BlockContainer;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
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

	@Nullable
	public UpgradeRecipe getRecipe(ItemStack stack) {
		if (stack.isEmpty()) return null;
		var type = ModifierUtils.getType(stack);
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
		for (var e : recipe.items) {
			var arr = e.getItems();
			list.add(arr[(int) (index % arr.length)]);
		}
		return new TileTooltip(list);
	}

	@Override
	public List<Component> lines() {
		List<Component> list = new ArrayList<>();
		ItemStack main = get(0);
		if (main.isEmpty()) {
			list.add(CFLang.TABLE_LINES_1.get());
			list.add(CFLang.TABLE_LINES_2.get());
			return list;
		}
		if (!main.isEmpty()) {
			if (!ModifierUtils.canHaveModifiers(main)) {
				list.add(CFLang.TABLE_LINES_7.get());
				return list;
			} else {
				UpgradeRecipe recipe = getRecipe(main);
                if (recipe == null) return list;
                int meta = 0;
                for (int i = 0; i < recipe.items.size(); i++) {
                    if (!get(i + 1).isEmpty() && recipe.items.get(i).test(get(i + 1))) {
                        meta++;
                    }
                }
				LocalPlayer player = Minecraft.getInstance().player;
				if (meta == recipe.items.size() && player != null && player.totalExperience >= recipe.exp) {
                    list.add(CFLang.TABLE_LINES_6.get());
                } else {
                    list.add(CFLang.TABLE_LINES_3.get());
					list.add(CFLang.TABLE_LINES_4.get());
                    list.add(CFLang.TABLE_LINES_5.get(CFLang.num(recipe.exp)));
                }
                return list;
            }
		}
		return list;
	}
}
