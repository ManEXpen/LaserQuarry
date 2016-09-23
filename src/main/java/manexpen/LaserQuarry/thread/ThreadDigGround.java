package manexpen.LaserQuarry.thread;

import manexpen.LaserQuarry.entity.LaserColor;
import manexpen.LaserQuarry.lib.BlockUtil;
import manexpen.LaserQuarry.lib.InvUtil;
import manexpen.LaserQuarry.proxies.CommonProxy;
import manexpen.LaserQuarry.tileentity.TileLaserQuarry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by ManEXpen on 2016/07/28.
 */
public class ThreadDigGround extends ThreadEditWorld {

    private TileLaserQuarry tileLaserQuarry;
    private EntityPlayer fakePlayer;

    /*各種現在のブロックを指すためのパラメータ*/
    public volatile int nowIterateX, nowIterateY, nowIterateZ, startX, startZ, endX, endZ = 0;


    public ThreadDigGround(TileLaserQuarry tile) {
        super(tile);
        this.tileLaserQuarry = tile;
    }

    private void dig(final int x, final int y, final int z) {
        Block digBlock = worldObj.getBlock(x, y, z);
        int meta = worldObj.getBlockMetadata(x, y, z);
        if (digBlock instanceof BlockAir) return;

        int needEnergy = BlockUtil.getDigEnergy(worldObj, x, y, z);
        tileLaserQuarry.storage.modifyEnergyStored(-needEnergy);

        if (FluidRegistry.lookupFluidForBlock(digBlock) == null) {
            InvUtil.setInvItem(new ItemStack(digBlock, 1, meta), tileLaserQuarry);
        }

        //Block破壊音とか？
        fakePlayer();
        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(x, y, z, worldObj, digBlock, meta, fakePlayer);
        MinecraftForge.EVENT_BUS.post(event);
        worldObj.playAuxSFXAtEntity(null, 2001, x, y, z, Block.getIdFromBlock(digBlock) + (meta << 12));

        worldObj.setBlock(x, y, z, Blocks.air);
    }

    private void doWork() {

        if (tileLaserQuarry.isActive() && tileLaserQuarry.posData != null) {

            if (tileLaserQuarry.isSleep) initWork();
            tileLaserQuarry.isSleep = false;

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
                //これないと重いで
                try {
                    sleep(5);
                } catch (InterruptedException e) {
                }
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

    private void fakePlayer() {
        fakePlayer = FakePlayerFactory.get((WorldServer) worldObj, CommonProxy.GAME_PROFILE);
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
