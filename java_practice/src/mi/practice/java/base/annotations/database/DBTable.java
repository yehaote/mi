package mi.practice.java.base.annotations.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
// Class, Interface, annotation
// 当想指定多个ElementType的时候可以用,隔开, 
// 外部用大括号包装起来
@Retention(RetentionPolicy.RUNTIME)
public @interface DBTable {
	public String name() default "";
}
