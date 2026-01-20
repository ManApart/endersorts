package org.manapart.endersorts

import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.util.RandomSource
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.ChestBlock
import net.minecraft.world.level.block.DoubleBlockCombiner
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.entity.ChestBlockEntity
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.phys.BlockHitResult
import org.manapart.endersorts.ModEntities.ENDERSORT_BLOCK_ENTITY
import java.util.function.Supplier

fun createEndersortProps(): BlockBehaviour.Properties {
    return BlockBehaviour.Properties.of().apply {
        requiresCorrectToolForDrops()
        sound(SoundType.METAL)
        strength(4f)
    }
}

class EndersortBlock(props: Properties) : ChestBlock(Supplier { ENDERSORT_BLOCK_ENTITY }, SoundEvents.ENDER_CHEST_OPEN, SoundEvents.ENDER_CHEST_CLOSE, props) {
    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = EndersortEntity(pos, state)

    override fun useWithoutItem(state: BlockState, level: Level, pos: BlockPos, player: Player, blockHitResult: BlockHitResult): InteractionResult {
        if (!level.isClientSide) {
            val tileEntity = level.getBlockEntity(pos)
            if (tileEntity is EndersortEntity) {
                player.openMenu(tileEntity as EndersortEntity?)
                player.awardStat(Stats.INSPECT_HOPPER)
                level.playSound(null, pos, SoundEvents.ENDER_CHEST_OPEN, SoundSource.PLAYERS, 1f, 1f)
            }
        }
        return InteractionResult.PASS
    }

//    override fun onRemove(state: BlockState, world: Level, pos: BlockPos, newState: BlockState, isMoving: Boolean) {
//        if (state.block !== newState.block) {
//            val tileEntity = world.getBlockEntity(pos)
//            if (tileEntity is EndersortEntity) {
//                dropContents(world, pos, tileEntity)
//            }
//            super.onRemove(state, world, pos, newState, isMoving)
//        }
//    }
//

    override fun <T : BlockEntity> getTicker(level: Level, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? {
        return if (level.isClientSide) {
            super.getTicker(level, state, type)
        } else {
            createTickerHelper(type, ENDERSORT_BLOCK_ENTITY, EndersortEntity::sortItems)
        }
    }

    override fun getRenderShape(blockState: BlockState) = RenderShape.MODEL

    override fun combine(blockState: BlockState, level: Level, pos: BlockPos, bl: Boolean) =
        DoubleBlockCombiner.NeighborCombineResult.Single(level.getBlockEntity(pos) as ChestBlockEntity)

    override fun animateTick(blockState: BlockState, level: Level, pos: BlockPos, rand: RandomSource) {
        for (i in 0..2) {
            val j = rand.nextInt(2) * 2 - 1
            val k = rand.nextInt(2) * 2 - 1
            val d0 = pos.x.toDouble() + 0.5 + 0.25 * j.toDouble()
            val d1 = (pos.y.toFloat() + rand.nextFloat()).toDouble()
            val d2 = pos.z.toDouble() + 0.5 + 0.25 * k.toDouble()
            val d3 = (rand.nextFloat() * j.toFloat()).toDouble()
            val d4 = (rand.nextFloat().toDouble() - 0.5) * 0.125
            val d5 = (rand.nextFloat() * k.toFloat()).toDouble()
            level.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5)
        }
    }
}
