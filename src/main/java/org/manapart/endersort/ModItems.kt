package org.manapart.endersort

import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object ModItems {
    val REGISTRY = KDeferredRegister(ForgeRegistries.ITEMS, MODID)

    val ENDERSORT_ITEM by REGISTRY.registerObject("endersort_item") {
        EndersortItem(ModBlocks.endersortBlock)
    }

    val ENDER_EXTENDER_ITEM by REGISTRY.registerObject("ender_extender_item") {
        EnderExtenderItem(ModBlocks.enderExtenderBlock)
    }
}