package manexpen.LaserQuarry.proxies;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.google.common.reflect.ClassPath;
import cpw.mods.fml.common.registry.GameRegistry;
import manexpen.LaserQuarry.LQItemBlockList;
import manexpen.LaserQuarry.LaserQuarry;
import manexpen.LaserQuarry.api.IHasRecipe;
import manexpen.LaserQuarry.lib.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.stream.Collectors;

/**
 * Created by ManEXpen on 2016/07/15.
 */
public class UniversalModInitializer {

    private static List<Field> fields = Arrays.asList(LQItemBlockList.class.getDeclaredFields());

    private static void create() {


        fields.stream().forEach(f -> {
            try {
                Class c = f.getType();
                Constructor cons = c.getConstructor();
                f.set(null, cons.newInstance());
                Object obj = f.get(null);
                if (obj instanceof Block) {
                    Block block = (Block) obj;
                    block.setBlockName(f.getName());
                } else {
                    Item item = (Item) obj;
                    item.setUnlocalizedName(f.getName());
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
                LogHelper.info("Register:[" + f.getName() + "]Block/Item");
                Object obj = f.get(null);
                if (obj instanceof Item) {
                    GameRegistry.registerItem((Item) obj, f.getName());
                    ((Item) obj).setCreativeTab(LaserQuarry.creativeTab);
                } else {
                    GameRegistry.registerBlock((Block) obj, f.getName());
                    ((Block) obj).setCreativeTab(LaserQuarry.creativeTab);
                }
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
                LogHelper.info("Register Recipe["+f.getName()+"]");
                if (obj instanceof IHasRecipe) ((IHasRecipe) obj).registerRecipe();
            } catch (IllegalAccessException e) {
                LogHelper.warn("Error Occurred During addRecipe");
                e.printStackTrace();
            }
        });
    }

    public static void register() {
        create();
        gameRegister();
        addRecipe();
    }

}
