package manexpen.LaserQuarry.item;

import cofh.api.energy.IEnergyContainerItem;
import com.google.common.base.Preconditions;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by ManEXpen on 2016/07/30.
 */
public abstract class ItemToolBase extends Item implements IEnergyContainerItem {
    public ItemToolBase() {
        setNoRepair();
    }

    protected void setEnergyToNBT(ItemStack itemStack, int energy) {
        Preconditions.checkNotNull(itemStack, "Something happened");
        itemStack.stackTagCompound.setInteger("Energy", energy);
    }
}
