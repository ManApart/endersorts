package org.manapart.item_filters;

import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(Endersort.MODID)
@Mod.EventBusSubscriber(modid = Endersort.MODID)
public class Endersort {

    public static final String MODID = "endersort";
    public static final EndersortBlock ENDERSORT_BLOCK = createBlock();
    public static final EndersortItem ENDERSORT_ITEM = createItem(ENDERSORT_BLOCK);
    public static final EnderExtenderBlock ENDER_EXTENDER_BLOCK = createExtenderBlock();
    public static final EnderExtenderItem ENDER_EXTENDER_ITEM = createExtenderItem(ENDER_EXTENDER_BLOCK);
    public static final TileEntityType<EndersortEntity> tileType = createEntityType(ENDERSORT_BLOCK);
    public static Item endersortIcon = createIcon();

    public Endersort() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    private static Item createIcon() {
        Item icon = new Item(new Item.Properties());
        icon.setRegistryName(MODID + ":es_icon");
        return icon;
    }

    private static EndersortBlock createBlock() {
        EndersortBlock filter = new EndersortBlock();
        filter.setRegistryName(new ResourceLocation(MODID + ":endersort_block"));
        return filter;
    }

    private static EndersortItem createItem(EndersortBlock block) {
        EndersortItem filter = new EndersortItem(block);
        filter.setRegistryName(new ResourceLocation(MODID + ":endersort_item"));
        return filter;
    }

    private static EnderExtenderBlock createExtenderBlock() {
        EnderExtenderBlock filter = new EnderExtenderBlock();
        filter.setRegistryName(new ResourceLocation(MODID + ":ender_extender_block"));
        return filter;
    }

    private static EnderExtenderItem createExtenderItem(EnderExtenderBlock block) {
        EnderExtenderItem filter = new EnderExtenderItem(block);
        filter.setRegistryName(new ResourceLocation(MODID + ":ender_extender_item"));
        return filter;
    }

    private static TileEntityType<EndersortEntity> createEntityType(EndersortBlock block) {
        TileEntityType.Builder<EndersortEntity> builder = TileEntityType.Builder.of(EndersortEntity::new, ENDERSORT_BLOCK);
        TileEntityType<EndersortEntity> tileType = builder.build(null);
        tileType.setRegistryName(MODID, "endersort");
        return tileType;
    }

}
