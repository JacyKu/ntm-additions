package com.ntmadditions.registry;

import com.ntmadditions.NTMAdditions;
import com.ntmadditions.block.WardBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, NTMAdditions.MOD_ID);

    /**
     * Both ward blocks share one block-entity type. The registry id stays "meteor_ward" so worlds
     * created before the Protection Ward was added keep their existing block entities.
     */
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WardBlockEntity>> WARD =
            BLOCK_ENTITIES.register("meteor_ward", () -> BlockEntityType.Builder
                    .of(WardBlockEntity::new, ModBlocks.METEOR_WARD.get(), ModBlocks.PROTECTION_WARD.get())
                    .build(null));

    private ModBlockEntities() {}
}
