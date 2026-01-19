package org.manapart.endersorts

import net.minecraft.world.level.block.EndRodBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour

fun createEndersortProps(): BlockBehaviour.Properties {
    val props = BlockBehaviour.Properties.of()
    props.requiresCorrectToolForDrops()
    props.sound(SoundType.METAL)
    props.strength(4f)
    return props
}
class EndersortBlock : EndRodBlock(createEndersortProps())
//class EndersortBlock : ChestBlock(createProps(), Supplier { BlockEntityType(null, setOf(), null) }) {
//    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = EndersortEntity(pos, state)
//
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
//    override fun getRenderShape(p_149645_1_: BlockState): RenderShape = RenderShape.MODEL
//
//    override fun <T : BlockEntity?> getTicker(world: Level, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? {
//        return if (world.isClientSide) {
//            super.getTicker(world, state, type)
//        } else {
//            createTickerHelper(type, ENDERSORT_BLOCK_ENTITY, EndersortEntity::sortItems)
//        }
//    }
//
//    //Chest combine was throwing a null pointer. This hopefully tells the combiner that these chests are always just 1, no double chests
//    override fun combine(p_225536_1_: BlockState, world: Level, pos: BlockPos, p_225536_4_: Boolean): DoubleBlockCombiner.NeighborCombineResult<ChestBlockEntity> {
//        return DoubleBlockCombiner.NeighborCombineResult.Single(world.getBlockEntity(pos) as ChestBlockEntity)
//    }
//
//    override fun animateTick(p_220827_: BlockState, world: Level, pos: BlockPos, rand: RandomSource) {
//        for (i in 0..2) {
//            val j = rand.nextInt(2) * 2 - 1
//            val k = rand.nextInt(2) * 2 - 1
//            val d0 = pos.x.toDouble() + 0.5 + 0.25 * j.toDouble()
//            val d1 = (pos.y.toFloat() + rand.nextFloat()).toDouble()
//            val d2 = pos.z.toDouble() + 0.5 + 0.25 * k.toDouble()
//            val d3 = (rand.nextFloat() * j.toFloat()).toDouble()
//            val d4 = (rand.nextFloat().toDouble() - 0.5) * 0.125
//            val d5 = (rand.nextFloat() * k.toFloat()).toDouble()
//            world.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5)
//        }
//    }
//
//}
