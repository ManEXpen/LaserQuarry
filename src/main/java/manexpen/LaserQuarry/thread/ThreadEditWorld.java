package manexpen.LaserQuarry.thread;

import manexpen.LaserQuarry.tileentity.TileLaserQuarry;
import manexpen.LaserQuarry.tileentity.TileMachineBase;
import net.minecraft.world.World;

/**
 * Created by ManEXpen on 2016/07/28.
 */
public abstract class ThreadEditWorld extends Thread {
    protected World worldObj;
    protected int xCoord, yCoord, zCoord;
    private TileMachineBase tile;

    public ThreadEditWorld(TileLaserQuarry tile) {
        this.xCoord = tile.xCoord;
        this.yCoord = tile.yCoord;
        this.zCoord = tile.zCoord;
        this.tile = tile;
    }

    @Override
    public void run() {
        this.worldObj = tile.getWorldObj();
    }
}
