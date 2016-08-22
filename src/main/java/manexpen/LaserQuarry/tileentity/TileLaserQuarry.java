package manexpen.LaserQuarry.tileentity;

import cofh.api.energy.EnergyStorage;
import manexpen.LaserQuarry.api.PosData2Dim;
import manexpen.LaserQuarry.entity.EntityRedLine;
import manexpen.LaserQuarry.entity.LaserColor;
import manexpen.LaserQuarry.packet.LQPacketHandler;
import manexpen.LaserQuarry.packet.messages.LQSyncPacket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;

/**
 * Created by ManEXpen on 2016/07/17.
 */
public class TileLaserQuarry extends TileMachineBase {
    private ArrayList<EntityRedLine> laserList = new ArrayList<>();
    private PosData2Dim posData;

    private int nowIterateX, nowIterateY, nowIterateZ, startX, startZ, endX, endZ;


    public TileLaserQuarry() {
        this.maxStackSize = 40000;
        this.itemStacks = new ItemStack[maxStackSize];
        this.storage = new EnergyStorage(900000);
        this.AccessibleSlot = new int[]{0, 1, 2, 3, 4, 5};
    }

    public void setLaser(EntityRedLine laser) {
        laserList.add(laser);
    }

    public void setPosData(PosData2Dim posData) {
        this.posData = posData;
    }

    /*レーザーの削除とか
    * 右クリック時に呼ばれる*/
    public void clearData() {
        if (laserList != null) laserList.forEach(EntityRedLine::setDead);
        laserList = null;
        laserList = new ArrayList<>();
    }

    private void doWork() {
        if (isActive() && posData != null) {
            initWork();

            for (; nowIterateY > 0; nowIterateY--) {
                for (; nowIterateX <= endX; nowIterateX++) {
                    for (; nowIterateZ <= endZ; ) {

                        //TODO: こ↑こ↓にブロック消去及びエネルギー操作を行う。スタック管理は別メソッドに切り出せ
                        
                        nowIterateZ++;
                    }
                }
            }

//            for (nowIterateY = 256; nowIterateY > 0; nowIterateY--) {
//                for (nowIterateX = startX; nowIterateX <= Math.max(posData.x1(), posData.x2()); nowIterateX++) {
//                    for (nowIterateZ = startZ; nowIterateZ <= Math.max(posData.z1(), posData.z2()); nowIterateZ++) {
//                        if (storage.getEnergyStored() >)
//                            worldObj.setBlockToAir(nowIterateX, nowIterateY, nowIterateZ);
//                    }
//                }
//            }
            workFinished();
        } else if (!isActive() && posData != null) {
            if (storage.getEnergyStored() >= 100) {
                setActive(true);
                laserList.forEach(x -> x.changeTexture(LaserColor.RED));
            }
        }

    }

    private void initWork() {
        startX = Math.min(posData.x1(), posData.x2());
        startZ = Math.min(posData.z1(), posData.z2());
        nowIterateX = startX;
        nowIterateY = 256;
        nowIterateZ = startZ;
        endX = Math.max(posData.x1(), posData.x2());
        endZ = Math.max(posData.z1(), posData.z2());
    }


    private void workFinished() {
        System.out.println('A');
        posData = null;
        setActive(false);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) {
            doWork();
            LQPacketHandler.INSTANCE.sendToAll(new LQSyncPacket(xCoord, yCoord, zCoord, stackCount, getEnergyStored(null), isActive()));
        }

    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

        storage.readFromNBT(tagCompound);

        this.stackCount = tagCompound.getInteger("StackCount");

        //ここから貯蔵スタック読み込み
        ItemStack[] stacks = new ItemStack[maxStackSize];
        NBTTagList nbttaglist = tagCompound.getTagList("Stacks", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbtTagCompound = nbttaglist.getCompoundTagAt(i);
            byte b = nbtTagCompound.getByte("Slot");

            if (b >= 0 && b < maxStackSize) {
                stacks[b] = ItemStack.loadItemStackFromNBT(nbtTagCompound);
            }
        }
        this.itemStacks = stacks;
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);

        storage.writeToNBT(tagCompound);

        tagCompound.setInteger("StackCount", this.stackCount);

        NBTTagList nbtTagList = new NBTTagList();
        for (int i = 0; i < itemStacks.length; i++) {
            if (itemStacks[i] == null) continue;
            NBTTagCompound nbttagcompound = new NBTTagCompound();
            nbttagcompound.setByte("Slot", (byte) i);
            this.itemStacks[i].writeToNBT(nbttagcompound);
            nbtTagList.appendTag(nbttagcompound);
        }
        tagCompound.setTag("Stacks", nbtTagList);
    }


    @Override
    public String getInventoryName() {
        return "LaserQuarry";
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        //EnderIO先輩これないと落ちるゾ
        if (itemStack == null) return;

        this.itemStacks[slot] = itemStack;
        this.stackCount += itemStack.stackSize;

        if (itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }


    @Override
    public int[] getAccessibleSlotsFromSide(int p_94128_1_) {
        return this.AccessibleSlot;
    }

    @Override
    public boolean canInsertItem(int p_102007_1_, ItemStack p_102007_2_, int p_102007_3_) {
        return true;
    }

    @Override
    public boolean canExtractItem(int p_102008_1_, ItemStack p_102008_2_, int p_102008_3_) {
        return true;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
        this.markDirty();
    }

    /*GUI&Packet関係*/
    @Override
    public int getStackSize() {
        return this.stackCount;
    }

    @Override
    public void setDispStackSize(int stackSize) {
        this.stackCount = stackSize;
    }

    @Override
    public double getProgress() {
        return calcProgress(0);
    }

    @Override
    public double calcProgress(double progress) {
        return 0;
    }

}
