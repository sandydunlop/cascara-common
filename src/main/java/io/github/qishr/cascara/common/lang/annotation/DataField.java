package io.github.qishr.cascara.common.lang.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;

// /**
//  * Represents a custom annotation for marking methods.
//  *
//  * @annotation JsonElement
//  * @param key The key of the annotation.
//  */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DataField {
    public String key() default "";
}