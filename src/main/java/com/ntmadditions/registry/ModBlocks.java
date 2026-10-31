package com.ntmadditions.registry;

import com.ntmadditions.NTMAdditions;
import com.ntmadditions.block.MeteorWardBlock;
import com.ntmadditions.block.ProtectionWardBlock;
import com.ntmadditions.block.WardBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, NTMAdditions.MOD_ID);

    public static final DeferredHolder<Block, WardBlock> METEOR_WARD =
            BLOCKS.register("meteor_ward", () -> new MeteorWardBlock(wardProperties(MapColor.COLOR_PURPLE)));

    public static final DeferredHolder<Block, WardBlock> PROTECTION_WARD =
            BLOCKS.register("protection_ward",
                    () -> new ProtectionWardBlock(wardProperties(MapColor.COLOR_BLUE)));

    private static BlockBehaviour.Properties wardProperties(MapColor color) {
        return BlockBehaviour.Properties.of()
                .mapColor(color)
                .strength(5.0F, 6.0F)
                .sound(SoundType.METAL);
    }

    private ModBlocks() {}
}
