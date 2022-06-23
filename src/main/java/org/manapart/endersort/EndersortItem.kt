package org.manapart.endersort

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTab

class EndersortItem(block: EndersortBlock) : BlockItem(block, Properties().tab(CreativeModeTab.TAB_MISC))