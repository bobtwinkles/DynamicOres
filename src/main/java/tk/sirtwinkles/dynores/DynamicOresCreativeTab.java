package tk.sirtwinkles.dynores;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class DynamicOresCreativeTab extends CreativeTabs {

    public DynamicOresCreativeTab() {
        super("Dynamic Ores");
    }

    @Override
    public Item getTabIconItem() {
        return (Item) Item.itemRegistry.getObject("minecraft:golden_apple");
    }

}
