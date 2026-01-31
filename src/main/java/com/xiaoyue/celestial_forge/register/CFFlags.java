package com.xiaoyue.celestial_forge.register;

import com.xiaoyue.celestial_forge.content.data.ModifierType;
import com.xiaoyue.celestial_forge.content.reinforce.IReinforce;
import com.xiaoyue.celestial_forge.content.reinforce.ReinforceData;
import com.xiaoyue.celestial_forge.data.CFLang;
import com.xiaoyue.celestial_forge.data.CFModConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

public class CFFlags {

	public static final Map<String, IReinforce> DATA_MAP = new LinkedHashMap<>();

	public static final ReinforceData ECHO_SHARD = new ReinforceData("echo_shard", "echo_shard", Items.GOLD_INGOT,
			item("echo_shard", CFLang.PICK_EXP_BONUS.get(CFLang.per(CFModConfig.COMMON.echoShardPickExpBonus.get()))),
			ModifierType.ARMOR, ModifierType.CURIO);

	public static final ReinforceData EARTH_CORE = new ReinforceData(flagName("earth_core"), CCore("earth_core"), Items.STONE_BRICKS,
			item(CCore("earth_core"), CFLang.BREAK_SPEED.get(CFLang.per(CFModConfig.COMMON.earthCoreMiningSpeed.get()))),
			ModifierType.TOOL, ModifierType.CURIO);

	public static final ReinforceData VOID_ESSENCE = new ReinforceData(flagName("void_essence"), CCore("void_essence"), Items.DRAGON_BREATH,
			item(CCore("void_essence"), CFLang.EXTRA_DAMAGE.get(CFLang.num(CFModConfig.COMMON.voidEssenceExtraDamage.get()))),
			ModifierType.CURIO, ModifierType.WEAPON);

	public static final ReinforceData DEATH_ESSENCE = new ReinforceData(flagName("death_essence"), CCore("death_essence"), Items.ROTTEN_FLESH,
			item(CCore("death_essence"), CFLang.DAMAGE_HEAL.get(CFLang.per(CFModConfig.COMMON.deathEssenceDamageHeal.get()))),
			ModifierType.CURIO, ModifierType.WEAPON);

	public static final ReinforceData PURE_NETHER_STAR = new ReinforceData(flagName("pure_nether_star"), CCore("pure_nether_star"), Items.NETHER_BRICK,
			item(CCore("pure_nether_star"), CFLang.UNDEAD_EXTRA_DAMAGE.get(CFLang.per(CFModConfig.COMMON.pureStarDamageFactor.get()))),
			ModifierType.CURIO, ModifierType.ARMOR);

	public static MutableComponent item(String like, MutableComponent text) {
		Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(like));
		if (item == null) {
			return Component.empty();
		}
		UnaryOperator<Style> style = item.getDefaultInstance().getRarity().getStyleModifier();
		MutableComponent name = brackets(item.getDescription().copy().withStyle(style));
		return name.append(text.withStyle(ChatFormatting.GRAY));
	}

	public static MutableComponent item(String like) {
		Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(like));
		if (item == null) {
			return Component.empty();
		}
		UnaryOperator<Style> style = item.getDefaultInstance().getRarity().getStyleModifier();
		return brackets(item.getDescription().copy().withStyle(style));
	}

	private static MutableComponent brackets(MutableComponent text) {
		return Component.literal("☆").append(text).append(Component.literal("☆ ")).withStyle(ChatFormatting.GRAY);
	}

	public static String CCore(String id) {
		return "celestial_core:" + id;
	}

	public static String flagName(String id) {
		return "celestial_forge:" + id;
	}

	public static void register() {

	}
}
