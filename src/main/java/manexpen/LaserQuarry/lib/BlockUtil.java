package manexpen.LaserQuarry.lib;

import manexpen.LaserQuarry.init.Config;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.world.World;

/**
 * Created by ManEXpen on 2016/08/23.
 */
public class BlockUtil {
    public static int getDigEnergy(World world, int x, int y, int z) {
        Block checkBlock = world.getBlock(x, y, z);
        if (checkBlock instanceof BlockAir) return 0;
        float hardness = checkBlock.getBlockHardness(world, x, y, z);
        return (int) (hardness * Config.EnergyCoefficient);
    }
}
