package com.task.inventory_api.aspect;

import java.lang.annotation.*;

public class InventoryAnnotation {

    @Documented
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    public @interface MethodRunTimeCheck  {
        String name() default "";
    }


}
