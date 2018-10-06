/**
 * @author kexiaohong
 * @version 1.0 2018年1月26日
 *
 */
package com.marry.inner.base.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Target(FIELD)
@Retention(RUNTIME)
public @interface EnableExample {
	String value() default "";
}
