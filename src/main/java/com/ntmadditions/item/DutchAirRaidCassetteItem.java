package com.ntmadditions.item;

import com.hbm.items.machine.ItemCassette;
import com.ntmadditions.registry.ModSirenTracks;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

/**
 * Cassette item for the Dutch air raid siren track.
 */
public class DutchAirRaidCassetteItem extends ItemCassette {

    public DutchAirRaidCassetteItem(Item.Properties properties) {
        super(properties, ModSirenTracks.DUTCH_AIR_RAID);
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable("item.ntmadditions.cassette_dutch_air_raid");
    }
}
