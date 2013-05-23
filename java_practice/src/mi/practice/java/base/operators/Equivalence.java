package mi.practice.java.base.operators;

/**
 * 测试对象的相等
 * new 出来的对象是两个不用的引用
 */
public class Equivalence {
	public static void main(String[] args) {
		Integer n1 = new Integer(47);
		Integer n2 = new Integer(47);
		System.out.println(n1 == n2);
		System.out.println(n1 != n2);
	}
}
