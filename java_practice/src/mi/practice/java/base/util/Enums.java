package mi.practice.java.base.util;

import java.util.Random;
/**
 * 枚举类型工具类,
 * 根据枚举类型的Class随机产生一个枚举类型的值.	
 */
public class Enums {
	private static Random rand = new Random(47);

	public static <T extends Enum<T>> T random(Class<T> ec) {
		return random(ec.getEnumConstants());
	}

	public static <T> T random(T[] values) {
		return values[rand.nextInt(values.length)];
	}
}
