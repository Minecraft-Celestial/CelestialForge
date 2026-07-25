package com.xiaoyue.celestial_forge.content.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.xiaoyue.celestial_forge.register.CFObjects;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

public record ModifierData(String name, int level, int exp, String book, boolean reinforced) {

    public static final Codec<ModifierData> CODEC = RecordCodecBuilder.create(ins -> ins.group(
            Codec.STRING.fieldOf("name").forGetter(ModifierData::name),
            Codec.INT.fieldOf("level").forGetter(ModifierData::level),
            Codec.INT.fieldOf("exp").forGetter(ModifierData::exp),
            Codec.STRING.fieldOf("book").forGetter(ModifierData::book),
            Codec.BOOL.fieldOf("reinforced").forGetter(ModifierData::reinforced)).apply(ins, ModifierData::new));

    public static final StreamCodec<FriendlyByteBuf, ModifierData> STREAM_CODEC = StreamCodec.of(
            (buf, data) -> {
                buf.writeUtf(data.name);
                buf.writeInt(data.level);
                buf.writeInt(data.exp);
                buf.writeUtf(data.book);
                buf.writeBoolean(data.reinforced);
            }, buf -> new ModifierData(buf.readUtf(), buf.readInt(), buf.readInt(), buf.readUtf(), buf.readBoolean()));

    public static ModifierData getOrCreate(ItemStack stack) {
        ModifierData data = stack.get(CFObjects.MODIFIER_DATA);
        if (data == null) {
            ModifierData empty = new ModifierData("", 0, 0, "", false);
            stack.set(CFObjects.MODIFIER_DATA, empty);
            return empty;
        }
        return data;
    }
}
