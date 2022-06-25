package org.manapart.endersort

import net.minecraft.world.level.block.EndRodBlock
import net.minecraft.world.level.block.RodBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraft.world.level.material.MaterialColor


class EnderExtenderBlock : EndRodBlock(createProps())

private fun createProps(): BlockBehaviour.Properties {
    val padMat = Material.Builder(MaterialColor.COLOR_BLUE).build()
    val props = BlockBehaviour.Properties.of(padMat)
    props.requiresCorrectToolForDrops()
    props.sound(SoundType.METAL)
    props.strength(4f)
    return props
}
