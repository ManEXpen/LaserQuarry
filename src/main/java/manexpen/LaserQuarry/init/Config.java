package manexpen.LaserQuarry.init;

import manexpen.LaserQuarry.lib.LogHelper;
import net.minecraftforge.common.config.Configuration;

/**
 * Created by ManEXpen on 2016/07/11.
 */
public class Config {
    public static float EnergyCoefficient = 500;

    public static void LoadCfg(Configuration cfg) {
        LogHelper.info("Config Load...");

        try {
            cfg.load();
            EnergyCoefficient = cfg.getFloat("EnergyCoefficient", "EnergySettings", 500, 10000, 0.1F, "ブロックの硬さ*これ=エネルギー");
        } catch (Exception e) {
            LogHelper.warn("Can't Read Config File. Default Value will be used.");
        } finally {
            cfg.save();
        }
    }
}
