package manexpen.LaserQuarry.api;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import manexpen.LaserQuarry.LaserQuarry;
import manexpen.LaserQuarry.lib.LogHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.item.Item;

import java.lang.reflect.Field;

/**
 * Created by manex on 2016/10/05.
 */
@SuppressWarnings("unchecked")
public class UniversalModInitializer {
    public static void regist(Class<?> registedClass, Object modInstance) {
        try {
            for (Field field : registedClass.getDeclaredFields()) {
                if (field.get(null) instanceof Class) classDistributor(field, modInstance);
                else {
                    field.set(null, field.getType().getDeclaredConstructor().newInstance());
                    gameRegister(field);
                }
            }
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    private static void classDistributor(Field field, Object modInstance) throws ReflectiveOperationException {
        ToClearify anotation = field.getAnnotation(ToClearify.class);
        if (anotation == null) throw new ReflectiveOperationException("Please Check Your Class!" + field.getName());
        switch (anotation.value()) {
            case ENTITY:
                EntityRegistry.registerModEntity((Class) field.get(null), field.getName(), EntityRegistry.findGlobalUniqueEntityId(), LaserQuarry.instance, 256, 5, true);
                break;
            case ENTITYRENDER:
                RenderingRegistry.registerEntityRenderingHandler(anotation.needToReg(), (Render) ((Class) field.get(null)).newInstance());
                break;
            case TILEENTITY:
                GameRegistry.registerTileEntity((Class) field.get(null), field.getName());
                break;
        }
    }

    private static void gameRegister(Field field) throws ReflectiveOperationException {

        Object raw = field.get(null);
        if (raw instanceof Item)
            GameRegistry.registerItem(((Item) raw).setUnlocalizedName(field.getName()).setCreativeTab(LaserQuarry.LQCreativeTab), field.getName());
        else
            GameRegistry.registerBlock(((Block) raw).setBlockName(field.getName()).setCreativeTab(LaserQuarry.LQCreativeTab), field.getName());

        if (raw instanceof IHasRecipe) ((IHasRecipe) raw).registerRecipe();

        LogHelper.info(String.format("Registered Block/Item %s!", field.getName()));
    }
}
