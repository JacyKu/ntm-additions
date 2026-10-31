package com.ntmadditions.config;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Common configuration for the addon.
 */
public final class NTMAdditionsConfig {

    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.BooleanValue LOCAL_MINE_PRIME_SOUND;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        LOCAL_MINE_PRIME_SOUND = builder
                .comment("Play the mine priming sound with a local 32-block fade instead of NTM's volume-3 sound.",
                        "This only affects the FSTBMB_START arming sound; explosions, damage, and block destruction are unchanged.")
                .define("localMinePrimeSound", true);

        SPEC = builder.build();
    }

    private NTMAdditionsConfig() {}
}
