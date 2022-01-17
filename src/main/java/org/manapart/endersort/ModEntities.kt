package org.manapart.endersort

import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraftforge.registries.DeferredRegister
import net.minecraftforge.registries.ForgeRegistries
import org.manapart.endersort.ModBlocks.ENDERSORT_BLOCK
import thedarkcolour.kotlinforforge.forge.registerObject

object ModEntities {

    val REGISTRY = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID)

    val ENDERSORT_BLOCK_ENTITY by REGISTRY.registerObject("endersort") {
        createEntityType()
    }

    private fun createEntityType(): BlockEntityType<EndersortEntity> {
        return BlockEntityType.Builder.of({ pos, state -> EndersortEntity(pos, state) }, ENDERSORT_BLOCK)
            .build(null).also {
                it.setRegistryName("$MODID:endersort")
            }
    }


}