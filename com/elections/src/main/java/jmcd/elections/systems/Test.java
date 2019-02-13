package jmcd.elections.systems;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark all methods to be used in the unit testing
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Test {

    enum Type{
        DIVISOR,QUOTA,REMAINDER,STV
    }
    boolean toBeTested() default true;

    Type type();

}
