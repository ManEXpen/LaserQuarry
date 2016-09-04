package manexpen.LaserQuarry.gui;

import cpw.mods.fml.common.network.IGuiHandler;
import manexpen.LaserQuarry.gui.client.GuiLaserQuarry;
import manexpen.LaserQuarry.gui.container.LaserQuarryContainer;
import manexpen.LaserQuarry.lib.GuiRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by ManEXpen on 2016/07/23.
 */
public class GuiHandler implements IGuiHandler {
    public static final String LASERQUARRY_GUI = "LaserQuarry-gui";

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GuiRegistry.getGuiId(LASERQUARRY_GUI))
            return new LaserQuarryContainer(x, y, z, player, world);
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GuiRegistry.getGuiId(LASERQUARRY_GUI))
            return new GuiLaserQuarry(x, y, z, player, world);

        return null;
    }
}
