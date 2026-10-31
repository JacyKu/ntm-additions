package com.ntmadditions.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;

/**
 * Stops meteor strikes aimed at players within its radius.
 */
public class MeteorWardBlock extends WardBlock {

    public static final MapCodec<MeteorWardBlock> CODEC = simpleCodec(MeteorWardBlock::new);

    public MeteorWardBlock(Properties properties) {
        super(WardType.METEOR, properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
