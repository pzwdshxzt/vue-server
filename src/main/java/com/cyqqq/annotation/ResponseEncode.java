package com.cyqqq.annotation;

import com.cyqqq.model.enums.SecurityMethod;

import java.lang.annotation.*;

/**
 * 加密响应数据
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResponseEncode {
    SecurityMethod method() default SecurityMethod.NULL;
}
