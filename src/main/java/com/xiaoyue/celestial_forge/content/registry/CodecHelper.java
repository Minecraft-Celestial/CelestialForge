package com.xiaoyue.celestial_forge.content.registry;

import com.google.common.base.Suppliers;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import dev.xkmc.l2serial.util.Wrappers;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryManager;

import java.util.List;
import java.util.function.Supplier;

public class CodecHelper {

	public static final Codec<ResourceLocation> RL = Codec.STRING.xmap(ResourceLocation::tryParse, ResourceLocation::toString);

	public static <T extends Enum<T>> Codec<T> enumCodec(Class<T> cls, T[] vals) {
		return Codec.STRING.xmap(e -> {
			try {
				return Enum.valueOf(cls, e);
			} catch (Exception ex) {
				throw new IllegalArgumentException(e + " is not a valid " + cls.getSimpleName() + ". Valid values are: " + List.of(vals));
			}
		}, Enum::name);
	}

	public static <T> Codec<Holder<T>> lazyCodec(ResourceKey<Registry<T>> key) {
		Supplier<Registry<T>> sup = Suppliers.memoize(() -> Wrappers.cast(RegistryManager.ACTIVE.getRegistry(key)));
		return new Codec<>() {
			@Override
			public <T1> DataResult<Pair<Holder<T>, T1>> decode(DynamicOps<T1> a, T1 b) {
				return sup.get().holderByNameCodec().decode(a, b);
			}

			@Override
			public <T1> DataResult<T1> encode(Holder<T> a, DynamicOps<T1> b, T1 c) {
				return sup.get().holderByNameCodec().encode(a, b, c);
			}
		};
	}


}
