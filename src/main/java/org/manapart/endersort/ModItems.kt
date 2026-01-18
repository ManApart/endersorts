package org.manapart.endersort

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.Item


object ModItems {

    fun <GenericItem : Item?> register(name: String?, itemFactory: (Item.Properties) -> GenericItem, settings: Item.Properties): GenericItem? {
        // Create the item key.
        val itemKey: ResourceKey<Item?> = ResourceKey.create(Registries.ITEM, ResourceLocation(MODID, name))

        val item: GenericItem = itemFactory(settings.setId(itemKey))

        // Register the item.
        Registry.register(BuiltInRegistries.ITEM, itemKey, item)

        return item
    }

//    val REGISTRY = DeferredRegister.create(ForgeRegistries.ITEMS, MODID)
//
//    val ENDERSORT_ITEM by REGISTRY.registerObject("endersort_item") {
//        EndersortItem(ModBlocks.ENDERSORT_BLOCK)
//    }
//
//    val ENDER_EXTENDER_ITEM by REGISTRY.registerObject("ender_extender_item") {
//        EnderExtenderItem(ModBlocks.ENDER_EXTENDER_BLOCK)
//    }
}
