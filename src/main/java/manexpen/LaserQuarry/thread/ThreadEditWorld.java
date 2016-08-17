package manexpen.LaserQuarry.thread;

import net.minecraft.world.World;

/**
 * Created by ManEXpen on 2016/07/28.
 */
public abstract class ThreadEditWorld extends Thread {
    protected World worldObj;
    protected int xCoord, yCoord, zCoord;

    public ThreadEditWorld(int xCoord, int yCoord, int zCoord, World worldObj) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.zCoord = zCoord;
        this.worldObj = worldObj;
    }

    @Override
    public void run() {
    }
}
