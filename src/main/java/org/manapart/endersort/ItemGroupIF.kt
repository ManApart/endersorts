package org.manapart.endersort

import net.minecraft.world.item.CreativeModeTab
import net.minecraft.world.item.ItemStack

class ItemGroupIF private constructor(index: Int, label: String) : CreativeModeTab(index, label) {
    override fun makeIcon(): ItemStack {
        return ItemStack(Endersort.endersortIcon)
    }

    companion object {
        val instance = ItemGroupIF(getGroupCountSafe(), "endersort")
    }
}