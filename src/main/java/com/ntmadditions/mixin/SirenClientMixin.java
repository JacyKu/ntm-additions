package com.ntmadditions.mixin;

import com.hbm.client.render.SirenClient;
import com.hbm.packet.SirenPayload;
import com.ntmadditions.config.NTMAdditionsConfig;
import dev.golem.core.net.GolemNetwork;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Allows the siren feature to be disabled without unregistering the track or sound event. */
@Mixin(SirenClient.class)
public abstract class SirenClientMixin {

    @Inject(method = "handle", at = @At("HEAD"), cancellable = true)
    private static void ntmadditions$disableSiren(SirenPayload payload,
                                                  GolemNetwork.ClientContext context,
                                                  CallbackInfo ci) {
        if (!NTMAdditionsConfig.ENABLE_SIREN.get()) {
            ci.cancel();
        }
    }
}
