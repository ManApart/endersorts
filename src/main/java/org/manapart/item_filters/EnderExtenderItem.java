package org.manapart.item_filters;

import net.minecraft.item.BlockItem;

public class EnderExtenderItem extends BlockItem {
    public EnderExtenderItem(EnderExtenderBlock block) {
        super(block, new Properties().tab(ItemGroupIF.instance));
    }
}
