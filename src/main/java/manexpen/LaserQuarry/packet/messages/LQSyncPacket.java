package manexpen.LaserQuarry.packet.messages;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by ManEXpen on 2016/07/25.
 */
public class LQSyncPacket implements IMessage {

    private int x, y, z;
    private int displayStackSize, energy;
    private boolean isActive;

    public LQSyncPacket() {
    }

    public LQSyncPacket(int x, int y, int z, int displayStackSize, int energy, boolean isActive) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.displayStackSize = displayStackSize;
        this.energy = energy;
        this.isActive = isActive;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.x = buf.readInt();
        this.y = buf.readInt();
        this.z = buf.readInt();
        this.displayStackSize = buf.readInt();
        this.energy = buf.readInt();
        this.isActive = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeInt(this.displayStackSize);
        buf.writeInt(this.energy);
        buf.writeBoolean(this.isActive);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getDisplayStackSize() {
        return displayStackSize;
    }

    public int getEnergy() {
        return energy;
    }

    public boolean getIsActive() {
        return isActive;
    }
}
