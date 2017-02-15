package com.hand.annotation;

import java.lang.annotation.*;

/**
 * Created by Hand on 2017/2/14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface RedisMap {
}
