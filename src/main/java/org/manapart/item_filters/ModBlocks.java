package org.manapart.item_filters;

import net.minecraft.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import static org.manapart.item_filters.Endersort.ENDERSORT_BLOCK;
import static org.manapart.item_filters.Endersort.ENDER_EXTENDER_BLOCK;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        if (!ForgeRegistries.BLOCKS.containsKey(ENDERSORT_BLOCK.getRegistryName())) {
            ForgeRegistries.BLOCKS.register(ENDERSORT_BLOCK);
            ForgeRegistries.BLOCKS.register(ENDER_EXTENDER_BLOCK);
        }
    }
}
