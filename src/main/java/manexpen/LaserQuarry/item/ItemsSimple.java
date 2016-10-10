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
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

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

    public static class ItemStrangeMeat extends ItemFood {

        private static final PotionEffect EFFECT1 = new PotionEffect(Potion.hunger.id, 600, 1);
        private static final PotionEffect EFFECT2 = new PotionEffect(Potion.confusion.id, 600, 0);

        public ItemStrangeMeat() {
            super(4, 0.1F, true);
            setTextureName("laser:meat");
        }

        @Override
        protected void onFoodEaten(ItemStack itemStack, World world, EntityPlayer player) {
            if (!world.isRemote) {
                player.addPotionEffect(EFFECT1);
                player.addPotionEffect(EFFECT2);
            }
        }
    }

    public static class canVillagerMeat extends ItemFood implements IHasRecipe {
        private static int newCount = 0;
        private canType type;
        private List<PotionEffect> effects = new ArrayList<>();

        public canVillagerMeat() {
            super(6, 0.1F, true);
            switch (newCount) {
                case 0:
                    setTextureName("laser:emptyCan");
                    break;
                case 1:
                    setTextureName("laser:meatCan");
                    effects.add(new PotionEffect(Potion.hunger.id, 1200, 2));
                    effects.add(new PotionEffect(Potion.confusion.id, 1200, 1));
                    break;
                case 2:
                    setTextureName("laser:emeraldCan");
                    effects.add(new PotionEffect(Potion.hunger.id, 1200, 2));
                    effects.add(new PotionEffect(Potion.confusion.id, 1200, 1));
                    effects.add(new PotionEffect(Potion.poison.id, 1200, 1));
                    break;
                case 3:
                    setTextureName("laser:superEmeraldCan");
                    setAlwaysEdible();
                    break;
            }
            type = canType.values()[newCount];
            newCount++;
        }


        @Override
        public ItemStack onEaten(ItemStack itemStack, World world, EntityPlayer player) {
            super.onEaten(itemStack, world, player);

            if (type == canType.SUPER) world.createExplosion(null, player.posX, player.posY, player.posZ, 10.0F, true);
            effects.forEach(player::addPotionEffect);

            return type == canType.EMERALD || type == canType.INMEAT ? new ItemStack(LQItemBlockList.emptyCan) : itemStack;
        }

        @Override
        public void registerRecipe() {

        }

        private enum canType {
            EMPTY, INMEAT, EMERALD, SUPER
        }
    }

    public static abstract class ItemSimpleBase extends Item implements IHasRecipe {
    }
}
