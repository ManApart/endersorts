package org.manapart.endersort

import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.server.level.ServerLevel
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.stats.Stats
import net.minecraft.world.Containers.dropContents
import net.minecraft.world.InteractionHand
import net.minecraft.world.InteractionResult
import net.minecraft.world.entity.player.Player
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.*
import net.minecraft.world.level.block.entity.BlockEntity
import net.minecraft.world.level.block.entity.BlockEntityTicker
import net.minecraft.world.level.block.entity.BlockEntityType
import net.minecraft.world.level.block.entity.ChestBlockEntity
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import net.minecraft.world.level.material.MaterialColor
import net.minecraft.world.phys.BlockHitResult
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import java.util.*
import java.util.function.Supplier

private fun createProps(): BlockBehaviour.Properties {
    val padMat = Material.Builder(MaterialColor.COLOR_BLUE).build()
    val props = BlockBehaviour.Properties.of(padMat)
    props.requiresCorrectToolForDrops()
    props.sound(SoundType.METAL)
    props.strength(4f)
    return props
}

//internal class EndersortProvider() : Supplier<BlockEntityType<EndersortEntity>> {
//    override fun get(): TeleporterNetwork = TeleporterNetwork(world)
//}

class EndersortBlock : ChestBlock(createProps(), Supplier { BlockEntityType(null, setOf(), null) }) {
    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = EndersortEntity(pos, state)

    override fun use(state: BlockState, world: Level, pos: BlockPos, player: Player, hand: InteractionHand, rayTraceResult: BlockHitResult): InteractionResult {
        if (!world.isClientSide) {
            val tileEntity = world.getBlockEntity(pos)
            if (tileEntity is EndersortEntity) {
                player.openMenu(tileEntity as EndersortEntity?)
                player.awardStat(Stats.INSPECT_HOPPER)
                world.playSound(null, pos, SoundEvents.ENDER_CHEST_OPEN, SoundSource.PLAYERS, 1f, 1f)
            }
        }
        return InteractionResult.PASS
    }

    override fun onRemove(state: BlockState, world: Level, pos: BlockPos, newState: BlockState, isMoving: Boolean) {
        if (state.block !== newState.block) {
            val tileEntity = world.getBlockEntity(pos)
            if (tileEntity is EndersortEntity) {
                dropContents(world, pos, tileEntity)
            }
            super.onRemove(state, world, pos, newState, isMoving)
        }
    }

    override fun getRenderShape(p_149645_1_: BlockState): RenderShape = RenderShape.MODEL

    override fun <T : BlockEntity?> getTicker(world: Level, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? {
        return if (world.isClientSide) {
            super.getTicker(world, state, type)
        } else {
            createTickerHelper(type, blockEntityType(), EndersortEntity::sortItems)
        }
    }

    //Chest combine was throwing a null pointer. This hopefully tells the combiner that these chests are always just 1, no double chests
    override fun combine(p_225536_1_: BlockState, world: Level, pos: BlockPos, p_225536_4_: Boolean): DoubleBlockCombiner.NeighborCombineResult<ChestBlockEntity> {
        return DoubleBlockCombiner.NeighborCombineResult.Single(world.getBlockEntity(pos) as ChestBlockEntity)
    }

    override fun tick(state: BlockState, world: ServerLevel, pos: BlockPos, p_153062_: Random) {
        val blockEntity = world.getBlockEntity(pos)!!
//        if (blockEntity is EndersortEntity) {
//            blockEntity.tickInternal()
//            blockEntity.recheckOpen()
//        }
    }

    @OnlyIn(Dist.CLIENT)
    override fun animateTick(p_180655_1_: BlockState, world: Level, pos: BlockPos, rand: Random) {
        for (i in 0..2) {
            val j = rand.nextInt(2) * 2 - 1
            val k = rand.nextInt(2) * 2 - 1
            val d0 = pos.x.toDouble() + 0.5 + 0.25 * j.toDouble()
            val d1 = (pos.y.toFloat() + rand.nextFloat()).toDouble()
            val d2 = pos.z.toDouble() + 0.5 + 0.25 * k.toDouble()
            val d3 = (rand.nextFloat() * j.toFloat()).toDouble()
            val d4 = (rand.nextFloat().toDouble() - 0.5) * 0.125
            val d5 = (rand.nextFloat() * k.toFloat()).toDouble()
            world.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5)
        }
    }

}