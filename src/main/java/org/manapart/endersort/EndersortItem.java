package org.manapart.endersort;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class EndersortItem extends BlockItem {
    public EndersortItem(EndersortBlock block) {
        super(block, new Item.Properties().tab(ItemGroupIF.instance));
    }

}
