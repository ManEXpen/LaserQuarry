package manexpen.LaserQuarry.proxies;

import cpw.mods.fml.common.network.NetworkRegistry;
import manexpen.LaserQuarry.LaserQuarry;
import manexpen.LaserQuarry.gui.GuiHandler;
import manexpen.LaserQuarry.lib.GuiRegistry;

/**
 * Created by ManEXpen on 2016/07/11.
 */
public class CommonProxy {
    public void register() {
        UniversalModInitializer.register();
    }

    public void networkRegister(LaserQuarry modLaserQuarry) {
        NetworkRegistry.INSTANCE.registerGuiHandler(modLaserQuarry, new GuiHandler());
    }

    public void renderRegister(LaserQuarry modLaserQuarry) {
        GuiRegistry.registerId(GuiHandler.LASERQUARRY_GUI, GuiRegistry.getNextGuiId());
    }
}
