package manexpen.LaserQuarry.packet;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import manexpen.LaserQuarry.LaserQuarry;
import manexpen.LaserQuarry.packet.messages.LQMessageHandler;
import manexpen.LaserQuarry.packet.messages.LQSyncPacket;

/**
 * Created by ManEXpen on 2016/07/25.
 */
public class LQPacketHandler {
    public static SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(LaserQuarry.MODNAME);

    public static void init() {
        INSTANCE.registerMessage(LQMessageHandler.class, LQSyncPacket.class, 0, Side.CLIENT);
    }
}
