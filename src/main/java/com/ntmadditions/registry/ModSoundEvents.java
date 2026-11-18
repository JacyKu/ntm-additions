package com.ntmadditions.registry;

import com.ntmadditions.NTMAdditions;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, NTMAdditions.MOD_ID);

    /**
     * NTM's mine arming sound, re-registered with an explicit 32-block range and attenuation so it
     * fades out locally instead of using NTM's much larger volume-based range.
     *
     * <p>The bundled sound is a mono downmix of NTM's original track. OpenAL does not attenuate
     * stereo positional sounds, which caused the original to play at full volume inside its range
     * and then cut off entirely.
     */
    public static final DeferredHolder<SoundEvent, SoundEvent> MINE_PRIME =
            SOUND_EVENTS.register("mine_prime",
                    () -> SoundEvent.createFixedRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(NTMAdditions.MOD_ID, "mine_prime"),
                            32.0F));

    /**
     * Dutch air raid siren track. The siren machine handles distance fading itself, so this uses a
     * normal variable-range event.
     */
    public static final DeferredHolder<SoundEvent, SoundEvent> DUTCH_AIR_RAID =
            SOUND_EVENTS.register("dutch_air_raid",
                    () -> SoundEvent.createVariableRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(NTMAdditions.MOD_ID, "dutch_air_raid")));

    /**
     * Positional metal pipe impact. A fixed 24-block range keeps it local while still allowing a
     * smooth distance fade instead of a hard cutoff.
     */
    public static final DeferredHolder<SoundEvent, SoundEvent> METAL_PIPE_FALL =
            SOUND_EVENTS.register("metal_pipe_fall",
                    () -> SoundEvent.createFixedRangeEvent(
                            ResourceLocation.fromNamespaceAndPath(NTMAdditions.MOD_ID, "metal_pipe_fall"),
                            24.0F));

    private ModSoundEvents() {}
}
