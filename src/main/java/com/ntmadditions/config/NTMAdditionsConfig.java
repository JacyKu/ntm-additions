package com.ntmadditions.config;

import net.neoforged.neoforge.common.ModConfigSpec;

/**
 * Common configuration for the addon.
 */
public final class NTMAdditionsConfig {

    public static final ModConfigSpec SPEC;
    public static final ModConfigSpec.BooleanValue ENABLE_CURIOS_CHARMS;
    public static final ModConfigSpec.BooleanValue ENABLE_WARDS;
    public static final ModConfigSpec.BooleanValue ENABLE_SIREN;
    public static final ModConfigSpec.BooleanValue ENABLE_METEOR_COMMAND;
    public static final ModConfigSpec.BooleanValue LOCAL_MINE_PRIME_SOUND;
    public static final ModConfigSpec.BooleanValue PREVENT_CORROSIVE_TANK_RUPTURE;
    public static final ModConfigSpec.BooleanValue METAL_PIPE_FALLING_SOUND;

    static {
        ModConfigSpec.Builder builder = new ModConfigSpec.Builder();

        ENABLE_CURIOS_CHARMS = builder
                .comment("Master switch for the addon's Curios charm items and their effects.",
                        "Curios slot data remains installed; disabling this prevents the charms from being equipped or granting effects.")
                .define("enableCuriosCharms", true);

        ENABLE_WARDS = builder
                .comment("Master switch for the Meteor Ward and Protection Ward blocks and their protection effects.")
                .define("enableWards", true);

        ENABLE_SIREN = builder
                .comment("Master switch for the Dutch Air Raid siren track and its cassette item.")
                .define("enableSiren", true);

        ENABLE_METEOR_COMMAND = builder
                .comment("Master switch for the /ntmadditions meteor command.")
                .define("enableMeteorCommand", true);

        LOCAL_MINE_PRIME_SOUND = builder
                .comment("Play the mine priming sound with a local 32-block fade instead of NTM's volume-3 sound.",
                        "This only affects the FSTBMB_START arming sound; explosions, damage, and block destruction are unchanged.")
                .define("localMinePrimeSound", true);

        PREVENT_CORROSIVE_TANK_RUPTURE = builder
                .comment("Keep highly corrosive fluids inside machine fluid tanks instead of rupturing the tank.",
                        "Set to false to restore NTM's original rupture behavior.",
                        "Antimatter explosions and steel barrel corrosion are unaffected.")
                .define("preventCorrosiveTankRupture", true);

        METAL_PIPE_FALLING_SOUND = builder
                .comment("Play the metal pipe falling sound when a dropped steel pipe lands.",
                        "The sound is positional and fades out over roughly 24 blocks.")
                .define("metalPipeFallingSound", true);

        SPEC = builder.build();
    }

    private NTMAdditionsConfig() {}
}
