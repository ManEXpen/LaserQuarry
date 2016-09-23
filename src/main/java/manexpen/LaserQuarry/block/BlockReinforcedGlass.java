package manexpen.LaserQuarry.block;

import manexpen.LaserQuarry.api.IHasRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Created by ManEXpen on 2016/07/23.
 */
public class BlockReinforcedGlass extends Block implements IHasRecipe {
    public BlockReinforcedGlass() {
        super(Material.glass);
        setStepSound(soundTypeGlass);
        setResistance(1000.0f);
        setBlockTextureName("laser:GlassResonant");
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public void registerRecipe() {

    }
}
