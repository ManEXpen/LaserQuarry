package manexpen.LaserQuarry.packet.messages.Handler;

import com.google.common.base.Preconditions;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import manexpen.LaserQuarry.packet.messages.LQSyncPacket;
import manexpen.LaserQuarry.tileentity.TileLaserQuarry;
import net.minecraft.client.Minecraft;

/**
 * Created by ManEXpen on 2016/07/25.
 */
public class LQMessageHandler implements IMessageHandler<LQSyncPacket, IMessage> {
    @Override
    public IMessage onMessage(LQSyncPacket pkt, MessageContext ctx) {
        try {
            TileLaserQuarry tileLaserQuarry = (TileLaserQuarry) Minecraft.getMinecraft().theWorld.getTileEntity(pkt.getX(), pkt.getY(), pkt.getZ());
            Preconditions.checkNotNull(tileLaserQuarry, "Packet Error while Sending/Recieving");
            tileLaserQuarry.setActive(pkt.getIsActive());
            tileLaserQuarry.setDispStackSize(pkt.getDisplayStackSize());
            tileLaserQuarry.setEnergy(pkt.getEnergy());
        } catch (NullPointerException e) {
        }
        return null;
    }
}
