package mi.practice.java.base.access;

/**
 * 在继承类中是可以访问protected
 * 修饰符指定的成员跟方法的
 */

public class Ex6 {
	protected int protectedInt = 0;

	public static void main(String[] args) {
		Ex6Ext ext = new Ex6Ext();
		for (int i = 0; i < 5; i++) {
			ext.plus();
		}
	}
}

class Ex6Ext extends Ex6 {
	public void plus() {
		protectedInt++;
		System.out.println(protectedInt);
	}

}
