package org.manapart.endersorts

import net.minecraft.core.BlockPos
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.entity.ChestBlockEntity
import net.minecraft.world.level.block.state.BlockState

class EndersortEntity(pos: BlockPos, state: BlockState) : ChestBlockEntity(pos, state) {
    companion object {
        fun sortItems(world: Level?, pos: BlockPos?, state: BlockState?, entity: ChestBlockEntity) {
            (entity as EndersortEntity).tickInternal()
        }
    }

    //    private var transferCooldown = -1
//    private val cooldownBuffer = 5
//    private val chestFinder = ChestFinder()
//    private var containerIndex = -1
//    private var distributedItems = false
//    private val storageHints: MutableMap<String, BlockPos> = mutableMapOf()
//
//    override fun getType(): BlockEntityType<*> = ModEntities.ENDERSORT_BLOCK_ENTITY
//    override fun getName(): MutableComponent = Component.literal("")
//
//    override fun getDefaultName(): Component = Component.literal("Endersort")
//
//    companion object {
//        fun sortItems(world: Level?, pos: BlockPos?, state: BlockState?, entity: ChestBlockEntity) {
//            (entity as EndersortEntity).tickInternal()
//        }
//    }
//
    fun tickInternal() {
        println("Ticking")

//        --transferCooldown
//        if (transferCooldown <= 0 && !this.isEmpty) {
//            transferCooldown = cooldownBuffer
//            if (chestFinder.isSearching()) {
//                chestFinder.findContainers(getLevel(), worldPosition)
//            } else if (chestFinder.hasContainers()) {
//                distributeItemsByHints()
//                distributeItems()
//            } else {
//                chestFinder.resetSearch(worldPosition)
//            }
//        }
    }
//
//    private fun clearContainers() {
//        chestFinder.resetSearch(worldPosition)
//        storageHints.clear()
//        containerIndex = -1
//    }
//
//    private fun distributeItemsByHints() {
//        if (storageHints.isNotEmpty()) {
//            for (item in items) {
//                if (!item.isEmpty) {
//                    val matchName = item.item.descriptionId
//                    val pos = storageHints[matchName]
//                    if (pos != null) {
//                        val chest = HopperBlockEntity.getContainerAt(getLevel()!!, pos)
//                        if (chest is ChestBlockEntity || chest is CompoundContainer) {
//                            val matches = buildMatchMap(chest)
//                            attemptToPush(item, chest, matches, pos)
//                        } else {
//                            println("Found invalid chest at " + pos.toShortString())
//                            clearContainers()
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    private fun distributeItems() {
//        if (!chestFinder.hasContainers()) {
//            return
//        }
//        containerIndex++
//        if (containerIndex >= chestFinder.containerPositions.size) {
//            containerIndex = 0
//        }
//        //        System.out.println("Distributing Items to " + containerIndex);
//        val pos = chestFinder.containerPositions[containerIndex]
//        val chest = HopperBlockEntity.getContainerAt(getLevel()!!, pos)
//        if (chest is ChestBlockEntity || chest is CompoundContainer) {
//            pushItems(chest, pos)
//        } else {
//            clearContainers()
//        }
//        if (distributedItems) {
//            distributedItems = false
//            getLevel()?.playSound(null, pos, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1f, 1f)
//        }
//    }
//
//    private fun pushItems(chest: Container, pos: BlockPos) {
//        if (!chest.isEmpty) {
//            val matches = buildMatchMap(chest)
//            for (item in items) {
//                attemptToPush(item, chest, matches, pos)
//            }
//        }
//    }
//
//    private fun buildMatchMap(chest: Container): Set<String> {
//        val matches: MutableSet<String> = HashSet()
//        for (i in 0 until chest.containerSize) {
//            val destItem = chest.getItem(i)
//            if (!destItem.isEmpty) {
//                matches.add(destItem.item.descriptionId)
//            }
//        }
//        return matches
//    }
//
//    private fun attemptToPush(item: ItemStack, destinationInventory: Container, matches: Set<String>, pos: BlockPos) {
//        val matchName: String = item.item.descriptionId
//        if (!item.isEmpty && matches.contains(matchName)) {
//            //Try to combine stacks first, then try full items
//            pushStackable(item, destinationInventory, matchName)
//            if (!item.isEmpty) {
//                pushNewStack(item, destinationInventory)
//            }
//            if (item.isEmpty) {
//                storageHints[matchName] = pos
//                //                System.out.println("Distributed " + matchName + " to " + pos.toShortString());
//            }
//        }
//    }
//
//    private fun pushStackable(item: ItemStack, destinationInventory: Container, matchName: String) {
//        if (item.isStackable) {
//            for (i in 0 until destinationInventory.containerSize) {
//                val destItem = destinationInventory.getItem(i)
//                if (destItem.isStackable && matchName == destItem.item.descriptionId) {
//                    val itemCount = min(destItem.maxStackSize - destItem.count, item.count)
//                    if (itemCount > 0) {
//                        destItem.count = destItem.count + itemCount
//                        item.count = item.count - itemCount
//                        if (destItem.isEmpty) {
//                            destinationInventory.setChanged()
//                        }
//                        distributedItems = true
//                        break
//                    }
//                }
//            }
//        }
//    }
//
//    private fun pushNewStack(item: ItemStack, destinationInventory: Container) {
//        for (i in 0 until destinationInventory.containerSize) {
//            var destItem = destinationInventory.getItem(i)
//            if (destItem.isEmpty) {
//                val itemCount = min(destItem.maxStackSize - destItem.count, item.count)
//                if (itemCount > 0) {
//                    destinationInventory.setItem(i, item.copy())
//                    destItem = destinationInventory.getItem(i)
//                    destItem.count = itemCount
//                    setChanged()
//                    item.count = item.count - itemCount
//                    if (destItem.isEmpty) {
//                        destinationInventory.setChanged()
//                    }
//                    distributedItems = true
//                    break
//                }
//            }
//        }
//    }
}
