package com.ntmadditions.block;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

/**
 * Common behaviour for the two Meteor Ward blocks.
 *
 * <p>Each ward stores its own radius. Right-clicking cycles it through 50, 100, 150, 200 and 250
 * blocks, wrapping back to 50 after 250, and shows the new value in the player's action bar.
 */
public abstract class WardBlock extends BaseEntityBlock {

    private final WardType type;

    protected WardBlock(WardType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    public WardType wardType() {
        return type;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new WardBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                               Player player, BlockHitResult hitResult) {
        if (!(level.getBlockEntity(pos) instanceof WardBlockEntity ward)) {
            return InteractionResult.PASS;
        }

        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        int radius = ward.cycleRadius();
        player.displayClientMessage(
                Component.translatable(type.radiusMessageKey(), radius), true);
        return InteractionResult.CONSUME;
    }
}
