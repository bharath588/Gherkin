package mobile;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Siddartha on 2/Jan/2017.
 */

@Target(ElementType.TYPE) @Retention(RetentionPolicy.RUNTIME) public @interface Description {

    String value() default "";
}
