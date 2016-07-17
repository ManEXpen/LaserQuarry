package manexpen.LaserQuarry;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.ModMetadata;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import manexpen.LaserQuarry.init.Config;
import manexpen.LaserQuarry.lib.LogHelper;
import manexpen.LaserQuarry.proxies.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.config.Configuration;

/**
 * Created by ManEXpen on 2016/07/11.
 */
@Mod(modid = "LaserQuarry", name = "LaserQuarry", useMetadata = true)
public class LaserQuarry {

    @Mod.Instance("LaserQuarry")
    public static LaserQuarry instance;

    @SidedProxy(serverSide = "manexpen.LaserQuarry.proxies.CommonProxy", clientSide = "manexpen.LaserQuarry.proxies.ClientProxy")
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        LogHelper.Logger = e.getModLog();
        LogHelper.info("PreInitializing...");
        Config.LoadCfg(new Configuration(e.getSuggestedConfigurationFile()));
        setModMetaData(e.getModMetadata());
        LogHelper.info("PreInitializing Finished!");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        LogHelper.info("Initializing...");
        proxy.register();
        LogHelper.info("Initializing Finished...");
    }

    private void setModMetaData(ModMetadata modMetadata) {
        modMetadata.version = "A-0.0.1";
    }

    public static final CreativeTabs LQCreativeTab = new CreativeTabs("laser") {
        @Override
        public Item getTabIconItem() {
            return Item.getItemFromBlock(LQItemBlockList.quarry);
        }
    };
}
