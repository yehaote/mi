package mi.practice.java.base.operators;

/**
 * 介绍指数
 * e代表10的幂
 * e大小写代表一样的意思
 * 默认浮点数是double类型
 */
public class Exponents {

	public static void main(String[] args) {
		float expFloat = 1.39e-43f;
		expFloat=1.39E-43f;
		System.out.println(expFloat);
		double expDouble = 47e47d;
		double expDouble2 = 47e47;
		System.out.println(expDouble);
		System.out.println(expDouble2);
	}

}
