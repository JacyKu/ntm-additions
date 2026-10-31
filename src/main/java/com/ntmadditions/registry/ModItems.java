package com.ntmadditions.registry;

import com.ntmadditions.NTMAdditions;
import com.ntmadditions.item.CurioCharmItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, NTMAdditions.MOD_ID);

    public static final DeferredHolder<Item, Item> METEOR_CHARM_CURIO =
            ITEMS.register("meteor_charm_curio",
                    () -> new CurioCharmItem(true, new Item.Properties().stacksTo(1)));

    public static final DeferredHolder<Item, Item> PROTECTION_CHARM_CURIO =
            ITEMS.register("protection_charm_curio",
                    () -> new CurioCharmItem(false, new Item.Properties().stacksTo(1)));

    private ModItems() {}
}
