package org.manapart.endersort

import net.minecraft.world.item.Item
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.fml.common.Mod
import thedarkcolour.kotlinforforge.forge.MOD_BUS

const val MODID = "endersort"

@Mod(MODID)
object Endersort {

    init {
        ModBlocks.REGISTRY.register(MOD_BUS)
        ModItems.REGISTRY.register(MOD_BUS)
    }

    val tileType = createEntityType()
    val endersortIcon by lazy { createIcon() }

    private fun createIcon(): Item {
        return Item(Item.Properties()).also {
            it.setRegistryName("$MODID:es_icon")
        }
    }

    private fun createEntityType(): BlockEntityType<EndersortEntity> {
        return BlockEntityType.Builder.of({ pos, state -> EndersortEntity(pos, state) }, ModBlocks.endersortBlock)
            .build(null).also {
                it.setRegistryName("$MODID:endersort")
            }
    }


}