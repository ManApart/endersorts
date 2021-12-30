package org.manapart.endersort

import net.minecraft.core.BlockPos
import net.minecraft.core.particles.ParticleTypes
import net.minecraft.sounds.SoundEvents
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.ChestBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.material.Material
import net.minecraft.world.level.material.MaterialColor
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

class EndersortBlock : ChestBlock(createProps(), Supplier { null }) {
    override fun createTileEntity(state: BlockState, world: IBlockReader): TileEntity = EndersortEntity()

    override fun use(state: BlockState, world: World, pos: BlockPos, player: PlayerEntity, hand: Hand, hit: BlockRayTraceResult): ActionResultType {
        if (!world.isClientSide) {
            val tileentity = world.getBlockEntity(pos)
            if (tileentity is EndersortEntity) {
                player.openMenu(tileentity as EndersortEntity?)
                player.awardStat(Stats.INSPECT_HOPPER)
                world.playSound(null, pos, SoundEvents.ENDER_CHEST_OPEN, SoundCategory.PLAYERS, 1f, 1f)
            }
        }
        return ActionResultType.PASS
    }

    override fun onRemove(state: BlockState, world: World, pos: BlockPos, newState: BlockState, isMoving: Boolean) {
        if (state.block !== newState.block) {
            val tileEntity = world.getBlockEntity(pos)
            if (tileEntity is EndersortEntity) {
                InventoryHelper.dropContents(world, pos, tileEntity)
            }
            super.onRemove(state, world, pos, newState, isMoving)
        }
    }

    override fun getRenderShape(p_149645_1_: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

    //Chest combine was throwing a null pointer. This hopefully tells the combiner that these chests are always just 1, no double chests
    override fun combine(p_225536_1_: BlockState, world: World, pos: BlockPos, p_225536_4_: Boolean): ICallbackWrapper<ChestTileEntity> {
        return ICallbackWrapper.Single(world.getBlockEntity(pos) as ChestTileEntity)
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