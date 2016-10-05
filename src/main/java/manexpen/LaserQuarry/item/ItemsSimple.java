package manexpen.LaserQuarry.item;

import buildcraft.BuildCraftSilicon;
import cpw.mods.fml.common.registry.GameRegistry;
import manexpen.LaserQuarry.LQItemBlockList;
import manexpen.LaserQuarry.api.IHasRecipe;
import moze_intel.projecte.gameObjs.ObjHandler;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by manex on 2016/10/05.
 */
public class ItemsSimple {
    public static class ItemHighLevelConductor extends ItemSimpleBase {
        public ItemHighLevelConductor() {
            setTextureName("laser:highlevelcon");
        }

        @Override
        public void registerRecipe() {
            GameRegistry.addRecipe(new ItemStack(LQItemBlockList.highLevelConductor),
                    "XXX",
                    "YYY",
                    "ZXZ",
                    'X', LQItemBlockList.reinforcedGlass,
                    'Y', LQItemBlockList.octaRedCrystal,
                    'Z', LQItemBlockList.lowLevelConductor);
        }
    }

    public static class ItemLowLevelConductor extends ItemSimpleBase {
        public ItemLowLevelConductor() {
            setTextureName("laser:lowlevelcon");
        }

        @Override
        public void registerRecipe() {
            GameRegistry.addRecipe(new ItemStack(LQItemBlockList.lowLevelConductor),
                    "X",
                    "Y",
                    "X",
                    'X', LQItemBlockList.reinforcedGlass,
                    'Y', Items.iron_ingot);
        }
    }

    public static class ItemCirculationLaserGen extends ItemSimpleBase {

        public ItemCirculationLaserGen() {
            setTextureName("laser:circLaserGen");
        }

        @Override
        public void registerRecipe() {
            GameRegistry.addRecipe(new ItemStack(LQItemBlockList.circLaserGen),
                    "XYX",
                    "YXY",
                    "ZZZ",
                    'X', LQItemBlockList.highLevelConductor,
                    'Y', LQItemBlockList.positionFixinger,
                    'Z', LQItemBlockList.chipSet);
        }
    }

    public static class ItemPositionFixinger extends ItemSimpleBase {
        public ItemPositionFixinger() {
            setTextureName("laser:posfix");
        }

        @Override
        public void registerRecipe() {
            GameRegistry.addRecipe(new ItemStack(LQItemBlockList.positionFixinger),
                    "XXX",
                    "YZY",
                    "AXA",
                    'X', LQItemBlockList.octaRedCrystal,
                    'Y', Blocks.glass_pane,
                    'Z', BuildCraftSilicon.laserBlock,
                    'A', new ItemStack(BuildCraftSilicon.redstoneChipset, 1, 4));
        }
    }

    public static class ItemOctaRedCrystal extends ItemSimpleBase {
        public ItemOctaRedCrystal() {
            setTextureName("laser:octaRedCrystal");
        }

        @Override
        public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
            super.onCreated(itemStack, world, player);
            itemStack.addEnchantment(Enchantment.silkTouch, 1);
        }

        @Override
        public void registerRecipe() {
            GameRegistry.addRecipe(new ItemStack(LQItemBlockList.octaRedCrystal),
                    "XZZ",
                    "XZZ",
                    "ABA",
                    'X', new ItemStack(BuildCraftSilicon.redstoneChipset, 1, 4),
                    'Z', BuildCraftSilicon.redstoneCrystal,
                    'A', ObjHandler.kleinStars,
                    'B', ObjHandler.tome);
        }
    }

    public static class ItemChipSet extends ItemSimpleBase {
        public ItemChipSet() {
            setTextureName("laser:chipset");
        }

        @Override
        public void registerRecipe() {
            GameRegistry.addRecipe(new ItemStack(LQItemBlockList.chipSet),
                    "XY",
                    "ZZ",
                    'X', new ItemStack(BuildCraftSilicon.redstoneChipset, 1, 4),
                    'Y', LQItemBlockList.reinforcedGlass,
                    'Z', LQItemBlockList.lowLevelConductor);
        }
    }

    public static abstract class ItemSimpleBase extends Item implements IHasRecipe {
    }
}
