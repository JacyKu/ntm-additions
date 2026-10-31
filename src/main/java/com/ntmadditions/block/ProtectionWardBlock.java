package com.ntmadditions.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.BaseEntityBlock;

/**
 * Diverts meteor strikes aimed at players within its radius and prevents their block damage.
 */
public class ProtectionWardBlock extends WardBlock {

    public static final MapCodec<ProtectionWardBlock> CODEC = simpleCodec(ProtectionWardBlock::new);

    public ProtectionWardBlock(Properties properties) {
        super(WardType.PROTECTION, properties);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }
}
