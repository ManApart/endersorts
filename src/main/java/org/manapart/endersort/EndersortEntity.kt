package org.manapart.endersort

import net.minecraft.core.BlockPos
import net.minecraft.network.chat.Component
import net.minecraft.network.chat.MutableComponent
import net.minecraft.network.chat.TextComponent
import net.minecraft.resources.ResourceLocation
import net.minecraft.sounds.SoundEvents
import net.minecraft.sounds.SoundSource
import net.minecraft.world.CompoundContainer
import net.minecraft.world.Container
import net.minecraft.world.item.ItemStack
import net.minecraft.world.level.Level
import net.minecraft.world.level.block.BaseEntityBlock
import net.minecraft.world.level.block.entity.*
import net.minecraft.world.level.block.state.BlockState
import kotlin.math.min

fun <T : BlockEntity?> serverTick(world: Level, state: BlockState, type: BlockEntityType<T>) {

}

class EndersortEntity(pos: BlockPos, state: BlockState) : ChestBlockEntity(pos, state) {
    private var transferCooldown = -1
    private val cooldownBuffer = 5
    private val chestFinder = ChestFinder()
    private var containerIndex = -1
    private var distributedItems = false
    private val storageHints: MutableMap<ResourceLocation, BlockPos> = mutableMapOf()

    override fun getType(): BlockEntityType<*> = Endersort.tileType
    override fun getName(): MutableComponent = TextComponent("")

    override fun getDefaultName(): TextComponent = TextComponent("Endersort")

//    override fun newBlockEntity(pos: BlockPos, state: BlockState): BlockEntity = EndersortEntity(pos, state)

//    override fun <T : BlockEntity?> getTicker(world: Level, state: BlockState, type: BlockEntityType<T>): BlockEntityTicker<T>? {
//        return if (level?.isClientSide == true) null else {
//            BaseEntityBlock.createTickerHelper(type, Endersort.tileType, ::serverTick);
//        }
//    }

    fun tick() {
        --transferCooldown
        if (transferCooldown <= 0 && !this.isEmpty) {
            transferCooldown = cooldownBuffer
            if (chestFinder.isSearching()) {
                chestFinder.findContainers(getLevel(), worldPosition)
            } else if (chestFinder.hasContainers()) {
                distributeItemsByHints()
                distributeItems()
            } else {
                chestFinder.resetSearch(worldPosition)
            }
        }
    }

    private fun clearContainers() {
        chestFinder.resetSearch(worldPosition)
        storageHints.clear()
        containerIndex = -1
    }

    private fun distributeItemsByHints() {
        if (storageHints.isNotEmpty()) {
            for (item in items) {
                if (!item.isEmpty) {
                    val matchName = item.item.registryName
                    val pos = storageHints[matchName]
                    if (pos != null) {
                        val chest = HopperBlockEntity.getContainerAt(getLevel()!!, pos)
                        if (chest is ChestBlockEntity || chest is CompoundContainer) {
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
        val chest = HopperBlockEntity.getContainerAt(getLevel()!!, pos)
        if (chest is ChestBlockEntity || chest is CompoundContainer) {
            pushItems(chest, pos)
        } else {
            clearContainers()
        }
        if (distributedItems) {
            distributedItems = false
            getLevel()?.playSound(null, pos, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1f, 1f)
        }
    }

    private fun pushItems(chest: Container, pos: BlockPos) {
        if (!chest.isEmpty) {
            val matches = buildMatchMap(chest)
            for (item in items) {
                attemptToPush(item, chest, matches, pos)
            }
        }
    }

    private fun buildMatchMap(chest: Container): Set<ResourceLocation?> {
        val matches: MutableSet<ResourceLocation?> = HashSet()
        for (i in 0 until chest.containerSize) {
            val destItem = chest.getItem(i)
            if (!destItem.isEmpty) {
                matches.add(destItem.item.registryName)
            }
        }
        return matches
    }

    private fun attemptToPush(item: ItemStack, destinationInventory: Container, matches: Set<ResourceLocation?>, pos: BlockPos) {
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

    private fun pushStackable(item: ItemStack, destinationInventory: Container, matchName: ResourceLocation?) {
        if (item.isStackable) {
            for (i in 0 until destinationInventory.containerSize) {
                val destItem = destinationInventory.getItem(i)
                if (destItem.isStackable && matchName == destItem.item.registryName) {
                    val itemCount = min(destItem.maxStackSize - destItem.count, item.count)
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

    private fun pushNewStack(item: ItemStack, destinationInventory: Container) {
        for (i in 0 until destinationInventory.containerSize) {
            var destItem = destinationInventory.getItem(i)
            if (destItem.isEmpty) {
                val itemCount = min(destItem.maxStackSize - destItem.count, item.count)
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
//
//    class EnderSortTicker<T : BlockEntity> : BlockEntityTicker<T> {
//        override fun tick(p_155253_: Level, p_155254_: BlockPos, p_155255_: BlockState, p_155256_: T) {
//            TODO("Not yet implemented")
//        }
//    }

}