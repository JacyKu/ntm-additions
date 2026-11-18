package com.ntmadditions.mixin;

import com.ntmadditions.config.NTMAdditionsConfig;
import com.ntmadditions.registry.ModSoundEvents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Plays the metal pipe sound when the dropped steel pipe comes to rest. */
@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    private static final String PLAYED_TAG = "ntmadditions_pipe_fall_played";
    private static final ResourceLocation STEEL_PIPE_ID =
            ResourceLocation.fromNamespaceAndPath("hbm", "pipe_steel");

    @Inject(method = "tick", at = @At("TAIL"))
    private void ntmadditions$playPipeFallSound(CallbackInfo ci) {
        ItemEntity self = (ItemEntity) (Object) this;
        if (self.level().isClientSide || !NTMAdditionsConfig.METAL_PIPE_FALLING_SOUND.get()) {
            return;
        }

        ItemStack stack = self.getItem();
        if (!BuiltInRegistries.ITEM.getKey(stack.getItem()).equals(STEEL_PIPE_ID)) {
            return;
        }
        if (!self.onGround() || self.getPersistentData().getBoolean(PLAYED_TAG)) {
            return;
        }

        self.getPersistentData().putBoolean(PLAYED_TAG, true);
        self.level().playSound(null, self.blockPosition(), ModSoundEvents.METAL_PIPE_FALL.get(),
                SoundSource.BLOCKS, 1.0F,
                0.95F + self.level().getRandom().nextFloat() * 0.1F);
    }
}
