package com.scc.log.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @since 2020年11月4日 上午11:02:29
 * @author sxx
 * @description 日志记录标签
 * 可以使用在方法或者类上，可以根据需要决定谁的优先级更高
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface LogRecord {

    String system() default "";

    String module() default "";

    String menuLv1() default "";

    String menuLv2() default "";

}
