package manexpen.LaserQuarry.block;

import manexpen.LaserQuarry.lib.DirectionHandler;
import manexpen.LaserQuarry.tileentity.TileLaserQuarry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by ManEXpen on 2016/07/14.
 */
public class BlockMachineLaserQuarry extends BlockMachineBase {

    private IIcon active, top, side, front, bottom;
    private int renderPass;

    public BlockMachineLaserQuarry() {
    }

    @Override
    public void registerBlockIcons(IIconRegister iconRegister) {
        top = iconRegister.registerIcon("laser:machine/MachineTop");
        side = iconRegister.registerIcon("laser:machine/MachineSide");
        front = iconRegister.registerIcon("laser:machine/MachineChunkQuarry");
        bottom = iconRegister.registerIcon("laser:machine/MachineBottom");
        active = iconRegister.registerIcon("laser:machine/MachineChunkQuarry_Active");
    }


    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int var6, float var7, float var8, float var9) {
        super.onBlockActivated(world, x, y, z, player, var6, var7, var8, var9);
        return false;
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tabs, List list) {

    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, entity, stack);

        DirectionHandler.setMetadataByDir(world, x, y, z, entity);
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int var6) {
        super.breakBlock(world, x, y, z, block, var6);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileLaserQuarry();
    }


    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == 0)
            return bottom;
        else if (side == 1)
            return top;
        else if ((side == 3 && meta == 0) || meta == side)
            return active;
        else
            return this.side;
    }

/*    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileLaserQuarry tile = (TileLaserQuarry) world.getTileEntity(x, y, z);

    }*/

    @Override
    public boolean canRenderInPass(int pass) {
        System.out.println(pass);
        renderPass = pass;
        return pass > 1;
    }


    @Override
    public void registerRecipe() {
    }

}
