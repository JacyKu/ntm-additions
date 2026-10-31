package com.ntmadditions;

import com.ntmadditions.block.WardManager;
import com.ntmadditions.command.MeteorCommand;
import com.ntmadditions.registry.ModBlockEntities;
import com.ntmadditions.registry.ModBlocks;
import com.ntmadditions.registry.ModCreativeTabs;
import com.ntmadditions.registry.ModItems;
import dev.golem.core.world.DamageEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import top.theillusivec4.curios.api.CuriosApi;

/**
 * NTM Additions: Curios-slot variants of NTM's meteor charms.
 *
 * <p>The original {@code hbm:meteor_charm} and {@code hbm:protection_charm} armor inserts are left
 * completely untouched. These are separate items that live in a Curios charm slot instead.
 */
@Mod(NTMAdditions.MOD_ID)
public final class NTMAdditions {

    public static final String MOD_ID = "ntmadditions";

    public NTMAdditions(IEventBus modBus) {
        ModBlocks.BLOCKS.register(modBus);
        ModBlockEntities.BLOCK_ENTITIES.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModCreativeTabs.TABS.register(modBus);

        // NTM installs this bridge during its own startup; installing it here is harmless when it
        // already ran and makes the addon's listener independent of NTM's initialization order.
        DamageEvents.install();
        DamageEvents.onHurt(NTMAdditions::onHurt);

        NeoForge.EVENT_BUS.addListener(NTMAdditions::onRegisterCommands);
    }

    private static void onRegisterCommands(RegisterCommandsEvent event) {
        MeteorCommand.register(event.getDispatcher());
    }

    /** Whether {@code player} has {@code item} equipped in any Curios slot. */
    public static boolean hasCurio(Player player, Item item) {
        return CuriosApi.getCuriosInventory(player)
                .map(handler -> handler.isEquipped(item))
                .orElse(false);
    }

    private static void onHurt(DamageEvents.DamageEvent event) {
        if (!"broadcast".equals(event.source().getMsgId())) return;
        if (!(event.entity() instanceof Player player)) return;

        if (WardManager.isMeteorProtected(player)
                || hasCurio(player, ModItems.METEOR_CHARM_CURIO.get())) {
            event.setAmount(0F);
        } else if (WardManager.isProtectionProtected(player)
                || hasCurio(player, ModItems.PROTECTION_CHARM_CURIO.get())) {
            event.setAmount(event.amount() * 0.5F);
        }
    }
}
