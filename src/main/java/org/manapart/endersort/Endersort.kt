package org.manapart.endersort

import net.minecraft.item.Item
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod

const val MODID = "endersort"

@Mod(MODID)
object Endersort {

    init {
        MinecraftForge.EVENT_BUS.register(this)
    }

    val tileType = createEntityType()
    var endersortIcon = createIcon()

    private fun createIcon(): Item {
        return Item(Item.Properties()).also {
            it.setRegistryName("$MODID:es_icon")
        }
    }


    private fun createEntityType(): TileEntityType<EndersortEntity> {
        return TileEntityType.Builder.of({ EndersortEntity() }, ModBlocks.ENDERSORT_BLOCK)
            .build(null).also {
                it.setRegistryName("$MODID:endersort")
            }
    }


}