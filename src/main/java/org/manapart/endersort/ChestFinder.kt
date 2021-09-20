package org.manapart.endersort

import net.minecraft.util.math.BlockPos
import java.util.HashSet
import net.minecraft.world.World
import org.manapart.endersort.EnderExtenderBlock
import net.minecraft.block.ChestBlock
import net.minecraft.inventory.IInventory
import net.minecraft.tileentity.HopperTileEntity
import org.manapart.endersort.EndersortEntity
import net.minecraft.tileentity.ChestTileEntity
import net.minecraft.inventory.DoubleSidedInventory
import java.util.ArrayList

class ChestFinder {
    private val radius = 5
    val containerPositions = ArrayList<BlockPos>()

    //Don't search these positions again
    private val searchedPositions: MutableSet<BlockPos> = HashSet()
    private val searchStarts = ArrayList<BlockPos>()
    fun getContainerPositions(): List<BlockPos> {
        return containerPositions
    }

    fun resetSearch(initialSource: BlockPos?) {
        containerPositions.clear()
        searchedPositions.clear()
        searchStarts.clear()
        if (initialSource != null) {
            searchStarts.add(initialSource)
        }
    }

    val isSearching: Boolean
        get() = !searchStarts.isEmpty()

    fun hasContainers(): Boolean {
        return !containerPositions.isEmpty()
    }

    fun findContainers(world: World?, initialSource: BlockPos?) {
        if (searchStarts.isEmpty() && initialSource != null) {
            searchStarts.add(initialSource)
        }
        if (!searchStarts.isEmpty() && world != null) {
            val source = searchStarts[0]
            searchStarts.remove(source)
            findContainersInRadius(world, source)
        }
        //We have found every chest we're going to find
        if (searchStarts.isEmpty()) {
            //Don't keep holding onto all these positions
            searchedPositions.clear()
        }
    }

    private fun findContainersInRadius(world: World, source: BlockPos) {
        val sourceX = source.x
        val sourceY = source.y
        val sourceZ = source.z
        val positions: MutableSet<BlockPos> = HashSet()
        for (x in sourceX - radius..sourceX + radius) {
            for (y in sourceY - radius..sourceY + radius) {
                for (z in sourceZ - radius..sourceZ + radius) {
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

    private fun validContainerExistsHere(world: World, pos: BlockPos): Boolean {
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