package com.byteowls.vaadin.selectize.config.annotation;

import static java.lang.annotation.ElementType.TYPE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface SelectizeOption {

    /**
     * Any fields listed here will not be printed in the generated {@code toString} implementation.
     * Mutually exclusive with {@link #of()}.
     */
    String[] exclude() default {};

    /**
     * If present, explicitly lists the fields that are to be printed.
     * Normally, all non-static fields are printed.
     * <p>
     * Mutually exclusive with {@link #exclude()}.
     */
    String[] of() default {};

    /**
     * The name of the property to use as the value when an item is selected.
     */
    String valueField() default "";

    /**
     * The name of the option group property that serves as its unique identifier.
     */
    String optgroupValueField() default "";

    /**
     * The name of the property to render as an option / item label (not needed when custom rendering functions are defined).
     */
    String labelField() default "";

    /**
     * The name of the property to render as an option group label (not needed when custom rendering functions are defined).
     */
    String optgroupLabelField() default "";

    /**
     * The name of the property to group items by.
     */
    String optgroupField() default "";

    /**
     * An array of property names to analyze when filtering options.
     */
    String[] searchField() default {};

}
