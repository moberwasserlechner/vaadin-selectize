package com.byteowls.vaadin.selectize.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SelectizeOptionSort {

    /**
     */
    boolean asc() default true;
    
    int order() default 1;

}
