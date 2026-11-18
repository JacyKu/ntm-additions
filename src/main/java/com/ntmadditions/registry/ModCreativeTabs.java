package com.ntmadditions.registry;

import com.ntmadditions.NTMAdditions;
import com.ntmadditions.config.NTMAdditionsConfig;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModCreativeTabs {

    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, NTMAdditions.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN =
            TABS.register("main", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.ntmadditions"))
                    .icon(() -> new ItemStack(ModItems.METEOR_CHARM_CURIO.get()))
                    .displayItems((parameters, output) -> {
                        if (NTMAdditionsConfig.ENABLE_CURIOS_CHARMS.get()) {
                            output.accept(ModItems.METEOR_CHARM_CURIO.get());
                            output.accept(ModItems.PROTECTION_CHARM_CURIO.get());
                        }
                        if (NTMAdditionsConfig.ENABLE_WARDS.get()) {
                            output.accept(ModItems.METEOR_WARD.get());
                            output.accept(ModItems.PROTECTION_WARD.get());
                        }
                        if (NTMAdditionsConfig.ENABLE_SIREN.get()) {
                            output.accept(ModItems.DUTCH_AIR_RAID_CASSETTE.get());
                        }
                    })
                    .build());

    private ModCreativeTabs() {}
}
