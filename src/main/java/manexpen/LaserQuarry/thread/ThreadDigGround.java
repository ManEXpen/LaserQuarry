package manexpen.LaserQuarry.thread;

import net.minecraft.world.World;

/**
 * Created by ManEXpen on 2016/07/28.
 */
public class ThreadDigGround extends ThreadEditWorld {

    protected int startPosX, startPosZ;

    public ThreadDigGround(int startPosX, int startPosZ, int xCoord, int yCoord, int zCoord, World worldObj) {
        super(xCoord, yCoord, zCoord, worldObj);
    }

    private void dig() {

    }
}
