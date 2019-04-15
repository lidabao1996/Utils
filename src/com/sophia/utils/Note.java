package com.sophia.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
//注解会在class字节码文件中存在，在运行时可以通过反射获取到
//表示可以注解在类商,方法上,字段上
@Target({ElementType.TYPE,ElementType.FIELD,ElementType.METHOD})
public @interface Note {
	public String value();
}
