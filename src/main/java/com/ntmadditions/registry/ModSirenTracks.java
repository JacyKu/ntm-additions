package com.ntmadditions.registry;

import com.hbm.items.machine.ItemCassette.SoundType;
import com.hbm.items.machine.ItemCassette.TrackType;
import com.ntmadditions.NTMAdditions;

/**
 * Siren tracks added by NTM Additions.
 *
 * <p>Track names and sound keys are namespaced so HBM does not treat them as its own built-in
 * tracks. The matching sound event is registered in {@link ModSoundEvents}.
 */
public final class ModSirenTracks {

    public static final TrackType DUTCH_AIR_RAID = TrackType.register(
            NTMAdditions.MOD_ID + ":dutch_air_raid",
            "Dutch Air Raid Siren",
            NTMAdditions.MOD_ID + ":dutch_air_raid",
            SoundType.LOOP,
            0xFFB000,
            500);

    private ModSirenTracks() {}
}
