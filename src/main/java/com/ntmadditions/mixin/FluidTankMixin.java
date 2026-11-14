package com.ntmadditions.mixin;

import com.hbm.tileentity.machine.TileEntityMachineFluidTank;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Keeps highly corrosive fluids inside machine fluid tanks instead of rupturing the tank.
 *
 * <p>Only the second {@code explode()} call in {@code serverTick} is suppressed; the first call is
 * the antimatter detonation and remains untouched.
 */
@Mixin(TileEntityMachineFluidTank.class)
public abstract class FluidTankMixin {

    @Redirect(method = "serverTick",
            at = @At(value = "INVOKE",
                    target = "Lcom/hbm/tileentity/machine/TileEntityMachineFluidTank;explode()V",
                    ordinal = 1))
    private static void ntmadditions$preventCorrosiveRupture(TileEntityMachineFluidTank tank) {
        // Intentionally do nothing.
    }
}
