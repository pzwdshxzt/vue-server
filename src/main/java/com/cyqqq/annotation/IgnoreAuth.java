package com.cyqqq.annotation;

import java.lang.annotation.*;

/**
 * 忽略Token验证
 *
 * @author huangjinxing
 * @email 83265414@qq.com
 * @date 2018-12-17 18:24
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface IgnoreAuth {
}
