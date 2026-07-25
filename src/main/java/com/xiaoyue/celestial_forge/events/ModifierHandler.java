package com.xiaoyue.celestial_forge.events;

import com.xiaoyue.celestial_forge.content.component.ModifierData;
import com.xiaoyue.celestial_forge.content.data.ModifierType;
import com.xiaoyue.celestial_forge.content.modifier.ModifierInstance;
import com.xiaoyue.celestial_forge.data.CFModConfig;
import com.xiaoyue.celestial_forge.register.CFItems;
import com.xiaoyue.celestial_forge.register.CFObjects;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import com.xiaoyue.celestial_forge.utils.TypeTestUtils;
import com.xiaoyue.celestial_invoker.content.common.Bindings;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AnvilUpdateEvent;
import net.neoforged.neoforge.event.GrindstoneEvent;
import net.neoforged.neoforge.event.ItemAttributeModifierEvent;
import net.neoforged.neoforge.event.entity.living.LivingExperienceDropEvent;

import static com.xiaoyue.celestial_forge.CelestialForge.MODID;

@EventBusSubscriber(modid = MODID)
public class ModifierHandler {

	@SubscribeEvent
	public static void onItemAttribute(ItemAttributeModifierEvent event) {
		ItemStack stack = event.getItemStack();
		var mod = ModifierUtils.getModifier(stack);
		if (mod == null) return;
		var type = TypeTestUtils.getType(stack);
		EquipmentSlot slot = Bindings.getSlot(stack);
		if (type != null && type.test(slot)) {
			for (int i = 0; i < mod.size(); i++) {
				var entry = mod.get(i);
				String name = mod.holder().id() + "_" + i + "_" + slot.getName();
				event.addModifier(entry.entry().attr(), entry.getAttributeModifier(name), EquipmentSlotGroup.bySlot(slot));
			}
		}
	}

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void onMobExpDrop(LivingExperienceDropEvent event) {
		var player = event.getAttackingPlayer();
		if (player == null) return;
		if (player.level().isClientSide()) return;
		if (!CFModConfig.SERVER.enableModifierUpgrades.get()) return;
		ModifierUtils.addExpToPlayer(player, event.getDroppedExperience());
	}

	@SubscribeEvent
	public static void modifierRecipe(AnvilUpdateEvent event) {
		ItemStack left = event.getLeft().copy();
		ItemStack right = event.getRight();
		ModifierData data = ModifierData.getOrCreate(left);
		if (left.is(CFItems.MODIFIER_BOOK.get()) && right.is(BuiltInRegistries.ITEM.get(ResourceLocation.parse(CFModConfig.SERVER.bookReinforcementMate.get())))) {
			event.setCost(CFModConfig.SERVER.modifierBookCraftCost.get());
			event.setMaterialCost(1);
			left.set(CFObjects.MODIFIER_DATA, new ModifierData("", 0, 0, "", true));
			event.setOutput(left);
		}
		var ins = ModifierUtils.getModifier(left);
		if (!right.is(CFItems.MODIFIER_BOOK.get())) return;
		var book = ModifierUtils.fromBook(right);
		int lv = 0;
		if (book == null) {
			if (ins == null) return;
			int minLv = CFModConfig.SERVER.modifierToBookLevel.get();
			if (ins.level() < minLv) return;
			event.setMaterialCost(1);
			event.setCost(CFModConfig.SERVER.modifierBookCraftCost.get());
			if (ModifierData.getOrCreate(right).reinforced()) {
				lv = ins.level();
			}
            event.setOutput(ModifierUtils.bookOf(ins.holder(), lv, ModifierData.getOrCreate(right).reinforced()));
		} else {
			if (ins != null || !TypeTestUtils.mightHaveModifiers(left)) return;
			if (book.type() != ModifierType.ALL && book.type() != TypeTestUtils.getType(left)) return;
			lv = data.level();
			if (ModifierData.getOrCreate(right).reinforced()) {
				lv = ModifierData.getOrCreate(right).level();
			}
			ModifierUtils.setModifier(left, ModifierInstance.of(book, lv));
			event.setMaterialCost(1);
			event.setOutput(left);
			event.setCost(CFModConfig.SERVER.modifierBookRecipeCost.get());
		}
	}

	@SubscribeEvent
	public static void grind(GrindstoneEvent.OnPlaceItem event) {
		var stack = event.getTopItem();
		var ins = ModifierUtils.getModifier(stack);
		if (ins == null) return;
		int maxLv = CFModConfig.SERVER.grindstoneRemovalPriorityLevel.get();
		if (!stack.isEnchanted() || ins.level() <= maxLv) {
			var copy = stack.copy();
			ModifierUtils.removeModifier(copy);
			event.setOutput(copy);
			event.setXp(3 + ins.level());
		}
	}

}
