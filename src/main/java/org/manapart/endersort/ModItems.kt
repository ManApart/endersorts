package org.manapart.endersort

import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.item.Item


object ModItems {

    fun <GenericItem : Item?> register(name: String?, settings: Item.Properties = Item.Properties(), itemFactory: (Item.Properties) -> GenericItem): GenericItem? {
        val itemKey: ResourceKey<Item?> = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(MODID, name))
        val item: GenericItem = itemFactory(settings.setId(itemKey))
        Registry.register(BuiltInRegistries.ITEM, itemKey, item)

        return item
    }

    val ENDERSORT_ITEM = register("endersort_item") {
        EndersortItem(ModBlocks.ENDERSORT_BLOCK)
    }

    val ENDER_EXTENDER_ITEM = register("ender_extender_item") {
        EnderExtenderItem(ModBlocks.ENDER_EXTENDER_BLOCK)
    }
}
