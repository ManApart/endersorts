package org.manapart.endersort

import net.minecraft.block.AbstractBlock.Properties
import net.minecraft.block.Block
import net.minecraft.block.SoundType
import net.minecraft.block.material.Material
import net.minecraft.block.material.MaterialColor
import net.minecraftforge.common.ToolType

class EnderExtenderBlock : Block(createProps())

private fun createProps(): Properties {
    val padMat = Material.Builder(MaterialColor.COLOR_BLUE).build()
    val props = Properties.of(padMat)
    props.requiresCorrectToolForDrops()
    props.harvestTool(ToolType.PICKAXE)
    props.sound(SoundType.METAL)
    props.strength(4f)
    return props
}
