package manexpen.LaserQuarry.lib;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by ManEXpen on 2016/07/23.
 */
public class GuiRegistry {
    private static int GUI_ID = 0;
    private static Map<String, Integer> guiList = Collections.synchronizedMap(new HashMap<String, Integer>());

    public static void registerId(String guiName, int guiId) {
        guiList.put(guiName, guiId);
    }

    public static int getGuiId(String guiName) {
        checkNotNull(guiName, "guiName must not be null");
        Integer guiId = checkNotNull(guiList.get(guiName), "Not found guiId in guiList.Please make sure registered.");
        return guiId;
    }

    public static int getNextGuiId() {
        return GUI_ID++;
    }
}
