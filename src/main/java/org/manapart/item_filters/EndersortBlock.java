package org.manapart.item_filters;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMerger;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class EndersortBlock extends ChestBlock {

    public EndersortBlock() {
        super(createProps(), () -> null);
    }

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
                world.playSound(null, pos, SoundEvents.ENDER_CHEST_OPEN, SoundCategory.PLAYERS, 1f, 1f);
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

    @Override
    public BlockRenderType getRenderShape(BlockState p_149645_1_) {
        return BlockRenderType.MODEL;
    }

    //Chest combine was throwing a null pointer. This hopefully tells the combiner that these chests are always just 1, no double chests
    @Override
    public TileEntityMerger.ICallbackWrapper<? extends ChestTileEntity> combine(BlockState p_225536_1_, World world, BlockPos pos, boolean p_225536_4_) {
        return new TileEntityMerger.ICallbackWrapper.Single(world.getBlockEntity(pos));
    }

    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState p_180655_1_, World world, BlockPos pos, Random rand) {
        for(int i = 0; i < 3; ++i) {
            int j = rand.nextInt(2) * 2 - 1;
            int k = rand.nextInt(2) * 2 - 1;
            double d0 = (double)pos.getX() + 0.5D + 0.25D * (double)j;
            double d1 = (double)((float)pos.getY() + rand.nextFloat());
            double d2 = (double)pos.getZ() + 0.5D + 0.25D * (double)k;
            double d3 = (double)(rand.nextFloat() * (float)j);
            double d4 = ((double)rand.nextFloat() - 0.5D) * 0.125D;
            double d5 = (double)(rand.nextFloat() * (float)k);
            world.addParticle(ParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5);
        }
    }

}
