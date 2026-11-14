package com.ntmadditions.client;

import com.ntmadditions.NTMAdditions;
import com.ntmadditions.registry.ModItems;
import com.ntmadditions.registry.ModSirenTracks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

/**
 * Client-only rendering hooks for NTM Additions.
 */
@EventBusSubscriber(modid = NTMAdditions.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientEvents {

    private ClientEvents() {}

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.register(
                (stack, tintIndex) -> tintIndex == 1
                        ? 0xFF000000 | ModSirenTracks.DUTCH_AIR_RAID.getColor()
                        : 0xFFFFFFFF,
                ModItems.DUTCH_AIR_RAID_CASSETTE.get());
    }
}
