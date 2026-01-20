package org.manapart.endersorts

import net.minecraft.world.level.block.EndRodBlock
import net.minecraft.world.level.block.SoundType
import net.minecraft.world.level.block.state.BlockBehaviour


class EnderExtenderBlock : EndRodBlock(createProps())

private fun createProps(): BlockBehaviour.Properties {
    return BlockBehaviour.Properties.of().apply {
//    requiresCorrectToolForDrops()
        sound(SoundType.METAL)
        strength(4f)
    }
}
