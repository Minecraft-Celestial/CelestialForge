package com.xiaoyue.celestial_forge.register;

import com.xiaoyue.celestial_forge.content.component.ModifierData;
import com.xiaoyue.celestial_forge.content.component.ReinforceData;
import com.xiaoyue.celestial_invoker.CelestialInvoker;
import com.xiaoyue.celestial_invoker.content.common.registrar.NeoForgeRegister;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;

import java.util.function.Supplier;

public class CFObjects {

    public static final NeoForgeRegister<DataComponentType<?>> TYPE = CelestialInvoker.EXTRA.neoforgeRegister(Registries.DATA_COMPONENT_TYPE);

    public static final Supplier<DataComponentType<ModifierData>> MODIFIER_DATA = TYPE.component("modifier_data",
            b -> b.persistent(ModifierData.CODEC).networkSynchronized(ModifierData.STREAM_CODEC));
    public static final Supplier<DataComponentType<ReinforceData>> REINFORCED_DATA = TYPE.component("reinforced_data",
            b -> b.persistent(ReinforceData.CODEC).networkSynchronized(ReinforceData.STREAM_CODEC));

    public static void register() {
    }
}
