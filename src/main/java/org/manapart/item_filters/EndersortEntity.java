package org.manapart.item_filters;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class EndersortEntity extends ChestTileEntity {

    private int transferCooldown = -1;
    private int cooldownBuffer = 20;
    private ArrayList<BlockPos> containerPositions = new ArrayList<>();
    private int containerIndex = -1;
    private int radius = 5;

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
            if (containerPositions.isEmpty()) {
                findContainers();
            } else {
                distributeItems();
            }
        }
    }

    @Override
    protected ITextComponent getDefaultName() {
        return new StringTextComponent("Endersort");
    }

    public void clearContainers() {
        this.containerPositions.clear();
        this.containerIndex = -1;
    }

    private void findContainers() {
        int sourceX = this.worldPosition.getX();
        int sourceY = this.worldPosition.getY();
        int sourceZ = this.worldPosition.getZ();
        Set<BlockPos> positions = new HashSet<>();
        for (int x = sourceX - radius; x < sourceX + radius; x++) {
            for (int y = sourceY - radius; y < sourceY + radius; y++) {
                for (int z = sourceZ - radius; z < sourceZ + radius; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    IInventory inventory = HopperTileEntity.getContainerAt(this.getLevel(), pos);
                    if (inventory instanceof ChestTileEntity && !(inventory instanceof EndersortEntity)) {
                        System.out.println("Found Chest at " + pos.toShortString());
                        positions.add(pos);
                    }
                }
            }
        }
        //TODO - if detect an extender, expand radius, but don't look twice at same position
        this.containerPositions = new ArrayList<>(positions);
        System.out.println("Found " + this.containerPositions.size() + " containers");
    }

    private void distributeItems() {
        containerIndex++;
        if (containerIndex >= containerPositions.size()) {
            containerIndex = 0;
        }
        System.out.println("Distributing Items to " + containerIndex);
        IInventory chest = HopperTileEntity.getContainerAt(this.getLevel(), containerPositions.get(containerIndex));
        if (chest instanceof ChestTileEntity) {
            pushItems(chest);
        } else {
            clearContainers();
        }
    }

    private void pushItems(IInventory chest) {
        Set<ResourceLocation> matches = buildMatchMap(chest);
        for (ItemStack item : this.getItems()) {
            attemptToPush(item, chest, matches);
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

    private void attemptToPush(ItemStack item, IInventory destinationInventory, Set<ResourceLocation> matches) {
        ResourceLocation matchName = item.getItem().getRegistryName();
        if (!item.isEmpty() && matches.contains(matchName)) {
            for (int i = 0; i < destinationInventory.getContainerSize(); i++) {
                ItemStack destItem = destinationInventory.getItem(i);
                if (destItem.isEmpty() || (item.isStackable() && destItem.isStackable() && matchName.equals(destItem.getItem().getRegistryName()))) {
                    int itemCount = Math.min(destItem.getMaxStackSize() - destItem.getCount(), item.getCount());
                    if (itemCount > 0) {
                        if (destItem.isEmpty()) {
                            destinationInventory.setItem(i, item.copy());
                            destItem = destinationInventory.getItem(i);
                            destItem.setCount(itemCount);
                            setChanged();
                        } else {
                            destItem.setCount(destItem.getCount() + itemCount);
                        }

                        item.setCount(item.getCount() - itemCount);
                        if (destItem.isEmpty()) {
                            destinationInventory.setChanged();
                        }
                        break;
                    }
                }
            }
        }
    }


}
