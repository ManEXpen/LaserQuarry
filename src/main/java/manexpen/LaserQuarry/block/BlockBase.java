package manexpen.LaserQuarry.block;

import manexpen.LaserQuarry.api.IHasRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Created by ManEXpen on 2016/07/14.
 */
public abstract class BlockBase extends Block implements IHasRecipe {
    public BlockBase() {
        super(Material.iron);
    }

    @Override
    public abstract TileEntity createNewTileEntity(World w, int metadata);

    @Override
    public abstract IIcon getIcon(int x, int y);

    @Override
    public abstract IIcon getIcon(IBlockAccess w, int x, int y, int z, int side);
}
