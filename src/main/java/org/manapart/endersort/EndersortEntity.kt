package org.manapart.endersort

import net.minecraft.tileentity.ChestTileEntity
import org.manapart.endersort.ChestFinder
import net.minecraft.util.ResourceLocation
import net.minecraft.util.math.BlockPos
import java.util.HashMap
import net.minecraft.tileentity.TileEntityType
import org.manapart.endersort.Endersort
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.StringTextComponent
import net.minecraft.item.ItemStack
import net.minecraft.inventory.IInventory
import net.minecraft.tileentity.HopperTileEntity
import net.minecraft.inventory.DoubleSidedInventory
import net.minecraft.util.SoundEvents
import net.minecraft.util.SoundCategory
import java.util.HashSet

class EndersortEntity : ChestTileEntity() {
    private var transferCooldown = -1
    private val cooldownBuffer = 5
    private val chestFinder = ChestFinder()
    private var containerIndex = -1
    private var distributedItems = false
    private val storageHints: MutableMap<ResourceLocation, BlockPos> = mutableMapOf()

    override fun getType(): TileEntityType<*>  = Endersort.tileType

    override fun tick() {
        --transferCooldown
        if (transferCooldown <= 0 && !this.isEmpty) {
            transferCooldown = cooldownBuffer
            if (chestFinder.isSearching) {
                chestFinder.findContainers(getLevel(), worldPosition)
            } else if (chestFinder.hasContainers()) {
                distributeItemsByHints()
                distributeItems()
            } else {
                chestFinder.resetSearch(worldPosition)
            }
        }
    }

    override fun getDefaultName(): ITextComponent {
        return StringTextComponent("Endersort")
    }

    fun clearContainers() {
        chestFinder.resetSearch(worldPosition)
        storageHints.clear()
        containerIndex = -1
    }

    private fun distributeItemsByHints() {
        if (!storageHints.isEmpty()) {
            for (item in items) {
                if (!item.isEmpty) {
                    val matchName = item.item.registryName
                    val pos = storageHints[matchName]
                    if (pos != null) {
                        val chest = HopperTileEntity.getContainerAt(getLevel(), pos)
                        if (chest is ChestTileEntity || chest is DoubleSidedInventory) {
                            val matches = buildMatchMap(chest)
                            attemptToPush(item, chest, matches, pos)
                        } else {
                            println("Found invalid chest at " + pos.toShortString())
                            clearContainers()
                        }
                    }
                }
            }
        }
    }

    private fun distributeItems() {
        if (!chestFinder.hasContainers()) {
            return
        }
        containerIndex++
        if (containerIndex >= chestFinder.containerPositions.size) {
            containerIndex = 0
        }
        //        System.out.println("Distributing Items to " + containerIndex);
        val pos = chestFinder.containerPositions[containerIndex]
        val chest = HopperTileEntity.getContainerAt(getLevel(), pos)
        if (chest is ChestTileEntity || chest is DoubleSidedInventory) {
            pushItems(chest, pos)
        } else {
            clearContainers()
        }
        if (distributedItems) {
            distributedItems = false
            getLevel()!!.playSound(null, pos, SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1f, 1f)
        }
    }

    private fun pushItems(chest: IInventory, pos: BlockPos) {
        if (!chest.isEmpty) {
            val matches = buildMatchMap(chest)
            for (item in items) {
                attemptToPush(item, chest, matches, pos)
            }
        }
    }

    private fun buildMatchMap(chest: IInventory): Set<ResourceLocation?> {
        val matches: MutableSet<ResourceLocation?> = HashSet()
        for (i in 0 until chest.containerSize) {
            val destItem = chest.getItem(i)
            if (!destItem.isEmpty) {
                matches.add(destItem.item.registryName)
            }
        }
        return matches
    }

    private fun attemptToPush(item: ItemStack, destinationInventory: IInventory, matches: Set<ResourceLocation?>, pos: BlockPos) {
        val matchName: ResourceLocation = item.item.registryName!!
        if (!item.isEmpty && matches.contains(matchName)) {
            //Try to combine stacks first, then try full items
            pushStackable(item, destinationInventory, matchName)
            if (!item.isEmpty) {
                pushNewStack(item, destinationInventory)
            }
            if (item.isEmpty) {
                storageHints[matchName] = pos
                //                System.out.println("Distributed " + matchName + " to " + pos.toShortString());
            }
        }
    }

    private fun pushStackable(item: ItemStack, destinationInventory: IInventory, matchName: ResourceLocation?) {
        if (item.isStackable) {
            for (i in 0 until destinationInventory.containerSize) {
                val destItem = destinationInventory.getItem(i)
                if (destItem.isStackable && matchName == destItem.item.registryName) {
                    val itemCount = Math.min(destItem.maxStackSize - destItem.count, item.count)
                    if (itemCount > 0) {
                        destItem.count = destItem.count + itemCount
                        item.count = item.count - itemCount
                        if (destItem.isEmpty) {
                            destinationInventory.setChanged()
                        }
                        distributedItems = true
                        break
                    }
                }
            }
        }
    }

    private fun pushNewStack(item: ItemStack, destinationInventory: IInventory) {
        for (i in 0 until destinationInventory.containerSize) {
            var destItem = destinationInventory.getItem(i)
            if (destItem.isEmpty) {
                val itemCount = Math.min(destItem.maxStackSize - destItem.count, item.count)
                if (itemCount > 0) {
                    destinationInventory.setItem(i, item.copy())
                    destItem = destinationInventory.getItem(i)
                    destItem.count = itemCount
                    setChanged()
                    item.count = item.count - itemCount
                    if (destItem.isEmpty) {
                        destinationInventory.setChanged()
                    }
                    distributedItems = true
                    break
                }
            }
        }
    }
}