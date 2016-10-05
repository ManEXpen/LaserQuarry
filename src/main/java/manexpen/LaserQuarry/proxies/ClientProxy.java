package manexpen.LaserQuarry.proxies;

import manexpen.LaserQuarry.LaserQuarry;
import manexpen.LaserQuarry.lib.LogHelper;

/**
 * Created by ManEXpen on 2016/07/11.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void register(LaserQuarry modLaserQuarry) {
        super.register(modLaserQuarry);
    }

    @Override
    public void networkRegister(LaserQuarry modLaserQuarry) {
        super.networkRegister(modLaserQuarry);
    }

    @Override
    public void renderRegister(LaserQuarry modLaserQuarry) {
        super.renderRegister(modLaserQuarry);
        LogHelper.info("Register Renderer");
    }
}
