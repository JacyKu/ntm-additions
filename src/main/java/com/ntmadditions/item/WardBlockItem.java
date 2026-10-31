package com.ntmadditions.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;

public class WardBlockItem extends BlockItem {

    public WardBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context,
                                List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        String descriptionId = getDescriptionId(stack);
        tooltip.add(Component.translatable(descriptionId + ".desc")
                .withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(descriptionId + ".usage")
                .withStyle(ChatFormatting.AQUA));
    }
}
