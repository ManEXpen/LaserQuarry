package manexpen.LaserQuarry.thread;

import manexpen.LaserQuarry.entity.LaserColor;
import manexpen.LaserQuarry.lib.BlockUtil;
import manexpen.LaserQuarry.lib.InvUtil;
import manexpen.LaserQuarry.lib.LogHelper;
import manexpen.LaserQuarry.tileentity.TileLaserQuarry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by ManEXpen on 2016/07/28.
 */
public class ThreadDigGround extends ThreadEditWorld {

    private TileLaserQuarry tileLaserQuarry;

    /*各種現在のブロックを指すためのパラメータ*/
    private int nowIterateX, nowIterateY, nowIterateZ, startX, startZ, endX, endZ = 0;

    private boolean stopFlag = false;

    public ThreadDigGround(TileLaserQuarry tile) {
        super(tile);
        this.tileLaserQuarry = tile;
    }

    public void stopDig() {
        this.stopFlag = true;
    }

    private void dig(final int x, final int y, final int z) {
        Block digBlock = worldObj.getBlock(x, y, z);
        if (digBlock instanceof BlockAir) return;

        int needEnergy = BlockUtil.getDigEnergy(worldObj, x, y, z);
        tileLaserQuarry.storage.modifyEnergyStored(-needEnergy);

        if (FluidRegistry.lookupFluidForBlock(digBlock) == null) {
            InvUtil.setInvItem(new ItemStack(digBlock, 1, worldObj.getBlockMetadata(x, y, z)), tileLaserQuarry);
        }

        worldObj.setBlock(x, y, z, Blocks.air);
    }

    private void doWork() {

        if (tileLaserQuarry.isActive() && tileLaserQuarry.posData != null) {
            LogHelper.info("はい");

            if (tileLaserQuarry.isSleep) initWork();
            tileLaserQuarry.isSleep = false;

            System.out.println("StartDig");
            //一旦行が終わったらまたstart~,end~に初期化するようにする
            for (; nowIterateY > 0; nowIterateY--) {
                for (; nowIterateX <= endX; nowIterateX++) {
                    for (; nowIterateZ <= endZ; nowIterateZ++) {
                        if (BlockUtil.getDigEnergy(worldObj, nowIterateX, nowIterateY, nowIterateZ) < tileLaserQuarry.storage.getEnergyStored()) {
                            dig(nowIterateX, nowIterateY, nowIterateZ);
                        } else {
                            return;
                        }
                    }

                    nowIterateZ = startZ;
                }
                nowIterateX = startX;
            }
            workFinished();
        } else if (!tileLaserQuarry.isActive() && tileLaserQuarry.posData != null) {
            if (tileLaserQuarry.storage.getEnergyStored() >= 100) {
                tileLaserQuarry.setActive(true);
                tileLaserQuarry.laserList.forEach(x -> x.changeTexture(LaserColor.RED));
            }
        }
    }

    private void initWork() {
        System.out.println(tileLaserQuarry.posData.toString());
        startX = Math.min(tileLaserQuarry.posData.x1(), tileLaserQuarry.posData.x2());
        startZ = Math.min(tileLaserQuarry.posData.z1(), tileLaserQuarry.posData.z2());
        nowIterateX = startX;
        nowIterateY = 256;
        nowIterateZ = startZ;
        endX = Math.max(tileLaserQuarry.posData.x1(), tileLaserQuarry.posData.x2());
        endZ = Math.max(tileLaserQuarry.posData.z1(), tileLaserQuarry.posData.z2());
    }

    private void workFinished() {
        tileLaserQuarry.posData = null;
        tileLaserQuarry.setActive(false);
        tileLaserQuarry.isSleep = true;
    }

    @Override
    public void run() {
        while (true) {
            super.run();
            if (worldObj != null) doWork();
            try {
                sleep(10);
            } catch (InterruptedException e) {
            }
        }
    }
}
