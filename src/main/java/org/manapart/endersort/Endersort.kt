package org.manapart.endersort

import net.minecraft.item.Item
import net.minecraft.tileentity.TileEntityType
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.server.FMLServerAboutToStartEvent
import org.apache.logging.log4j.LogManager
import thedarkcolour.kotlinforforge.forge.FORGE_BUS
import thedarkcolour.kotlinforforge.forge.MOD_BUS

const val MODID = "endersort"

@Mod(MODID)
object Endersort {
    private val log = LogManager.getLogger()

    init {
        log.info("Endersort init")
        ModBlocks.REGISTRY.register(MOD_BUS)
        ModItems.REGISTRY.register(MOD_BUS)

        FORGE_BUS.addListener(::onServerAboutToStart)
    }

    val tileType = createEntityType()
    val endersortIcon by lazy{ createIcon() }

    private fun createIcon(): Item {
        return Item(Item.Properties()).also {
            it.setRegistryName("$MODID:es_icon")
        }
    }
    private fun onServerAboutToStart(event: FMLServerAboutToStartEvent) {
        log.info("Server starting...")
    }

    private fun createEntityType(): TileEntityType<EndersortEntity> {
        return TileEntityType.Builder.of({ EndersortEntity() }, ModBlocks.endersortBlock)
            .build(null).also {
                it.setRegistryName("$MODID:endersort")
            }
    }


}