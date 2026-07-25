package com.xiaoyue.celestial_forge.content.component;

import com.mojang.serialization.Codec;
import com.xiaoyue.celestial_forge.register.CFObjects;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record ReinforceData(String name) {

    public static final Codec<ReinforceData> CODEC =Codec.STRING.xmap(ReinforceData::new, ReinforceData::name);

    public static final StreamCodec<FriendlyByteBuf, ReinforceData> STREAM_CODEC = StreamCodec.of(
            (buf, data) -> buf.writeUtf(data.name), buf -> new ReinforceData(buf.readUtf()));

    public static ReinforceData getOrCreate(ItemStack stack) {
        ReinforceData data = stack.get(CFObjects.REINFORCED_DATA);
        if (data == null) {
            ReinforceData empty = new ReinforceData("");
            stack.set(CFObjects.REINFORCED_DATA, empty);
            return empty;
        }
        return data;
    }
}
