package manexpen.LaserQuarry.gui.container;

import manexpen.LaserQuarry.tileentity.TileLaserQuarry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by ManEXpen on 2016/07/23.
 */
public class LaserQuarryContainer extends Container {
    int x, y, z;
    private TileLaserQuarry tileLaserQuarry;

    public LaserQuarryContainer(int x, int y, int z, EntityPlayer player, World world) {
        this.x = x;
        this.y = y;
        this.z = z;
        tileLaserQuarry = (TileLaserQuarry) world.getTileEntity(x, y, z);
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }

    @Override
    public Slot getSlot(int p_75139_1_) {
        return null;
    }

    @Override
    public void putStackInSlot(int p_75141_1_, ItemStack p_75141_2_) {
    }
}
