package manexpen.LaserQuarry.proxies;

import cpw.mods.fml.client.registry.RenderingRegistry;
import manexpen.LaserQuarry.LaserQuarry;
import manexpen.LaserQuarry.entity.EntityRedLine;
import manexpen.LaserQuarry.lib.LogHelper;
import manexpen.LaserQuarry.render.RenderRedLine;

/**
 * Created by ManEXpen on 2016/07/11.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public void register() {
        super.register();
    }

    @Override
    public void networkRegister(LaserQuarry modLaserQuarry) {
        super.networkRegister(modLaserQuarry);
    }

    @Override
    public void renderRegister(LaserQuarry modLaserQuarry) {
        super.renderRegister(modLaserQuarry);
        RenderingRegistry.registerEntityRenderingHandler(EntityRedLine.class, new RenderRedLine());
        LogHelper.info("Register Renderer");
    }
}
