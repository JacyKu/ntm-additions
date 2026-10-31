package com.ntmadditions.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

/**
 * A Curios-slot counterpart to NTM's armor-insert charms.
 *
 * <p>It deliberately does not extend {@code ItemModCharm}/{@code ItemArmorMod}, so it cannot be
 * inserted into a helmet and the original NTM items keep their existing behavior and recipes.
 */
public class CurioCharmItem extends Item implements ICurioItem {

    private final boolean meteor;

    public CurioCharmItem(boolean meteor, Properties properties) {
        super(properties);
        this.meteor = meteor;
    }

    public boolean stopsMeteors() {
        return meteor;
    }

    public boolean repelsMeteors() {
        return !meteor;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
                                List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.translatable("item.ntmadditions.charm.blessed")
                .withStyle(ChatFormatting.AQUA));

        if (meteor) {
            tooltip.add(Component.translatable("item.ntmadditions.charm.meteor.spawning")
                    .withStyle(ChatFormatting.AQUA));
            tooltip.add(Component.translatable("item.ntmadditions.charm.meteor.damage")
                    .withStyle(ChatFormatting.AQUA));
        } else {
            tooltip.add(Component.translatable("item.ntmadditions.charm.protection.divert")
                    .withStyle(ChatFormatting.AQUA));
            tooltip.add(Component.translatable("item.ntmadditions.charm.protection.blocks")
                    .withStyle(ChatFormatting.AQUA));
            tooltip.add(Component.translatable("item.ntmadditions.charm.protection.damage")
                    .withStyle(ChatFormatting.AQUA));
        }
    }
}
