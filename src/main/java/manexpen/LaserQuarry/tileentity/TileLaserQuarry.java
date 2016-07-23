package manexpen.LaserQuarry.tileentity;

import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by ManEXpen on 2016/07/17.
 */
public class TileLaserQuarry extends TileMachineBase {
    public boolean isActive = false;


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound) {
        super.readFromNBT(tagCompound);
    }

    @Override
    public void writeToNBT(NBTTagCompound tagCompound) {
        super.writeToNBT(tagCompound);
    }


    @Override
    public String getInventoryName() {
        return "LaserQuarry";
    }
}
