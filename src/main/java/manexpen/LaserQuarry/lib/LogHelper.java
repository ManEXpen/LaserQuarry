package manexpen.LaserQuarry.lib;

/**
 * Created by ManEXpen on 2016/07/15.
 */
public class LogHelper {
    public static org.apache.logging.log4j.Logger Logger;

    public static void info(Object s) {
        Logger.info(s.toString());
    }

    public static void warn(Object s) {
        Logger.warn(s.toString());
    }

    public static void debug(Object s) {
        Logger.debug(s.toString());
    }
}
