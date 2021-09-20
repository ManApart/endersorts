package org.manapart.endersort

import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.KDeferredRegister

object ModBlocks {

    val endersortBlock = EndersortBlock()
    val enderExtenderBlock = EnderExtenderBlock()

    val REGISTRY = KDeferredRegister(ForgeRegistries.BLOCKS, MODID)

    val ENDERSORT_BLOCK by REGISTRY.registerObject("endersort_block") {
        endersortBlock
    }

    val ENDER_EXTENDER_BLOCK by REGISTRY.registerObject("ender_extender_block") {
        enderExtenderBlock
    }


}