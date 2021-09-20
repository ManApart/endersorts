package org.manapart.endersort

import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import org.manapart.endersort.Endersort
import org.manapart.endersort.ItemGroupIF

class ItemGroupIF private constructor(index: Int, label: String) : ItemGroup(index, label) {
    override fun makeIcon(): ItemStack {
        return ItemStack(Endersort.endersortIcon)
    }

    companion object {
        val instance = ItemGroupIF(getGroupCountSafe(), "endersort")
    }
}