package org.manapart.endersort

import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.registerObject

object ModBlocks {

    val REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID)

    val ENDERSORT_BLOCK by REGISTRY.registerObject("endersort_block") {
        EndersortBlock()
    }

    val ENDER_EXTENDER_BLOCK by REGISTRY.registerObject("ender_extender_block") {
        EnderExtenderBlock()
    }


}