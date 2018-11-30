package com.cyqqq.annotation;

import com.cyqqq.model.enums.SecurityMethod;

import java.lang.annotation.*;

/**
 * 解密请求数据
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestDecode {
    SecurityMethod method() default SecurityMethod.NULL;
}
