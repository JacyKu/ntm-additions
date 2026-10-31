package com.ntmadditions.mixin;

import com.hbm.tileentity.bomb.TileEntityLandmine;
import com.ntmadditions.config.NTMAdditionsConfig;
import com.ntmadditions.registry.ModSoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Makes the mine arming sound local to the mine instead of NTM's long-range volume-3 sound.
 */
@Mixin(TileEntityLandmine.class)
public abstract class TileEntityLandmineMixin {

    @Redirect(method = "serverTick",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/level/Level;playSound(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/core/BlockPos;Lnet/minecraft/sounds/SoundEvent;Lnet/minecraft/sounds/SoundSource;FF)V"))
    private static void ntmadditions$localizePrimeSound(Level level, Player player, BlockPos pos,
                                                        SoundEvent sound, SoundSource source,
                                                        float volume, float pitch) {
        if (NTMAdditionsConfig.LOCAL_MINE_PRIME_SOUND.get()) {
            level.playSound(player, pos, ModSoundEvents.MINE_PRIME.get(), source, 1.0F, pitch);
        } else {
            level.playSound(player, pos, sound, source, volume, pitch);
        }
    }
}
