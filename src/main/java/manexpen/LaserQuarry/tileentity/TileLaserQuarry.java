package manexpen.LaserQuarry.tileentity;

import cofh.api.energy.EnergyStorage;
import manexpen.LaserQuarry.LaserQuarry;
import manexpen.LaserQuarry.api.PosData2Dim;
import manexpen.LaserQuarry.entity.EntityRedLine;
import manexpen.LaserQuarry.lib.LogHelper;
import manexpen.LaserQuarry.packet.LQPacketHandler;
import manexpen.LaserQuarry.packet.messages.LQSyncPacket;
import manexpen.LaserQuarry.thread.ThreadDigGround;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;

/**
 * Created by ManEXpen on 2016/07/17.
 */
public class TileLaserQuarry extends TileMachineBase {
    public ArrayList<EntityRedLine> laserList = new ArrayList<>();
    private ThreadDigGround digger;
    private boolean triedToAssignTicket = false;
    public PosData2Dim posData;
    public ForgeChunkManager.Ticket ticket;

    /*起動していないことを確かめるためのフラグ*/
    public boolean isSleep = true;

    /*各種現在のブロックを指すためのパラメータ*/
    private int nowIterateX, nowIterateY, nowIterateZ, startX, startZ, endX, endZ = 0;

    public TileLaserQuarry() {
        this.maxStackSize = 40000;
        this.itemStacks = new ItemStack[maxStackSize];
        this.storage = new EnergyStorage(9000_0000);
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
        startX = endX = startZ = endZ = nowIterateX = nowIterateY = nowIterateZ = 0;
        setActive(false);
        isSleep = true;
        if (digger != null) digger.stop();
        digger = null;
        if (this.ticket != null) ForgeChunkManager.releaseTicket(this.ticket);
    }

    public void initThread() {
        digger = new ThreadDigGround(this);
        digger.setDaemon(true);
        digger.start();
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) {
            if (!triedToAssignTicket && !isSleep) {
                triedToAssignTicket = true;
                if (assignTicket()) {
                    ticket.getModData().setInteger("machineX", xCoord);
                    ticket.getModData().setInteger("machineY", yCoord);
                    ticket.getModData().setInteger("machineZ", zCoord);
                    ForgeChunkManager.forceChunk(ticket, new ChunkCoordIntPair(xCoord >> 4, zCoord >> 4));
                    for (int chunkX = digger.startX >> 4; chunkX <= digger.endX >> 4; chunkX++) {
                        for (int chunkZ = digger.startZ >> 4; chunkZ <= digger.endZ >> 4; chunkZ++) {
                            ChunkCoordIntPair chunk = new ChunkCoordIntPair(chunkX, chunkZ);
                            ForgeChunkManager.forceChunk(this.ticket, chunk);
                        }
                    }
                } else LogHelper.warn("Couldn't be reserved for TileLaserQuarry");
            }
            LQPacketHandler.INSTANCE.sendToAll(new LQSyncPacket(xCoord, yCoord, zCoord, stackCount, getEnergyStored(null), isActive()));
        }

    }

    private boolean assignTicket() {
        if (ticket == null)
            ticket = ForgeChunkManager.requestTicket(LaserQuarry.instance, worldObj, ForgeChunkManager.Type.NORMAL);
        return ticket != null;
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
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        return itemStacks[0] != null;
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
