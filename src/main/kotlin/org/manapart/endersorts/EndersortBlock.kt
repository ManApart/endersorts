package org.manapart.endersorts

import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.sounds.SoundEvents
import net.minecraft.util.RandomSource
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.ChestBlock
import net.minecraft.world.level.block.DoubleBlockCombiner
import net.minecraft.world.level.block.RenderShape
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.ChestBlockEntity
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import java.util.function.Supplier

fun createEndersortProps(): BlockBehaviour.Properties {
    return BlockBehaviour.Properties.of().apply {
        requiresCorrectToolForDrops()
        sound(SoundType.METAL)
        strength(4f)
    }
}

class EndersortBlock(props: Properties) : ChestBlock(Supplier { ModEntities.ENDERSORT_BLOCK_ENTITY }, SoundEvents.CHEST_OPEN, SoundEvents.CHEST_CLOSE, props) {
    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = EndersortBlockEntity(pos, state)

//    override fun use(state: BlockState, world: Level, pos: BlockPos, player: Player, hand: InteractionHand, rayTraceResult: BlockHitResult): InteractionResult {
//        if (!world.isClientSide) {
//            val tileEntity = world.getBlockEntity(pos)
//            if (tileEntity is EndersortEntity) {
//                player.openMenu(tileEntity as EndersortEntity?)
//                player.awardStat(Stats.INSPECT_HOPPER)
//                world.playSound(null, pos, SoundEvents.ENDER_CHEST_OPEN, SoundSource.PLAYERS, 1f, 1f)
//            }
//        }
//        return InteractionResult.PASS
//    }
//
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
//    override fun <T : BlockEntity?> getTicker(world: Level, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? {
//        return if (world.isClientSide) {
//            super.getTicker(world, state, type)
//        } else {
//            createTickerHelper(type, ENDERSORT_BLOCK_ENTITY, EndersortEntity::sortItems)
//        }
//    }


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
