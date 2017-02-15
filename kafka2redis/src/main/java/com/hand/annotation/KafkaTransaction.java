package com.hand.annotation;

import java.lang.annotation.*;

/**
 * Created by Hand on 2017/2/14.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface KafkaTransaction {
}
