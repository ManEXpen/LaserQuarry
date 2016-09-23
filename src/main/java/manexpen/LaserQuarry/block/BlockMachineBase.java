package manexpen.LaserQuarry.block;

import manexpen.LaserQuarry.api.IHasRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by ManEXpen on 2016/07/14.
 */
public abstract class BlockMachineBase extends BlockContainer implements IHasRecipe {

    protected BlockMachineBase() {
        super(Material.iron);
    }

    @Override
    public abstract void registerBlockIcons(IIconRegister iconRegister);


    @Override
    public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
        super.getSubBlocks(item, tabs, list);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, entity, stack);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int var6) {
        super.breakBlock(world, x, y, z, block, var6);
    }

    @Override
    public abstract TileEntity createNewTileEntity(World world, int metadata);


    @Override
    public abstract IIcon getIcon(int x, int y);

}
