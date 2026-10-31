package com.ntmadditions.block;

import com.ntmadditions.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Stores a ward's current protection radius and keeps the server-side lookup registry up to date.
 */
public class WardBlockEntity extends BlockEntity {

    public static final int MIN_RADIUS = 50;
    public static final int MAX_RADIUS = 250;
    public static final int RADIUS_STEP = 50;
    private static final String RADIUS_TAG = "Radius";

    private int radius = MIN_RADIUS;

    public WardBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.WARD.get(), pos, state);
    }

    public WardType wardType() {
        return getBlockState().getBlock() instanceof WardBlock ward
                ? ward.wardType()
                : WardType.METEOR;
    }

    public int getRadius() {
        return radius;
    }

    /**
     * Advances 50 -> 100 -> 150 -> 200 -> 250 -> 50 and returns the new radius.
     */
    public int cycleRadius() {
        radius = radius >= MAX_RADIUS ? MIN_RADIUS : radius + RADIUS_STEP;
        setChanged();
        return radius;
    }

    public boolean isInRange(Entity entity) {
        double dx = entity.getX() - (worldPosition.getX() + 0.5D);
        double dy = entity.getY() + entity.getBbHeight() * 0.5D - (worldPosition.getY() + 0.5D);
        double dz = entity.getZ() - (worldPosition.getZ() + 0.5D);
        return dx * dx + dy * dy + dz * dz <= (double) radius * radius;
    }

    /**
     * Horizontal check used for meteor spawn points. Meteors spawn at y=384, so including the
     * ward's Y coordinate would make every meteor fall outside even a 250-block radius.
     */
    public boolean isHorizontallyInRange(double x, double z) {
        double dx = x - (worldPosition.getX() + 0.5D);
        double dz = z - (worldPosition.getZ() + 0.5D);
        return dx * dx + dz * dz <= (double) radius * radius;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        if (level != null && !level.isClientSide) {
            WardManager.add(level, worldPosition);
        }
    }

    @Override
    public void onChunkUnloaded() {
        if (level != null) {
            WardManager.remove(level, worldPosition);
        }
        super.onChunkUnloaded();
    }

    @Override
    public void setRemoved() {
        if (level != null) {
            WardManager.remove(level, worldPosition);
        }
        super.setRemoved();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt(RADIUS_TAG, radius);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        this.radius = normalizeRadius(tag.getInt(RADIUS_TAG));
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private static int normalizeRadius(int value) {
        if (value < MIN_RADIUS || value > MAX_RADIUS) return MIN_RADIUS;
        return Math.round(value / (float) RADIUS_STEP) * RADIUS_STEP;
    }
}
