package manexpen.LaserQuarry.tileentity;

import cofh.api.energy.EnergyStorage;
import manexpen.LaserQuarry.packet.LQPacketHandler;
import manexpen.LaserQuarry.packet.messages.LQSyncPacket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

/**
 * Created by ManEXpen on 2016/07/17.
 */
public class TileLaserQuarry extends TileMachineBase {


    public TileLaserQuarry() {
        this.maxStackSize = 40000;
        this.itemStacks = new ItemStack[maxStackSize];
        this.storage = new EnergyStorage(900000);
        this.AccessibleSlot = new int[]{0, 1, 2, 3, 4, 5};
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!worldObj.isRemote) {
            LQPacketHandler.INSTANCE.sendToAll(new LQSyncPacket(xCoord, yCoord, zCoord, stackCount, getEnergyStored(null), isActive()));
        }

    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);

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
        this.itemStacks[slot] = itemStack;
        this.stackCount += itemStack.stackSize;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
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
