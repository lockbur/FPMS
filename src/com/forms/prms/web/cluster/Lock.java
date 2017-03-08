package com.forms.prms.web.cluster;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
/**
 * 所有字段支持SpEL表达式
 * @author forms
 *
 */
public @interface Lock {

	String taskType() default "";

	String taskSubType() default "";

	String instOper() default "'SYSTEM'";

	String memo() default "";

}
