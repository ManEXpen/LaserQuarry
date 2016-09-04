package manexpen.LaserQuarry.block;

import manexpen.LaserQuarry.LaserQuarry;
import manexpen.LaserQuarry.api.PosData2Dim;
import manexpen.LaserQuarry.api.TileMachineRegistry;
import manexpen.LaserQuarry.gui.GuiHandler;
import manexpen.LaserQuarry.item.ItemAreaSetter;
import manexpen.LaserQuarry.lib.DirectionHandler;
import manexpen.LaserQuarry.lib.GuiRegistry;
import manexpen.LaserQuarry.tileentity.TileLaserQuarry;
import manexpen.LaserQuarry.tileentity.TileMachineBase;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by ManEXpen on 2016/07/14.
 */
public class BlockMachineLaserQuarry extends BlockMachineBase {

    private IIcon active, top, side, front, bottom;

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

        TileEntity tmp = world.getTileEntity(x, y, z);
        ItemStack b = player.getCurrentEquippedItem();

        if (!world.isRemote && tmp != null && b != null) {

            if (b.getItem() instanceof ItemAreaSetter && tmp instanceof TileLaserQuarry) {
                TileLaserQuarry tile = (TileLaserQuarry) tmp;
                ItemAreaSetter item = (ItemAreaSetter) b.getItem();

                if (item.getFoundLasers().size() == 5) {
                    //TileEntity処理
                    tile.clearData();
                    item.getFoundLasers().forEach(tile::setLaser);
                    tile.setPosData(new PosData2Dim(item.getPosData()));
                    tile.initThread();
                    //TileEntityにデータを移したらitemのLaserListをクリア
                    item.clearFoundLaserList();
                    player.addChatComponentMessage(new ChatComponentText("Set Pos Data to LaserQuarry"));
                    return true;
                }
            }
        }

        player.openGui(LaserQuarry.instance, GuiRegistry.getGuiId(GuiHandler.LASERQUARRY_GUI), world, x, y, z);

        return true;
    }

    @Override
    public void getSubBlocks(Item item, CreativeTabs tabs, List list) {
        super.getSubBlocks(item, tabs, list);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, entity, stack);
        DirectionHandler.setMetadataByDir(world, x, y, z, entity);
        if (!world.isRemote) {
            TileMachineRegistry.addMachine((TileMachineBase) world.getTileEntity(x, y, z));
        }
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int var6) {
        TileEntity tmp = world.getTileEntity(x, y, z);
        if (tmp != null) {
            TileLaserQuarry tile = (TileLaserQuarry) tmp;
            tile.clearData();
            if (!world.isRemote) {
                TileMachineRegistry.delMachine((TileMachineBase) world.getTileEntity(x, y, z));
            }
        }

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
            return front;
        else
            return this.side;
    }

    @Override
    public IIcon getIcon(IBlockAccess world, int x, int y, int z, int side) {
        TileLaserQuarry tile = (TileLaserQuarry) world.getTileEntity(x, y, z);
        int meta = world.getBlockMetadata(x, y, z);
        if ((side == 3 && meta == 0) || meta == side) {
            if (tile.isActive) return active;
        }
        return getIcon(side, meta);
    }


    @Override
    public void registerRecipe() {
    }

}
