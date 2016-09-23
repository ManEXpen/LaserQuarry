package manexpen.LaserQuarry.lib;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

/**
 * Created by ManEXpen on 2016/08/19.
 */
public class RenderUtil {
    public static FloatBuffer toFloatBuffer(float[] values, int elements) {
        FloatBuffer floats = BufferUtils.createFloatBuffer(elements).put(values);
        floats.flip();
        return floats;
    }
}
