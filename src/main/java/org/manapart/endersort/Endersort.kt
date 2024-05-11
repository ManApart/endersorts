package org.manapart.endersort

import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent
import net.minecraftforge.fml.common.Mod
import org.manapart.endersort.ModBlocks.ENDERSORT_BLOCK
import org.manapart.endersort.ModBlocks.ENDER_EXTENDER_BLOCK
import org.manapart.endersort.ModItems.ENDERSORT_ITEM
import org.manapart.endersort.ModItems.ENDER_EXTENDER_ITEM
import thedarkcolour.kotlinforforge.forge.MOD_BUS

const val MODID = "endersort"

@Mod(MODID)
object Endersort {

    init {
        ModBlocks.REGISTRY.register(MOD_BUS)
        ModItems.REGISTRY.register(MOD_BUS)
        ModEntities.REGISTRY.register(MOD_BUS)
        MOD_BUS.addListener(::buildContents)
    }


    val icon by lazy { Item(Item.Properties()) }

    private fun buildContents(event: BuildCreativeModeTabContentsEvent) {
        if (event.tabKey == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(ENDERSORT_ITEM)
            event.accept(ENDER_EXTENDER_ITEM)
            event.accept(ENDERSORT_BLOCK)
            event.accept(ENDER_EXTENDER_BLOCK)
        }
    }

}