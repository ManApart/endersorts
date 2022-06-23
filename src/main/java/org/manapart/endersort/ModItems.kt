package org.manapart.endersort

import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject

object ModItems {
    val REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MODID)

    val ENDERSORT_ITEM by REGISTRY.registerObject("endersort_item") {
        EndersortItem(ModBlocks.ENDERSORT_BLOCK)
    }

    val ENDER_EXTENDER_ITEM by REGISTRY.registerObject("ender_extender_item") {
        EnderExtenderItem(ModBlocks.ENDER_EXTENDER_BLOCK)
    }
}