package mi.practice.java.base.operators;

/**
 * 进行逻辑运算的时候, 会有短路的现象
 * 就是说如果exp1 && exp2, 如果exp1的结果是false的话,就不会再去计算exp2
 * || 同理, 如果前者是true的话
 */
public class ShortCircuit {
	static boolean test1(int val1) {
		System.out.println("test1(" + val1 + ")");
		System.out.println("result: " + (val1 < 1));
		return val1 < 1;
	}

	static boolean test2(int val2) {
		System.out.println("test2(" + val2 + ")");
		System.out.println("result: " + (val2 < 2));
		return val2 < 2;
	}

	static boolean test3(int val3) {
		System.out.println("test1(" + val3 + ")");
		System.out.println("result: " + (val3 < 3));
		return val3 < 3;
	}

	public static void main(String[] args) {
		boolean b = test1(0) && test2(2) && test3(2);
		System.out.println("expression is " + b);
	}

}
