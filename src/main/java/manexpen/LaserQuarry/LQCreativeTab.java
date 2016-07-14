package manexpen.LaserQuarry;

import manexpen.LaserQuarry.block.BlockLaserQuarry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

/**
 * Created by ManEXpen on 2016/07/15.
 */
public class LQCreativeTab extends CreativeTabs {
    public LQCreativeTab() {
        super("LaserQuarry");
    }

    @Override
    public Item getTabIconItem() {
        return Item.getItemFromBlock(LQItemBlockList.quarry);
    }
}
