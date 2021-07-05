package org.manapart.endersort;

import net.minecraft.item.BlockItem;

public class EnderExtenderItem extends BlockItem {
    public EnderExtenderItem(EnderExtenderBlock block) {
        super(block, new Properties().tab(ItemGroupIF.instance));
    }
}
