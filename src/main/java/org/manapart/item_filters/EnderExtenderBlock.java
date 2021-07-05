package org.manapart.item_filters;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;

public class EnderExtenderBlock extends Block {

    public EnderExtenderBlock() {
        super(createProps());
    }

    private static Properties createProps() {
        Material padMat = new Material.Builder(MaterialColor.COLOR_BLUE).build();
        Properties props = Properties.of(padMat);
        props.requiresCorrectToolForDrops();
        props.harvestTool(ToolType.PICKAXE);
        props.sound(SoundType.METAL);
        props.strength(4);
        return props;
    }

}
