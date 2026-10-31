package com.ntmadditions.block;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

/**
 * Server-side index of loaded wards.
 *
 * <p>Wards register themselves when their block entity loads and unregister when it unloads. The
 * actual radius and type are always read from the live block entity, so right-click changes take
 * effect immediately.
 */
public final class WardManager {

    private static final Map<ResourceKey<Level>, Set<BlockPos>> WARDS = new HashMap<>();

    private WardManager() {}

    public static void add(Level level, BlockPos pos) {
        if (level.isClientSide) return;
        WARDS.computeIfAbsent(level.dimension(), key -> new HashSet<>()).add(pos.immutable());
    }

    public static void remove(Level level, BlockPos pos) {
        Set<BlockPos> positions = WARDS.get(level.dimension());
        if (positions == null) return;
        positions.remove(pos);
        if (positions.isEmpty()) {
            WARDS.remove(level.dimension());
        }
    }

    /** Whether the player is inside the radius of a loaded ward of the given type. */
    public static boolean isProtected(Player player, WardType type) {
        if (!(player.level() instanceof ServerLevel level)) return false;

        Set<BlockPos> positions = WARDS.get(level.dimension());
        if (positions == null || positions.isEmpty()) return false;

        for (BlockPos pos : positions) {
            if (level.getBlockEntity(pos) instanceof WardBlockEntity ward
                    && ward.wardType() == type
                    && ward.isInRange(player)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isMeteorProtected(Player player) {
        return isProtected(player, WardType.METEOR);
    }

    public static boolean isProtectionProtected(Player player) {
        return isProtected(player, WardType.PROTECTION);
    }

    /** Whether a meteor spawn point lies inside the horizontal radius of a meteor ward. */
    public static boolean isMeteorProtected(Level level, Vec3 spawn) {
        return isProtectedAt(level, spawn.x, spawn.z, WardType.METEOR);
    }

    /** Whether a meteor spawn point lies inside the horizontal radius of a protection ward. */
    public static boolean isProtectionProtected(Level level, Vec3 spawn) {
        return isProtectedAt(level, spawn.x, spawn.z, WardType.PROTECTION);
    }

    private static boolean isProtectedAt(Level level, double x, double z, WardType type) {
        if (!(level instanceof ServerLevel)) return false;

        Set<BlockPos> positions = WARDS.get(level.dimension());
        if (positions == null || positions.isEmpty()) return false;

        for (BlockPos pos : positions) {
            if (level.getBlockEntity(pos) instanceof WardBlockEntity ward
                    && ward.wardType() == type
                    && ward.isHorizontallyInRange(x, z)) {
                return true;
            }
        }
        return false;
    }
}
