package manexpen.LaserQuarry.tileentity;

import manexpen.LaserQuarry.api.IGuiConnector;
import manexpen.LaserQuarry.packet.LQPacketHandler;
import manexpen.LaserQuarry.packet.messages.LQSyncPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by ManEXpen on 2016/07/16.
 */
public abstract class TileMachineBase extends TileEntity implements IInventory, IGuiConnector {
    public ItemStack[] itemStacks;
    protected int maxStackSize;
    protected int energy;
    protected int stackCount = 0;
    protected boolean canOutput = true;
    public boolean isActive = false;

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            LQPacketHandler.INSTANCE.sendToAll(new LQSyncPacket(xCoord, yCoord, zCoord, stackCount, energy, isActive));
        }
    }

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
                //stackCount -= itemStacks[slot].stackSize;
                itemstack = this.itemStacks[slot];
                this.itemStacks[slot] = null;
                this.markDirty();
                return itemstack;
            } else {
                //stackCount -= number;
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

}
