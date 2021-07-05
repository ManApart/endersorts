package org.manapart.endersort;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ItemGroupIF extends ItemGroup {
    public static final ItemGroupIF instance = new ItemGroupIF(ItemGroup.getGroupCountSafe(), "endersort");

    private ItemGroupIF(int index, String label) {
        super(index, label);
    }

    @Override
    public ItemStack makeIcon() {
        return new ItemStack(Endersort.endersortIcon);
    }

}
