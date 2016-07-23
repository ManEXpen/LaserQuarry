package manexpen.LaserQuarry.gui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

/**
 * Created by ManEXpen on 2016/07/23.
 */
public class LaserQuarryContainer extends Container {
    int x, y, z;

    public LaserQuarryContainer(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }
}
