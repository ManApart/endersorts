package org.manapart.item_filters;

import net.minecraft.block.Block;
import net.minecraft.block.ChestBlock;
import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ChestFinder {
    private final int radius = 5;

    private final ArrayList<BlockPos> containerPositions = new ArrayList<>();
    //Don't search these positions again
    private final Set<BlockPos> searchedPositions = new HashSet<>();
    //Don't add these (double chest) inventories a second time
    private final Set<IInventory> searchedInventories = new HashSet<>();
    private final ArrayList<BlockPos> searchStarts = new ArrayList<>();

    public List<BlockPos> getContainerPositions() {
        return this.containerPositions;
    }

    public void resetSearch(BlockPos initialSource) {
        this.containerPositions.clear();
        this.searchedPositions.clear();
        this.searchedInventories.clear();
        this.searchStarts.clear();
        if (initialSource != null) {
            this.searchStarts.add(initialSource);
        }
    }

    public boolean isSearching() {
        return !searchStarts.isEmpty();
    }

    public boolean hasContainers() {
        return !containerPositions.isEmpty();
    }

    public void findContainers(World world, BlockPos initialSource) {
        if (searchStarts.isEmpty() && initialSource != null) {
            searchStarts.add(initialSource);
        }
        if (!searchStarts.isEmpty() && world != null) {
            BlockPos source = searchStarts.get(0);
            searchStarts.remove(source);
            findContainersInRadius(world, source);
        }
        //We have found every chest we're going to find
        if (searchStarts.isEmpty()) {
            //Don't keep holding onto all these positions
            searchedPositions.clear();
            searchedInventories.clear();
        }
    }

    private void findContainersInRadius(World world, BlockPos source) {
        int sourceX = source.getX();
        int sourceY = source.getY();
        int sourceZ = source.getZ();
        Set<BlockPos> positions = new HashSet<>();
        for (int x = sourceX - radius; x <= sourceX + radius; x++) {
            for (int y = sourceY - radius; y <= sourceY + radius; y++) {
                for (int z = sourceZ - radius; z <= sourceZ + radius; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    if (!searchedPositions.contains(pos)) {
                        searchedPositions.add(pos);
                        Block block = world.getBlockState(pos).getBlock();
                        if (block instanceof EnderExtenderBlock) {
                            searchStarts.add(pos);
                        } else if (block instanceof ChestBlock) {
                            IInventory inventory = HopperTileEntity.getContainerAt(world, pos);
                            if ((inventory instanceof ChestTileEntity || inventory instanceof DoubleSidedInventory) && !(inventory instanceof EndersortEntity)) {
//                        System.out.println("Found Chest at " + pos.toShortString());
                                //Dedupe the two sided inventories
                                if (inventory instanceof DoubleSidedInventory) {
                                    Direction neighborDirection = ChestBlock.getConnectedDirection(world.getBlockState(pos));
                                    BlockPos neighborPos = pos.offset(neighborDirection.getNormal());
                                    if (!searchedInventories.contains(inventory) && !searchedPositions.contains(neighborPos)){
                                        positions.add(pos);
                                        searchedInventories.add(inventory);
                                        searchedPositions.add(neighborPos);
                                    }
                                } else if (!searchedInventories.contains(inventory)) {
                                    positions.add(pos);
                                    searchedInventories.add(inventory);
                                }
                            }
                        }
                    }
                }
            }
        }
        this.containerPositions.addAll(positions);
        System.out.println("Found " + positions.size() + " containers");
    }

}
