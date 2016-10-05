package manexpen.LaserQuarry.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by manex on 2016/10/05.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ToClearify {
    public ClassKinds value();

    /*You have to set Parametor if you want to register*/
    public Class needToReg() default Object.class;
}
