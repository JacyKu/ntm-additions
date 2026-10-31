package com.ntmadditions.registry;

import com.ntmadditions.NTMAdditions;
import com.ntmadditions.item.CurioCharmItem;
import com.ntmadditions.item.WardBlockItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
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

    public static final DeferredHolder<Item, BlockItem> METEOR_WARD =
            ITEMS.register("meteor_ward",
                    () -> new WardBlockItem(ModBlocks.METEOR_WARD.get(), new Item.Properties()));

    public static final DeferredHolder<Item, BlockItem> PROTECTION_WARD =
            ITEMS.register("protection_ward",
                    () -> new WardBlockItem(ModBlocks.PROTECTION_WARD.get(), new Item.Properties()));

    private ModItems() {}
}
