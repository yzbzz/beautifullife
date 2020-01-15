package com.iannotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by yzbzz on 2018/4/12.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface ICodeLabsElement {

    String path() default "";

    String parentId() default "";

    String parentContent() default "";

    String id() default "";

    String content() default "";
}
