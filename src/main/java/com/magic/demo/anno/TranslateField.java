package com.magic.demo.anno;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 仅对 String 类型的字段有效
 * @author zheng
 * @since 2023/4/18 5:49 PM
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TranslateField {
    String lookupFieldPath();

}
