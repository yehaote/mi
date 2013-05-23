package mi.practice.java.base.operators;

/**
 * 字符串操作符
 * 当表达式里包含字符串的时候, 所有的其他类型会转化成String
 */
public class StringOperators {
	public static void main(String[] args) {
		int x = 0, y = 1, z = 2;
		String s = "x, y, z ";
		System.out.println(s + x + y + z);
		System.out.println(x + " " + s);
		s += "(summed) = ";
		System.out.println(s + (x + y + z));
		System.out.println(" " + x);
	}
}
