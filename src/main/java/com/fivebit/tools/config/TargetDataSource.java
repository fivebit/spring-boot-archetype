package com.fivebit.tools.config;

import java.lang.annotation.*;

/**
 * Created by fivebit on 2017/12/19.
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDataSource {
    String value() default "dataSource";
}
