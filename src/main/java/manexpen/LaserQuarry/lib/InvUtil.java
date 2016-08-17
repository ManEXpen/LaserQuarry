package manexpen.LaserQuarry.lib;

import manexpen.LaserQuarry.tileentity.TileMachineBase;
import net.minecraft.item.ItemStack;

/**
 * Created by ManEXpen on 2016/07/25.
 */
public class InvUtil {

    public static void setInvItem(final ItemStack addItem, TileMachineBase tile) {

        for (int i = 0; i < tile.itemStacks.length; i++) {
            ItemStack stack = tile.itemStacks[i];


            if (stack != null && stack.getItem().equals(addItem.getItem())) {
                if (64 < tile.itemStacks[i].stackSize) continue;
                else {
                    tile.itemStacks[i].stackSize++;
                    tile.markDirty();
                    return;
                }
            } else if (stack == null) {
                tile.itemStacks[i] = addItem;
                tile.markDirty();
                return;
            }
        }
    }
}
