package mi.practice.java.base.access.ex;

import mi.practice.java.base.access.Ex5;

/**
 * 练习5, 尝试在不同的包中尝试不同访问修饰符的作用
 * private, default, protected都不能访问
 * 只有public 能访问
 */
public class Ex5Main {

	public static void main(String[] args) {
		Ex5 ex = new Ex5();
//		System.out.println(ex.a);
//		System.out.println(ex.b);
//		System.out.println(ex.c);
		System.out.println(ex.d);
//		ex.privateSay();
//		ex.packageSay();
//		ex.protectedSay();
		ex.publicSay();
	}

}
