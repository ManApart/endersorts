package org.manapart.endersorts

import net.minecraft.world.level.block.EndRodBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour


class EnderExtenderBlock : EndRodBlock(createProps())

private fun createProps(): BlockBehaviour.Properties {
    val props = BlockBehaviour.Properties.of()
    props.requiresCorrectToolForDrops()
    props.sound(SoundType.METAL)
    props.strength(4f)
    return props
}
