package com.ntmadditions.mixin;

import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectCollection;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * TEMPORARY compatibility guard for HBM's per-level radiation maps.
 *
 * <p>HBM stores its live radiation data in two non-thread-safe {@code Long2ObjectMap}s. Under
 * Skein/C2ME, HBM radiation access can happen from more than one ticking context at once. A
 * concurrent write can corrupt the map; the next chunk unload or player radiation tick then throws
 * an {@code ArrayIndexOutOfBoundsException}. Neruina catches the player variant and kicks the
 * player instead of letting the server crash.
 *
 * <p>This mixin serializes every map operation in {@code WorldRadiationData} behind one temporary
 * lock. The collection views are copied while locked because callers iterate them after the call.
 * This is deliberately a blunt, temporary guard, not a permanent design: remove it once HBM ships
 * thread-safe radiation storage or a proper compatibility hook.
 */
@Mixin(targets = "com.hbm.handler.radiation.RadiationSystemNT$WorldRadiationData")
public abstract class HbmRadiationMapThreadSafetyMixin {

    @Unique
    private static final Object NTMADDITIONS_RADIATION_LOCK = new Object();

    @Unique
    private static Object ntmadditions$get(Long2ObjectMap<?> map, long key) {
        synchronized (NTMADDITIONS_RADIATION_LOCK) {
            return map.get(key);
        }
    }

    @Unique
    private static Object ntmadditions$put(Long2ObjectMap<Object> map, long key, Object value) {
        synchronized (NTMADDITIONS_RADIATION_LOCK) {
            return map.put(key, value);
        }
    }

    @Unique
    private static Object ntmadditions$remove(Long2ObjectMap<?> map, long key) {
        synchronized (NTMADDITIONS_RADIATION_LOCK) {
            return map.remove(key);
        }
    }

    @Unique
    private static boolean ntmadditions$containsKey(Long2ObjectMap<?> map, long key) {
        synchronized (NTMADDITIONS_RADIATION_LOCK) {
            return map.containsKey(key);
        }
    }

    @Unique
    private static boolean ntmadditions$isEmpty(Long2ObjectMap<?> map) {
        synchronized (NTMADDITIONS_RADIATION_LOCK) {
            return map.isEmpty();
        }
    }

    @Unique
    private static void ntmadditions$clear(Long2ObjectMap<?> map) {
        synchronized (NTMADDITIONS_RADIATION_LOCK) {
            map.clear();
        }
    }

    @Unique
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ObjectCollection<Object> ntmadditions$values(Long2ObjectMap map) {
        synchronized (NTMADDITIONS_RADIATION_LOCK) {
            return new ObjectArrayList(map.values());
        }
    }

    @Unique
    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ObjectSet<Long2ObjectMap.Entry<Object>> ntmadditions$entrySet(Long2ObjectMap map) {
        synchronized (NTMADDITIONS_RADIATION_LOCK) {
            ObjectOpenHashSet<Long2ObjectMap.Entry<Object>> copy = new ObjectOpenHashSet<>();
            for (Object entry : map.long2ObjectEntrySet()) {
                copy.add((Long2ObjectMap.Entry<Object>) entry);
            }
            return copy;
        }
    }

    @Redirect(method = "*",
            at = @At(value = "INVOKE",
                    target = "Lit/unimi/dsi/fastutil/longs/Long2ObjectMap;get(J)Ljava/lang/Object;"))
    private static Object ntmadditions$lockedGet(Long2ObjectMap<?> map, long key) {
        return ntmadditions$get(map, key);
    }

    @Redirect(method = "*",
            at = @At(value = "INVOKE",
                    target = "Lit/unimi/dsi/fastutil/longs/Long2ObjectMap;put(JLjava/lang/Object;)Ljava/lang/Object;"))
    private static Object ntmadditions$lockedPut(Long2ObjectMap<Object> map, long key, Object value) {
        return ntmadditions$put(map, key, value);
    }

    @Redirect(method = "*",
            at = @At(value = "INVOKE",
                    target = "Lit/unimi/dsi/fastutil/longs/Long2ObjectMap;remove(J)Ljava/lang/Object;"))
    private static Object ntmadditions$lockedRemove(Long2ObjectMap<?> map, long key) {
        return ntmadditions$remove(map, key);
    }

    @Redirect(method = "*",
            at = @At(value = "INVOKE",
                    target = "Lit/unimi/dsi/fastutil/longs/Long2ObjectMap;containsKey(J)Z"))
    private static boolean ntmadditions$lockedContainsKey(Long2ObjectMap<?> map, long key) {
        return ntmadditions$containsKey(map, key);
    }

    @Redirect(method = "*",
            at = @At(value = "INVOKE",
                    target = "Lit/unimi/dsi/fastutil/longs/Long2ObjectMap;isEmpty()Z"))
    private static boolean ntmadditions$lockedIsEmpty(Long2ObjectMap<?> map) {
        return ntmadditions$isEmpty(map);
    }

    @Redirect(method = "*",
            at = @At(value = "INVOKE",
                    target = "Lit/unimi/dsi/fastutil/longs/Long2ObjectMap;clear()V"))
    private static void ntmadditions$lockedClear(Long2ObjectMap<?> map) {
        ntmadditions$clear(map);
    }

    @Redirect(method = "*",
            at = @At(value = "INVOKE",
                    target = "Lit/unimi/dsi/fastutil/longs/Long2ObjectMap;values()Lit/unimi/dsi/fastutil/objects/ObjectCollection;"))
    private static ObjectCollection<Object> ntmadditions$lockedValues(Long2ObjectMap<?> map) {
        return ntmadditions$values(map);
    }

    @Redirect(method = "*",
            at = @At(value = "INVOKE",
                    target = "Lit/unimi/dsi/fastutil/longs/Long2ObjectMap;long2ObjectEntrySet()Lit/unimi/dsi/fastutil/objects/ObjectSet;"))
    private static ObjectSet<Long2ObjectMap.Entry<Object>> ntmadditions$lockedEntrySet(Long2ObjectMap<?> map) {
        return ntmadditions$entrySet(map);
    }
}
