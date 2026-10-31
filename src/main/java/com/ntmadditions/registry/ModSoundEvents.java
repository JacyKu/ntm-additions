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

    private ModSoundEvents() {}
}
