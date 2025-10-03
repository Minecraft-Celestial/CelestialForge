package com.xiaoyue.celestial_forge.content.overlay;

import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface InfoTile {

	@Nullable
	TileTooltip getImage();

	List<Component> lines();

}
