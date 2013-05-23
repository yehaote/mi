package mi.practice.java.base.annotations.database;
/**
 * 定义一个唯一条件的标注
 */
public @interface Uniqueness {
	Constraints constraints() default @Constraints(unique = true);
}
