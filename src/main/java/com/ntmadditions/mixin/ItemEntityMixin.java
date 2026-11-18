package com.ntmadditions.mixin;

import com.hbm.items.ModItems;
import com.ntmadditions.config.NTMAdditionsConfig;
import com.ntmadditions.registry.ModSoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Plays the metal pipe impact sound when a dropped steel pipe first lands.
 *
 * <p>The sound is played at the item entity's position so it stays local to the pipe rather than
 * following the player, and a persistent flag prevents it from repeating while the item rests on
 * the ground.
 */
@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    private static final String PLAYED_TAG = "ntmadditions_pipe_fall_played";

    @Inject(method = "tick", at = @At("TAIL"))
    private void ntmadditions$playPipeFallSound(CallbackInfo ci) {
        ItemEntity self = (ItemEntity) (Object) this;
        if (self.level().isClientSide || !NTMAdditionsConfig.METAL_PIPE_FALLING_SOUND.get()) return;

        ItemStack stack = self.getItem();
        if (!stack.is(ModItems.pipes_steel.get()) || !self.onGround()) return;
        if (self.getPersistentData().getBoolean(PLAYED_TAG)) return;

        self.getPersistentData().putBoolean(PLAYED_TAG, true);
        self.level().playSound(null, self.blockPosition(), ModSoundEvents.METAL_PIPE_FALL.get(),
                SoundSource.BLOCKS, 0.65F, 0.95F + self.level().getRandom().nextFloat() * 0.1F);
    }
}
