package com.xiaoyue.celestial_forge.content.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ForgeTableRenderer implements BlockEntityRenderer<ForgeTableBlockEntity> {

	public ForgeTableRenderer(BlockEntityRendererProvider.Context context) {

	}

	@Override
	public void render(
			ForgeTableBlockEntity be, float pTick, PoseStack pose, MultiBufferSource buffer, int light, int overlay
	) {
		light = LightTexture.FULL_BRIGHT;
		var level = be.getLevel();
		if (level == null) return;
		ItemStack main = be.get(0);
		if (main.isEmpty()) return;
		List<ItemStack> list = new ArrayList<>();
		for (int i = 1; i < 9; i++) {
			ItemStack stack = be.get(i);
			if (!stack.isEmpty()) {
				list.add(stack);
			}
		}
		float time = (float) Math.floorMod(level.getGameTime(), 80L) + pTick;
		pose.pushPose();
		pose.translate(0.5, 1.5, 0.5);
		pose.pushPose();
		pose.mulPose(Axis.YP.rotationDegrees(time * 4.5F));
		Minecraft.getInstance().getItemRenderer().renderStatic(main, ItemDisplayContext.GROUND, light, overlay, pose, buffer, level, 0);
		pose.popPose();
		for (int i = 0; i < list.size(); i++) {
			ItemStack sub = list.get(i);
			pose.pushPose();
			pose.mulPose(Axis.YP.rotationDegrees(360f / list.size() * i - time * 4.5F));
			pose.translate(0.5, -0.1, 0);
			pose.mulPose(Axis.YP.rotationDegrees(time * 9F - 360f / list.size() * i));
			Minecraft.getInstance().getItemRenderer().renderStatic(sub, ItemDisplayContext.GROUND, light, overlay, pose, buffer, level, 0);
			pose.popPose();
		}
		pose.popPose();
	}

}
