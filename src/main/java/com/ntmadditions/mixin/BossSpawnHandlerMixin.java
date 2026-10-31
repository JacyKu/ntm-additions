package com.ntmadditions.mixin;

import com.hbm.entity.projectile.EntityMeteor;
import com.hbm.handler.BossSpawnHandler;
import com.ntmadditions.NTMAdditions;
import com.ntmadditions.block.WardManager;
import com.ntmadditions.registry.ModItems;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Makes NTM's meteor strike checks aware of the Curios variants and both ward blocks.
 *
 * <p>The original method only scans worn armor. The Curios effects are player-bound, so they are
 * checked in {@code strike}. Wards affect the meteor itself, so they are checked after
 * {@code spawnMeteorAtPlayer} has chosen the meteor's actual spawn coordinates. This matters
 * because NTM offsets the spawn by up to 100 blocks from the targeted player: a ward only
 * protects meteors that actually spawn inside its radius, not every meteor aimed at a nearby
 * player.
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
        boolean repelled = NTMAdditions.hasCurio(player, ModItems.PROTECTION_CHARM_CURIO.get());
        return BossSpawnHandler.spawnMeteorAtPlayer(player, repell || repelled);
    }

    @Inject(method = "spawnMeteorAtPlayer", at = @At("RETURN"), cancellable = true)
    private static void ntmadditions$applyWardsAtSpawn(Player player, boolean repell,
                                                       CallbackInfoReturnable<EntityMeteor> cir) {
        EntityMeteor meteor = cir.getReturnValue();
        if (meteor == null) return;

        Vec3 spawn = meteor.position();
        if (WardManager.isMeteorProtected(player.level(), spawn)) {
            meteor.discard();
            cir.setReturnValue(null);
            return;
        }

        if (!repell && WardManager.isProtectionProtected(player.level(), spawn)) {
            Vec3 away = new Vec3(meteor.getX() - player.getX(), 0.0D, meteor.getZ() - player.getZ());
            if (away.lengthSqr() < 1.0E-4D) {
                away = new Vec3(1.0D, 0.0D, 0.0D);
            } else {
                away = away.normalize();
            }

            double velocity = player.level().getRandom().nextDouble();
            meteor.setDeltaMovement(away.x * velocity, -2.5D, away.z * velocity);
            meteor.safe = true;
        }
    }
}
