package manexpen.LaserQuarry.lib;

import net.minecraft.util.IIcon;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by ManEXpen on 2016/07/17.
 */
public class IconRegistry {
    public static Map<String, IIcon> icons = new ConcurrentHashMap<>();

    private IconRegistry() {
    }

    public static void putIcon(String type, IIcon icon) {
        icons.put(type, icon);
    }

    public static IIcon getIcon(String type) {
        return icons.get(type);
    }

}
