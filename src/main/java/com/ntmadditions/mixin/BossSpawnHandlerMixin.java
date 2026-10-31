package com.ntmadditions.mixin;

import com.hbm.entity.projectile.EntityMeteor;
import com.hbm.handler.BossSpawnHandler;
import com.ntmadditions.NTMAdditions;
import com.ntmadditions.registry.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Makes NTM's meteor strike checks aware of the Curios variants.
 *
 * <p>The original method only scans worn armor. A Meteor Charm curio cancels the strike exactly like
 * the armor insert; a Charm of Protection curio forces the "repelled" flag at the final spawn call,
 * so the meteor is diverted and marked safe instead of destroying blocks.
 */
@Mixin(BossSpawnHandler.class)
public abstract class BossSpawnHandlerMixin {

    @Inject(method = "strike", at = @At("HEAD"), cancellable = true)
    private static void ntmadditions$stopMeteor(ServerLevel level, Player player,
                                                CallbackInfoReturnable<EntityMeteor> cir) {
        if (NTMAdditions.hasCurio(player, ModItems.METEOR_CHARM_CURIO.get())) {
            cir.setReturnValue(null);
        }
    }

    @Redirect(method = "strike",
            at = @At(value = "INVOKE",
                    target = "Lcom/hbm/handler/BossSpawnHandler;spawnMeteorAtPlayer(Lnet/minecraft/world/entity/player/Player;Z)Lcom/hbm/entity/projectile/EntityMeteor;"))
    private static EntityMeteor ntmadditions$repelMeteor(Player player, boolean repell) {
        boolean protectedByCurio =
                NTMAdditions.hasCurio(player, ModItems.PROTECTION_CHARM_CURIO.get());
        return BossSpawnHandler.spawnMeteorAtPlayer(player, repell || protectedByCurio);
    }
}
