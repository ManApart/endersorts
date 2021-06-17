package org.manapart.item_filters;

import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import static org.manapart.item_filters.Endersort.*;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModItems {

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        if (!ForgeRegistries.ITEMS.containsKey(ENDERSORT_ITEM.getRegistryName())) {
            ForgeRegistries.ITEMS.register(ENDERSORT_ITEM);
            ForgeRegistries.ITEMS.register(ENDER_EXTENDER_ITEM);
            ForgeRegistries.ITEMS.register(endersortIcon);
        }
    }
}
