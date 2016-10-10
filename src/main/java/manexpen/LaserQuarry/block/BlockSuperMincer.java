package manexpen.LaserQuarry.block;

import manexpen.LaserQuarry.tileentity.TileSuperMincer;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by manex on 2016/10/06.
 */
public class BlockSuperMincer extends BlockContainer {
    private IIcon top, side, bottom;

    public BlockSuperMincer() {
        super(Material.iron);
        setTickRandomly(true);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        if (side == 1) return top;
        else if (side == 0) return bottom;
        else return this.side;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rnd) {
        TileSuperMincer tile = (TileSuperMincer) world.getTileEntity(x, y, z);
        if (tile == null) return;
        if (!tile.isMachineTop()) return;


        if (tile.getVillager() != null && tile.runTime > 5 && rnd.nextInt(2) == 0) {
            double x1 = x + rnd.nextDouble();
            double y1 = y - 1.0D;
            double z1 = z + rnd.nextDouble();
            world.spawnParticle("lava", x1, y1, z1, 0.0D, 0.0D, 0.0D);
        }

        double x2 = x + rnd.nextDouble();
        double y2 = y - 1.0D;
        double z2 = z + rnd.nextDouble();
        world.spawnParticle("dripLava", x2, y2, z2, 0.0D, 0.0D, 0.0D);
    }

    @Override
    public int tickRate(World world) {
        return 30;
    }

    @Override
    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
        super.onEntityCollidedWithBlock(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, p_149670_5_);
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        top = register.registerIcon("laser:mincer_top");
        side = register.registerIcon("laser:mincer");
        bottom = register.registerIcon("laser:mincer_bottom");
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileSuperMincer();
    }
}
