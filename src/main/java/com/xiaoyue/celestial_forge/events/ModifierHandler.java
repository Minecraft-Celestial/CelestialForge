package com.xiaoyue.celestial_forge.events;

import com.xiaoyue.celestial_forge.content.data.ModifierType;
import com.xiaoyue.celestial_forge.content.modifier.ModifierInstance;
import com.xiaoyue.celestial_forge.data.CFModConfig;
import com.xiaoyue.celestial_forge.register.CFItems;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import com.xiaoyue.celestial_forge.utils.TypeTestUtils;
import dev.xkmc.l2library.util.math.MathHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.GrindstoneEvent;
import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.UUID;

import static com.xiaoyue.celestial_forge.CelestialForge.MODID;

@Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModifierHandler {

	@SubscribeEvent
	public static void onItemAttribute(ItemAttributeModifierEvent event) {
		ItemStack stack = event.getItemStack();
		var mod = ModifierUtils.getModifier(stack);
		if (mod == null) return;
		var type = TypeTestUtils.getType(stack);
		var slot = event.getSlotType();
		if (slot.isArmor() && slot != LivingEntity.getEquipmentSlotForItem(stack)) return;
		if (type != null && type.test(slot)) {
			for (int i = 0; i < mod.size(); i++) {
				var entry = mod.get(i);
				String name = slot + "/" + mod.holder().id() + "/" + i;
				UUID id = MathHelper.getUUIDFromString(name);
				event.addModifier(entry.entry().attr(), entry.getAttributeModifier(id, name));
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onMobExpDrop(LivingExperienceDropEvent event) {
		var player = event.getAttackingPlayer();
		if (player == null) return;
		ModifierUtils.addExpToPlayer(player, event.getDroppedExperience());
	}

	@SubscribeEvent
	public static void modifierRecipe(AnvilUpdateEvent event) {
		ItemStack left = event.getLeft().copy();
		ItemStack right = event.getRight();
		var ins = ModifierUtils.getModifier(left);
		if (!right.is(CFItems.MODIFIER_BOOK.get())) return;
		var book = ModifierUtils.fromBook(right);
		if (book == null) {
			if (ins == null) return;
			int minLv = CFModConfig.COMMON.modifierToBookLevel.get();
			if (ins.level() < minLv) return;
			event.setMaterialCost(1);
			event.setCost(CFModConfig.COMMON.modifierBookCraftCost.get());
			event.setOutput(ModifierUtils.bookOf(ins.holder()));
		} else {
			if (ins != null || !TypeTestUtils.mightHaveModifiers(left)) return;
			if (book.type() != ModifierType.ALL && book.type() != TypeTestUtils.getType(left)) return;
			ModifierUtils.setModifier(left, ModifierInstance.of(book));
			event.setMaterialCost(1);
			event.setOutput(left);
			event.setCost(CFModConfig.COMMON.modifierBookRecipeCost.get());
		}
	}

	@SubscribeEvent
	public static void grind(GrindstoneEvent.OnPlaceItem event) {
		var stack = event.getTopItem();
		var ins = ModifierUtils.getModifier(stack);
		if (ins == null) return;
		int maxLv = CFModConfig.COMMON.grindstoneRemovalPriorityLevel.get();
		if (!stack.isEnchanted() || ins.level() <= maxLv) {
			var copy = stack.copy();
			ModifierUtils.removeModifier(copy);
			event.setOutput(copy);
			event.setXp(3 + ins.level());
		}
	}

}
