package manexpen.LaserQuarry.tileentity;

import manexpen.LaserQuarry.lib.IconRegistry;
import net.minecraft.util.IIcon;

/**
 * Created by ManEXpen on 2016/07/17.
 */
public class TileLaserQuarry extends TileMachineBase {
    public boolean isActive = false;

    @Override
    public IIcon getTexture(int i, int i1) {
        System.out.println(i + " : " + i1);
        return IconRegistry.getIcon("front");
    }
}
