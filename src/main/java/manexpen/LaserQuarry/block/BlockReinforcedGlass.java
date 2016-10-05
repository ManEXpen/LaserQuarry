package manexpen.LaserQuarry.block;

import cpw.mods.fml.common.registry.GameRegistry;
import manexpen.LaserQuarry.LQItemBlockList;
import manexpen.LaserQuarry.api.IHasRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

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
        GameRegistry.addRecipe(new ItemStack(LQItemBlockList.reinforcedGlass),
                "XYX",
                "YXY",
                "XZX",
                'X', Blocks.glass,
                'Y', Blocks.obsidian,
                'Z', Items.emerald);
    }
}
