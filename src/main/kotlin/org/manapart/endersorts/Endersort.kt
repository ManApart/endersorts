package org.manapart.endersorts

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents.ModifyEntries
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import org.manapart.endersorts.ModItems.ENDERSORT_ITEM
import org.manapart.endersorts.ModItems.ENDER_EXTENDER_ITEM


const val MODID = "endersorts"

object Endersort : ModInitializer{
    val icon by lazy { Item(Item.Properties()) }

    override fun onInitialize() {
        ModEntities.initialize()
        ModBlocks.initialize()
        ModItems.initialize()
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.REDSTONE_BLOCKS).register(ModifyEntries { itemGroup: FabricItemGroupEntries ->
                itemGroup.accept(ENDERSORT_ITEM)
                itemGroup.accept(ENDER_EXTENDER_ITEM)
        })
    }

}
