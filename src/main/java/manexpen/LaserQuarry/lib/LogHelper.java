package manexpen.LaserQuarry.lib;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.logging.log4j.Logger;

/**
 * Created by ManEXpen on 2016/07/15.
 */
public class LogHelper {
    public static org.apache.logging.log4j.Logger Logger;

    public static void info(String s) {
        Logger.info(s);
    }

    public static void warn(String s) {
        Logger.warn(s);
    }
}
