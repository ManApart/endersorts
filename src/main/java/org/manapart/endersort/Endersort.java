package org.manapart.endersort;

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
        EndersortBlock block = new EndersortBlock();
        block.setRegistryName(new ResourceLocation(MODID + ":endersort_block"));
        return block;
    }

    private static EndersortItem createItem(EndersortBlock block) {
        EndersortItem item = new EndersortItem(block);
        item.setRegistryName(new ResourceLocation(MODID + ":endersort_item"));
        return item;
    }

    private static EnderExtenderBlock createExtenderBlock() {
        EnderExtenderBlock block = new EnderExtenderBlock();
        block.setRegistryName(new ResourceLocation(MODID + ":ender_extender_block"));
        return block;
    }

    private static EnderExtenderItem createExtenderItem(EnderExtenderBlock block) {
        EnderExtenderItem item = new EnderExtenderItem(block);
        item.setRegistryName(new ResourceLocation(MODID + ":ender_extender_item"));
        return item;
    }

    private static TileEntityType<EndersortEntity> createEntityType(EndersortBlock block) {
        TileEntityType.Builder<EndersortEntity> builder = TileEntityType.Builder.of(EndersortEntity::new, ENDERSORT_BLOCK);
        TileEntityType<EndersortEntity> tileType = builder.build(null);
        tileType.setRegistryName(MODID, "endersort");
        return tileType;
    }

}
