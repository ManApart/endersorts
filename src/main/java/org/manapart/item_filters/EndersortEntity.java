package org.manapart.item_filters;

import net.minecraft.inventory.DoubleSidedInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.HashSet;
import java.util.Set;


public class EndersortEntity extends ChestTileEntity {
    private int transferCooldown = -1;
    private int cooldownBuffer = 5;
    private ChestFinder chestFinder = new ChestFinder();
    private int containerIndex = -1;
    private boolean distributedItems = false;

    public EndersortEntity() {
    }

    @Override
    public TileEntityType<?> getType() {
        return Endersort.tileType;
    }

    @Override
    public void tick() {
        --this.transferCooldown;
        if (this.transferCooldown <= 0 && !this.isEmpty()) {
            this.transferCooldown = cooldownBuffer;
            if (chestFinder.isSearching()) {
                chestFinder.findContainers(this.getLevel(), this.worldPosition);
            } else if (chestFinder.hasContainers()) {
                distributeItems();
            } else {
                chestFinder.resetSearch(worldPosition);
            }
        }
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new StringTextComponent("Endersort");
    }

    public void clearContainers() {
        this.chestFinder.resetSearch(worldPosition);
        this.containerIndex = -1;
    }

    private void distributeItems() {
        containerIndex++;
        if (containerIndex >= chestFinder.getContainerPositions().size()) {
            containerIndex = 0;
        }
//        System.out.println("Distributing Items to " + containerIndex);
        BlockPos pos = chestFinder.getContainerPositions().get(containerIndex);
        IInventory chest = HopperTileEntity.getContainerAt(this.getLevel(), pos);
        if (chest instanceof ChestTileEntity || chest instanceof DoubleSidedInventory) {
            pushItems(chest, pos);
        } else {
            clearContainers();
        }
        if (distributedItems) {
            distributedItems = false;
            getLevel().playSound(null, pos, SoundEvents.ENDERMAN_TELEPORT, SoundCategory.PLAYERS, 1f, 1f);
        }
    }

    private void pushItems(IInventory chest, BlockPos pos) {
        if (!chest.isEmpty()) {
            Set<ResourceLocation> matches = buildMatchMap(chest);
            for (ItemStack item : this.getItems()) {
                attemptToPush(item, chest, matches, pos);
            }
        }
    }

    private Set<ResourceLocation> buildMatchMap(IInventory chest) {
        Set<ResourceLocation> matches = new HashSet<>();
        for (int i = 0; i < chest.getContainerSize(); i++) {
            ItemStack destItem = chest.getItem(i);
            if (!destItem.isEmpty()) {
                matches.add(destItem.getItem().getRegistryName());
            }
        }
        return matches;
    }

    private void attemptToPush(ItemStack item, IInventory destinationInventory, Set<ResourceLocation> matches, BlockPos pos) {
        ResourceLocation matchName = item.getItem().getRegistryName();
        if (!item.isEmpty() && matches.contains(matchName)) {
            //Try to combine stacks first, then try full items
            pushStackable(item, destinationInventory, matchName);
            if (!item.isEmpty()) {
                pushNewStack(item, destinationInventory);
            }

//            if (item.isEmpty()) {
//                System.out.println("Distributed " + matchName + " to " + pos.toShortString());
//            }
        }
    }

    private void pushStackable(ItemStack item, IInventory destinationInventory, ResourceLocation matchName) {
        if (item.isStackable()) {
            for (int i = 0; i < destinationInventory.getContainerSize(); i++) {
                ItemStack destItem = destinationInventory.getItem(i);
                if (destItem.isStackable() && matchName.equals(destItem.getItem().getRegistryName())) {
                    int itemCount = Math.min(destItem.getMaxStackSize() - destItem.getCount(), item.getCount());
                    if (itemCount > 0) {
                        destItem.setCount(destItem.getCount() + itemCount);
                        item.setCount(item.getCount() - itemCount);
                        if (destItem.isEmpty()) {
                            destinationInventory.setChanged();
                        }
                        distributedItems = true;
                        break;
                    }
                }
            }
        }
    }

    private void pushNewStack(ItemStack item, IInventory destinationInventory) {
        for (int i = 0; i < destinationInventory.getContainerSize(); i++) {
            ItemStack destItem = destinationInventory.getItem(i);
            if (destItem.isEmpty()) {
                int itemCount = Math.min(destItem.getMaxStackSize() - destItem.getCount(), item.getCount());
                if (itemCount > 0) {
                    destinationInventory.setItem(i, item.copy());
                    destItem = destinationInventory.getItem(i);
                    destItem.setCount(itemCount);
                    setChanged();
                    item.setCount(item.getCount() - itemCount);
                    if (destItem.isEmpty()) {
                        destinationInventory.setChanged();
                    }
                    distributedItems = true;
                    break;
                }
            }
        }
    }

}
