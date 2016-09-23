package manexpen.LaserQuarry.lib.chunkManager;

import com.google.common.collect.Lists;
import manexpen.LaserQuarry.LQItemBlockList;
import manexpen.LaserQuarry.tileentity.TileLaserQuarry;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.List;

/**
 * Created by manex on 2016/09/23.
 */
public class ChunkLoadingCallback implements ForgeChunkManager.OrderedLoadingCallback {
    @Override
    public List<ForgeChunkManager.Ticket> ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world, int maxTicketCount) {
        List<ForgeChunkManager.Ticket> validTickets = Lists.newArrayList();
        for (ForgeChunkManager.Ticket ticket : tickets) {
            int xCoord = ticket.getModData().getInteger("machineX");
            int yCoord = ticket.getModData().getInteger("machineY");
            int zCoord = ticket.getModData().getInteger("machineZ");

            if (world.getBlock(xCoord, yCoord, zCoord) == LQItemBlockList.quarry) validTickets.add(ticket);
        }
        return validTickets;
    }

    @Override
    public void ticketsLoaded(List<ForgeChunkManager.Ticket> tickets, World world) {
        for (ForgeChunkManager.Ticket ticket : tickets) {
            int xCoord = ticket.getModData().getInteger("machineX");
            int yCoord = ticket.getModData().getInteger("machineY");
            int zCoord = ticket.getModData().getInteger("machineZ");

            TileLaserQuarry tq = (TileLaserQuarry) world.getTileEntity(xCoord, yCoord, zCoord);
            tq.forceChunkLoading(ticket);
        }
    }
}
