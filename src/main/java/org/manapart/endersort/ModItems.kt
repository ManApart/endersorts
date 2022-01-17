package org.manapart.endersort

import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import org.manapart.endersort.ModBlocks.ENDERSORT_BLOCK
import org.manapart.endersort.ModBlocks.ENDER_EXTENDER_BLOCK
import thedarkcolour.kotlinforforge.forge.registerObject

object ModItems {
    val REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MODID)

    val ENDERSORT_ITEM by REGISTRY.registerObject("endersort_item") {
        EndersortItem(ENDERSORT_BLOCK)
    }

    val ENDER_EXTENDER_ITEM by REGISTRY.registerObject("ender_extender_item") {
        EnderExtenderItem(ENDER_EXTENDER_BLOCK)
    }
}