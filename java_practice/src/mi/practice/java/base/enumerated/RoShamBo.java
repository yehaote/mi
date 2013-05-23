package mi.practice.java.base.enumerated;

import mi.practice.java.base.util.Enums;

public class RoShamBo {
	/**
	 * 对两个Competitor进行比较
	 * @param a
	 * @param b
	 */
	public static <T extends Competitor<T>> void match(T a, T b) {
		System.out.println(a + " vs. " + b + ": " + a.compete(b));
	}
	
	/**
	 * 随机生产多个继承Competitor的枚举类,
	 * 并进行比较
	 * @param rsbClass
	 * @param size
	 */
	public static <T extends Enum<T> & Competitor<T>> void play(
			Class<T> rsbClass, int size) {
		for (int i = 0; i < size; i++) {
			match(Enums.random(rsbClass), Enums.random(rsbClass));
		}
	}
}
