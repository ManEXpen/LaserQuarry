package manexpen.LaserQuarry;

import manexpen.LaserQuarry.block.BlockMachineLaserQuarry;
import manexpen.LaserQuarry.block.BlockReinforcedGlass;
import manexpen.LaserQuarry.item.ItemAreaSetter;
import manexpen.LaserQuarry.item.ItemSpawnDebug;
import manexpen.LaserQuarry.tileentity.TileLaserQuarry;

/**
 * Created by ManEXpen on 2016/07/15.
 */
public class LQItemBlockList {
    /*Block Fields*/
    public static BlockMachineLaserQuarry quarry;
    public static BlockReinforcedGlass reinforcedGlass;

    /*Items Fields*/
    public static ItemAreaSetter areaSetter;
    public static ItemSpawnDebug debugger;

    /*TileEntity Fields*/
    public static Class tileLaserQuarryClass = TileLaserQuarry.class;
}
