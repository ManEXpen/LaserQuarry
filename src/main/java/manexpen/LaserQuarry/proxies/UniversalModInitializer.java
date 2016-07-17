package manexpen.LaserQuarry.proxies;

import cpw.mods.fml.common.registry.GameRegistry;
import manexpen.LaserQuarry.LQItemBlockList;
import manexpen.LaserQuarry.LaserQuarry;
import manexpen.LaserQuarry.api.IHasRecipe;
import manexpen.LaserQuarry.lib.LogHelper;
import manexpen.LaserQuarry.tileentity.TileLaserQuarry;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ManEXpen on 2016/07/15.
 */
public class UniversalModInitializer {

    private static List<Field> fields = Arrays.asList(LQItemBlockList.class.getDeclaredFields());
    private static List<Field> tiles = Arrays.asList(TileList.class.getDeclaredFields());

    private static void create() {


        fields.stream().forEach(f -> {
            try {
                Class c = f.getType();
                Constructor cons = c.getConstructor();
                f.set(null, cons.newInstance());
                Object obj = f.get(null);
                if (obj instanceof Block) {
                    Block block = (Block) obj;
                    f.set(null, block.setBlockName(f.getName()));
                } else {
                    Item item = (Item) obj;
                    f.set(null, item.setUnlocalizedName(f.getName()));
                }
            } catch (Exception e) {
                LogHelper.warn("Error Occurred During initializing Item/Block");
                e.printStackTrace();
            }
        });
    }

    private static void gameRegister() {
        fields.stream().forEach(f -> {
            try {
                Object obj = f.get(null);
                if (obj instanceof Item) {
                    ((Item) obj).setUnlocalizedName(f.getName()).setCreativeTab(LaserQuarry.LQCreativeTab);
                    GameRegistry.registerItem((Item) obj, f.getName());
                } else {
                    ((Block) obj).setBlockName(f.getName()).setCreativeTab(LaserQuarry.LQCreativeTab);
                    GameRegistry.registerBlock((Block) obj, f.getName());
                }
                LogHelper.info("Register Block/Item :[" + f.getName() + "]");
            } catch (IllegalAccessException e) {
                LogHelper.warn("Error Occurred During gameRegistry");
                e.printStackTrace();
            }
        });
    }

    private static void addRecipe() {
        fields.stream().forEach(f -> {
            try {
                Object obj = f.get(null);
                if (obj instanceof IHasRecipe) ((IHasRecipe) obj).registerRecipe();
                LogHelper.info("Register Recipe[" + f.getName() + "]");
            } catch (IllegalAccessException e) {
                LogHelper.warn("Error Occurred During addRecipe");
                e.printStackTrace();
            }
        });
    }

    private static void registerTileEntity() {
        tiles.stream().forEach(f -> {
            try {
                GameRegistry.registerTileEntity((Class) f.get(null), f.getName());
                LogHelper.info("register TileEntity[" + f.getName() + "]");
            } catch (IllegalAccessException e) {
                LogHelper.warn("Error Occurred During register TileEntity");
                e.printStackTrace();
            }
        });
    }

    public static void register() {
        create();
        gameRegister();
        registerTileEntity();
        addRecipe();
    }

    private static class TileList {
        public static Class quarryClass = TileLaserQuarry.class;
    }
}
