package org.manapart.item_filters;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class EndersortBlock extends ChestBlock {
    public EndersortBlock() {
        super(createProps(), () -> {
//            return TileEntityType.Builder.of(ChestTileEntity::new, Blocks.CHEST).build(ChestTileEntity.class);
//            return TileEntityType.CHEST;
//            return Registry.register(Registry.BLOCK_ENTITY_TYPE, "endersort", TileEntityType.Builder.of(ChestTileEntity::new, Blocks.CHEST));
//            return new EndersortEntity(false);
            return null;
        });
    }

//    public static final TileEntityType<ChestTileEntity> CHEST = register("chest", TileEntityType.Builder.of(ChestTileEntity::new, Blocks.CHEST));

    private static AbstractBlock.Properties createProps() {
        Material padMat = new Material.Builder(MaterialColor.COLOR_BLUE).build();
        AbstractBlock.Properties props = AbstractBlock.Properties.of(padMat);
        props.requiresCorrectToolForDrops();
        props.harvestTool(ToolType.PICKAXE);
        props.sound(SoundType.METAL);
        props.strength(4);
        return props;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new EndersortEntity();
    }

    @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isClientSide) {
            TileEntity tileentity = world.getBlockEntity(pos);
            if (tileentity instanceof EndersortEntity) {
                player.openMenu((EndersortEntity) tileentity);
                player.awardStat(Stats.INSPECT_HOPPER);
            }
        }
        return ActionResultType.PASS;
    }

    @Override
    public void onRemove(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.getBlock() != newState.getBlock()) {
            TileEntity tileentity = world.getBlockEntity(pos);
            if (tileentity instanceof EndersortEntity) {
                InventoryHelper.dropContents(world, pos, (EndersortEntity) tileentity);
            }

            super.onRemove(state, world, pos, newState, isMoving);
        }
    }

}
