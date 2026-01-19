package org.manapart.endersorts

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item


const val MODID = "endersorts"

object Endersort : ModInitializer{
    val icon by lazy { Item(Item.Properties()) }

    override fun onInitialize() {
        ModBlocks.initialize()
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(ModifyEntries { itemGroup: FabricItemGroupEntries ->
            itemGroup.accept(ModBlocks.ENDERSORT_BLOCK.asItem())
//            itemGroup.accept(ModBlocks.ENDER_EXTENDER_BLOCK.asItem())
        })
//        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.REDSTONE_BLOCKS)
//            .register(ModifyEntries { itemGroup: FabricItemGroupEntries ->
//                itemGroup.accept(ENDERSORT_ITEM)
//                itemGroup.accept(ENDER_EXTENDER_ITEM)
//                //TODO - also except blocks
//            })
    }

}
