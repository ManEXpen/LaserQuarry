package manexpen.LaserQuarry.tileentity;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import manexpen.LaserQuarry.api.IMultiable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by ManEXpen on 2016/07/16.
 */
public abstract class TileMachineBase extends TileEntity implements ISidedInventory, IMultiable, IEnergyHandler {
    public ItemStack[] itemStacks;
    protected int maxStackSize;
    protected int energy;
    protected int stackCount = 0;
    protected boolean canOutput = true;
    protected int[] AccessibleSlot;
    public boolean isActive = false;

    public EnergyStorage storage;

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
        storage.readFromNBT(tagCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
        storage.writeToNBT(tagCompound);
    }

    @Override
    public void updateEntity() {
    }

    /*IInventory and ISidedInventory*/

    @Override
    public int getSizeInventory() {
        if (canOutput)
            return maxStackSize;
        return 0;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return itemStacks[slot];
    }

    @Override
    public ItemStack decrStackSize(int slot, int number) {
        if (this.itemStacks[slot] != null) {
            ItemStack itemstack;

            if (this.itemStacks[slot].stackSize <= number) {
                itemstack = this.itemStacks[slot];
                stackCount -= itemstack.stackSize;
                this.itemStacks[slot] = null;
                this.markDirty();
                return itemstack;
            } else {
                stackCount -= number;
                itemstack = this.itemStacks[slot].splitStack(number);

                if (this.itemStacks[slot].stackSize == 0) {
                    this.itemStacks[slot] = null;
                }

                this.markDirty();
                return itemstack;
            }
        }
        return null;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.itemStacks[slot] != null) {
            ItemStack itemstack = this.itemStacks[slot];
            this.itemStacks[slot] = null;
            return itemstack;
        }
        return null;
    }


    @Override
    public boolean hasCustomInventoryName() {
        return true;
    }

    @Override
    public int getInventoryStackLimit() {
        if (canOutput)
            return 64;
        return 0;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer p_70300_1_) {
        return true;
    }

    @Override
    public void openInventory() {
    }

    @Override
    public void closeInventory() {
    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return canOutput;
    }


    /*RF制御*/

    @Override
    public int extractEnergy(ForgeDirection from, int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public boolean canConnectEnergy(ForgeDirection from) {
        return true;
    }

    @Override
    public int receiveEnergy(ForgeDirection from, int maxReceive, boolean simulate) {
        return storage.receiveEnergy(maxReceive, simulate);
    }

    /*null呼び出しでおｋ*/
    @Override
    public int getEnergyStored(ForgeDirection from) {
        return storage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(ForgeDirection from) {
        return storage.getMaxEnergyStored();
    }

    @Override
    public void setEnergy(int energy) {
        this.energy = energy;
    }
}
