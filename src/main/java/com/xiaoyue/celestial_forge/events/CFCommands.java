package com.xiaoyue.celestial_forge.events;

import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.xiaoyue.celestial_forge.CelestialForge;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CelestialForge.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CFCommands {

	@SubscribeEvent
	public static void register(RegisterCommandsEvent event) {
		event.getDispatcher().register(literal("celestialforge")
				.then(literal("add_exp")
						.then(argument("player", EntityArgument.player())
								.then(argument("exp", IntegerArgumentType.integer(0))
										.executes(ctx -> {
											var player = EntityArgument.getPlayer(ctx, "player");
											var exp = IntegerArgumentType.getInteger(ctx, "exp");
											ModifierUtils.addExpToPlayer(player, exp);
											ctx.getSource().sendSystemMessage(Component.literal("Added " + exp + " exp to player items"));
											return 0;
										}))))
		);
	}

	protected static LiteralArgumentBuilder<CommandSourceStack> literal(String str) {
		return LiteralArgumentBuilder.literal(str);
	}

	protected static <T> RequiredArgumentBuilder<CommandSourceStack, T> argument(String name, ArgumentType<T> type) {
		return RequiredArgumentBuilder.argument(name, type);
	}

}
