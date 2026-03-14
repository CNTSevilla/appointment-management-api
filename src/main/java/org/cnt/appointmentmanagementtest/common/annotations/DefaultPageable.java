package org.cnt.appointmentmanagementtest.common.annotations;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DefaultPageable {
    int page() default 0;
    int size() default 20;
    String sort() default "";
    String direction() default "asc";
}