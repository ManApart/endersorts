package org.manapart.endersort

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.ChestBlock
import java.util.HashSet
import java.util.ArrayList

class ChestFinder {
    private val radius = 5
    val containerPositions = ArrayList<BlockPos>()

    //Don't search these positions again
    private val searchedPositions: MutableSet<BlockPos> = HashSet()
    private val searchStarts = ArrayList<BlockPos>()

    fun resetSearch(initialSource: BlockPos?) {
        containerPositions.clear()
        searchedPositions.clear()
        searchStarts.clear()
        if (initialSource != null) {
            searchStarts.add(initialSource)
        }
    }

    fun isSearching(): Boolean = searchStarts.isNotEmpty()
    fun hasContainers(): Boolean = containerPositions.isNotEmpty()

    fun findContainers(world: Level?, initialSource: BlockPos?) {
        if (searchStarts.isEmpty() && initialSource != null) {
            searchStarts.add(initialSource)
        }
        if (searchStarts.isNotEmpty() && world != null) {
            val source = searchStarts.first()
            searchStarts.remove(source)
            findContainersInRadius(world, source)
        }
        //We have found every chest we're going to find
        if (searchStarts.isEmpty()) {
            //Don't keep holding onto all these positions
            searchedPositions.clear()
        }
    }

    private fun findContainersInRadius(world: Level, source: BlockPos) {
        val positions: MutableSet<BlockPos> = HashSet()
        for (x in source.x - radius..source.x + radius) {
            for (y in source.y - radius..source.y + radius) {
                for (z in source.z - radius..source.z + radius) {
                    val pos = BlockPos(x, y, z)
                    if (validContainerExistsHere(world, pos)) {
                        positions.add(pos)
                    }
                }
            }
        }
        containerPositions.addAll(positions)
        println("Found " + positions.size + " containers")
    }

    private fun validContainerExistsHere(world: Level, pos: BlockPos): Boolean {
        if (!searchedPositions.contains(pos)) {
            searchedPositions.add(pos)
            val block = world.getBlockState(pos).block
            if (block is EnderExtenderBlock) {
                searchStarts.add(pos)
            } else if (block is ChestBlock) {
                val inventory = HopperTileEntity.getContainerAt(world, pos)
                if (inventory !is EndersortEntity) {
                    if (inventory is ChestTileEntity) {
                        return true
                    } else if (inventory is DoubleSidedInventory) {
                        val neighborDirection = ChestBlock.getConnectedDirection(world.getBlockState(pos))
                        val neighborPos = pos.offset(neighborDirection.normal)
                        if (!searchedPositions.contains(neighborPos)) {
                            searchedPositions.add(neighborPos)
                            return true
                        }
                    }
                }
            }
        }
        return false
    }
}