package com.kalllx.ardb.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Belong
{
    boolean M2M() default false; // many to many flag
    String interTable() default ""; // intermedia table
    String foreignKey() default ""; 
    String foreignKey2() default "";
}
