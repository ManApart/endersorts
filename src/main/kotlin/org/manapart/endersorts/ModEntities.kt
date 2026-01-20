package org.manapart.endersorts

import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.core.BlockPos
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.resources.Identifier
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.state.BlockState


object ModEntities {

    val ENDERSORT_BLOCK_ENTITY = register<EndersortBlockEntity>("endersort", ModBlocks.ENDERSORT_BLOCK) { pos, state ->
        EndersortBlockEntity(pos, state)
    }

    private fun <T : BlockEntity> register(
        name: String,
        block: Block,
        entityFactory: FabricBlockEntityTypeBuilder.Factory<out T>,
    ): BlockEntityType<T> {
        val id: Identifier = Identifier.fromNamespaceAndPath(MODID, name)
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.create(entityFactory, block).build())
    }
}

class EndersortBlockEntity(pos: BlockPos, state: BlockState) : BlockEntity(ModEntities.ENDERSORT_BLOCK_ENTITY, pos, state)
