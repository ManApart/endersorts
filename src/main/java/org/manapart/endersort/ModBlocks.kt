package org.manapart.endersort

import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object ModBlocks {

    val REGISTRY = KDeferredRegister(ForgeRegistries.BLOCKS, MODID)

    val ENDERSORT_BLOCK by REGISTRY.registerObject("$MODID:endersort_block") {
        EndersortBlock()
    }

    val ENDER_EXTENDER_BLOCK by REGISTRY.registerObject("$MODID:ender_extender_block") {
        EnderExtenderBlock()
    }




}