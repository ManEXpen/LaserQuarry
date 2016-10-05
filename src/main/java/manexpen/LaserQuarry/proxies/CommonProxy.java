package manexpen.LaserQuarry.proxies;

import com.mojang.authlib.GameProfile;
import cpw.mods.fml.common.network.NetworkRegistry;
import manexpen.LaserQuarry.LQItemBlockList;
import manexpen.LaserQuarry.LaserQuarry;
import manexpen.LaserQuarry.api.UniversalModInitializer;
import manexpen.LaserQuarry.gui.GuiHandler;
import manexpen.LaserQuarry.lib.GuiRegistry;
import manexpen.LaserQuarry.lib.LogHelper;
import manexpen.LaserQuarry.packet.LQPacketHandler;
import net.minecraftforge.common.ForgeChunkManager;

import java.util.UUID;

/**
 * Created by ManEXpen on 2016/07/11.
 */
public class CommonProxy {
    public static final GameProfile GAME_PROFILE = new GameProfile(UUID.nameUUIDFromBytes("LaserQuarry".getBytes()), "[LaserQuarry]");

    public void register(LaserQuarry modLaserQuarry) {
        LogHelper.info("LaserQuarry's fake player: UUID = " + GAME_PROFILE.getId().toString() + ", name = '" + GAME_PROFILE.getName() + "'!");
        UniversalModInitializer.regist(LQItemBlockList.class, modLaserQuarry);
        ForgeChunkManager.setForcedChunkLoadingCallback(LaserQuarry.instance, null);
    }

    public void networkRegister(LaserQuarry modLaserQuarry) {
        LQPacketHandler.init();
        NetworkRegistry.INSTANCE.registerGuiHandler(modLaserQuarry, new GuiHandler());
    }

    public void renderRegister(LaserQuarry modLaserQuarry) {
        GuiRegistry.registerId(GuiHandler.LASERQUARRY_GUI, GuiRegistry.getNextGuiId());
    }

}
