package manexpen.LaserQuarry.lib;

import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

/**
 * Created by ManEXpen on 2016/07/17.
 */
public class GetInstance {
    public static World getTheWorld() {
        return Minecraft.getMinecraft().theWorld;
    }
}
