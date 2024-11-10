package com.xiaoyue.celestial_forge.events;

import com.xiaoyue.celestial_forge.content.data.DataHolder;
import com.xiaoyue.celestial_forge.content.data.ModifierType;
import com.xiaoyue.celestial_forge.content.item.ModifierBook;
import com.xiaoyue.celestial_forge.content.modifier.ModifierHolder;
import com.xiaoyue.celestial_forge.content.modifier.ModifierInstance;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.theillusivec4.curios.api.event.CurioAttributeModifierEvent;

import java.util.UUID;

import static com.xiaoyue.celestial_forge.CelestialForge.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModifierHandler {

	@SubscribeEvent
	public static void onItemAttribute(ItemAttributeModifierEvent event) {
		ItemStack stack = event.getItemStack();
		var mod = ModifierUtils.getModifier(stack);
		if (mod == null) return;
		var type = ModifierUtils.getType(stack);
		var slot = event.getSlotType();
		if ((type == ModifierType.TOOL || type == ModifierType.RANGED) && slot == EquipmentSlot.MAINHAND ||
				type == ModifierType.ARMOR && slot.isArmor()) {
			for (int i = 0; i < mod.size(); i++) {
				var entry = mod.get(i);
				String name = slot + "/" + mod.holder().id() + "/" + i;
				UUID id = MathHelper.getUUIDFromString(name);
				event.addModifier(entry.entry().attr(), entry.getAttributeModifier(id, name));
			}
		}
	}

	@SubscribeEvent
	public static void onCuriosAttribute(CurioAttributeModifierEvent event) {
		ItemStack stack = event.getItemStack();
		var mod = ModifierUtils.getModifier(stack);
		if (mod == null) return;
		var type = mod.holder().type();
		var ctx = event.getSlotContext();
		if ((type == ModifierType.CURIO)) {
			for (int i = 0; i < mod.size(); i++) {
				var entry = mod.get(i);
				String name = ctx.identifier() + "/" + ctx.index() + "/" + mod.holder().id() + "/" + i;
				UUID id = MathHelper.getUUIDFromString(name);
				event.addModifier(entry.entry().attr(), entry.getAttributeModifier(id, name));
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onMobExpDrop(LivingExperienceDropEvent event) {
		var player = event.getAttackingPlayer();
		if (player == null) return;
		var list = ModifierUtils.getAllOnPlayer(player);
		int total = event.getDroppedExperience();
		int n = list.size();
		int base = total / n;
		int avail = total - base;
		var r = event.getAttackingPlayer().getRandom();
		for (int i = 0; i < n; i++) {
			int a = r.nextInt(n);
			int b = r.nextInt(n);
			var t = list.get(a);
			list.set(a, list.get(b));
			list.set(b, t);
		}
		for (var e : list) {
			int toAdd = base;
			if (avail > 0) {
				toAdd++;
				avail--;
			}
			ModifierUtils.addExp(e.getFirst(), e.getSecond(), toAdd);
		}
	}

	@SubscribeEvent
	public static void addTooltip(ItemTooltipEvent event) {
		ItemStack stack = event.getItemStack();
		ModifierInstance modifier = ModifierUtils.getModifier(stack);
		if (modifier == null) return;
		event.getToolTip().addAll(modifier.getInfoLines());
	}

	@SubscribeEvent //TODO
	public static void ModifierRecipe(AnvilUpdateEvent event) {
		ItemStack left = event.getLeft().copy();
		ItemStack right = event.getRight();

		if (ModifierUtils.canHaveModifiers(left)) {
			if (right.getItem() instanceof ModifierBook) {
				ModifierHolder modifier = DataHolder.byId(new ResourceLocation(right.getTag().getString(ModifierUtils.bookTagName)));
				if (modifier == null) return;
				ModifierUtils.setModifier(left, ModifierInstance.of(modifier));
				event.setMaterialCost(1);
				event.setOutput(left);
				event.setCost(22);
			}
		}
	}
}
