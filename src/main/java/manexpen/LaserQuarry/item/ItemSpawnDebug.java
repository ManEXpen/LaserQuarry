package manexpen.LaserQuarry.item;

import manexpen.LaserQuarry.entity.EntitySquareLaser;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by ManEXpen on 2016/08/28.
 */
public class ItemSpawnDebug extends Item {
    public ItemSpawnDebug() {
        setMaxStackSize(1);
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        world.spawnEntityInWorld(new EntitySquareLaser(world, x, z, 0, 0));
        return true;
    }
}
