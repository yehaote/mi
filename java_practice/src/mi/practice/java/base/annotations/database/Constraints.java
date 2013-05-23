package mi.practice.java.base.annotations.database;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
// 类的成员
@Retention(RetentionPolicy.RUNTIME)
public @interface Constraints {
	boolean primayKey() default false;

	boolean allowNull() default true;

	boolean unique() default false;
}
