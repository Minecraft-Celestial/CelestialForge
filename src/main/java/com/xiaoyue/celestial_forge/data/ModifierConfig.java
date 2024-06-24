package com.xiaoyue.celestial_forge.data;

import com.google.gson.*;
import com.xiaoyue.celestial_forge.utils.ModifierUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.xiaoyue.celestial_forge.CelestialForge.MODID;

public class ModifierConfig extends SimpleJsonResourceReloadListener {

    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();

    public static List<ModifierData> modifiers = new ArrayList<>();

    public ModifierConfig() {
        super(GSON, "modifier/modifier_config");
    }

    private void modifierBuilder(JsonElement json) {
        JsonObject object = json.getAsJsonObject();
        String name = GsonHelper.getAsString(object, "name");
        String type = GsonHelper.getAsString(object, "type");
        int weight = GsonHelper.getAsInt(object, "weight");
        JsonArray attributes = GsonHelper.getAsJsonArray(object, "attributes");
        List<Attribute> attrList = new ArrayList<>();
        List<Double> doubleList = new ArrayList<>();
        List<AttributeModifier.Operation> opList = new ArrayList<>();
        for (JsonElement attribute : attributes) {
            JsonObject attrJson = attribute.getAsJsonObject();
            String attrName = GsonHelper.getAsString(attrJson, "attribute");
            Attribute attr = ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation(attrName));
            double amount = GsonHelper.getAsDouble(attrJson, "amount");
            String opName = GsonHelper.getAsString(attrJson, "operation");
            AttributeModifier.Operation operation = ModifierUtils.getOP(opName);
            attrList.add(attr);
            doubleList.add(amount);
            opList.add(operation);
        }
        modifiers.add(new ModifierData(name, type, weight, attrList.toArray(new Attribute[attrList.size()]), doubleList.toArray(new Double[doubleList.size()]), opList.toArray(new AttributeModifier.Operation[opList.size()])));
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, ResourceManager manager, ProfilerFiller filler) {
        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            ResourceLocation key = entry.getKey();
            if (key.getNamespace().equals(MODID)) {
                modifierBuilder(entry.getValue());
            }
        }
    }
}
