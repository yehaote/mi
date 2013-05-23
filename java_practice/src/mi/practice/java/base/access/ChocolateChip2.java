package mi.practice.java.base.access;

import mi.practice.java.base.access.dessert.Cookie2;

public class ChocolateChip2 extends Cookie2 {
	public ChocolateChip2() {
		System.out.println("ChocolateChip2 constructor");
	}

	public void Chomp() {
		bite();// protected 方法
	}

	public static void main(String[] args) {
		ChocolateChip2 x = new ChocolateChip2();
		x.Chomp();
		@SuppressWarnings("unused")
		Cookie2 cookie2 = new Cookie2();
		// cookie2.bite(); //protected方法, 除了继承只有包访问权限, 而不是public
	}
}
