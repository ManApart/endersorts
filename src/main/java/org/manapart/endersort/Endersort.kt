package org.manapart.endersort

import net.minecraft.world.item.Item
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

const val MODID = "endersort"

@Mod(MODID)
object Endersort {

    init {
        ModBlocks.REGISTRY.register(MOD_BUS)
        ModItems.REGISTRY.register(MOD_BUS)
        ModEntities.REGISTRY.register(MOD_BUS)
    }


    val icon by lazy { Item(Item.Properties()) }

}