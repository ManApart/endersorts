package org.manapart.endersort

import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object ModItems {
    val REGISTRY = KDeferredRegister(ForgeRegistries.ITEMS, MODID)

    val ENDERSORT_ITEM by REGISTRY.registerObject("$MODID:endersort_item") {
        EndersortItem(ModBlocks.ENDERSORT_BLOCK)
    }

    val ENDER_EXTENDER_ITEM by REGISTRY.registerObject("$MODID:ender_extender_item") {
        EnderExtenderItem(ModBlocks.ENDER_EXTENDER_BLOCK)
    }
}