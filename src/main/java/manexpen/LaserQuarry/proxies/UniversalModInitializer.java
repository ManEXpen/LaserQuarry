package manexpen.LaserQuarry.proxies;

import cpw.mods.fml.common.registry.GameRegistry;
import manexpen.LaserQuarry.LQItemBlockList;
import manexpen.LaserQuarry.LaserQuarry;
import manexpen.LaserQuarry.api.IHasRecipe;
import manexpen.LaserQuarry.lib.LogHelper;
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

    private static void gameRegister(Field f) {
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
    }

    private static void addRecipe(Field f) {
        try {
            Object obj = f.get(null);
            if (obj instanceof IHasRecipe) ((IHasRecipe) obj).registerRecipe();
            LogHelper.info("Register Recipe[" + f.getName() + "]");
        } catch (IllegalAccessException e) {
            LogHelper.warn("Error Occurred During addRecipe");
            e.printStackTrace();
        }
    }

    public static void register() {
        fields.forEach(f -> {
            try {
                Class c = f.getType();
                Object obj = f.get(null);

                if (obj instanceof Class) {
                    GameRegistry.registerTileEntity((Class) obj, f.getName());
                    obj = null;
                    return;
                }

                Constructor cons = c.getConstructor();
                f.set(null, cons.newInstance());
                gameRegister(f);
                addRecipe(f);

            } catch (ReflectiveOperationException e) {
                LogHelper.warn("Error Occurred During initializing Item/Block");
                e.printStackTrace();
            }
        });
    }
}
