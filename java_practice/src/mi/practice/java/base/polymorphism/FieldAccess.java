package mi.practice.java.base.polymorphism;
/**
 * 只有方法是可以多态的
 * 成员不是多态的
 * 当子类转换成父类进行调用的时候, 
 * 所有的成员调用都是针对父类的成员
 * 最好把所有的成员都设置成private
 * 父类的成员跟子类的成员最好也不要同名, 以免混淆
 */
class Super {
	public int field = 0;

	public int getField() {
		return field;
	}
}

class Sub extends Super {
	public int field = 1;

	@Override
	public int getField() {
		return field;
	}

	public int getSuperField() {
		return super.field;
	}
}

public class FieldAccess {
	public static void main(String[] args) {
		Super sup = new Sub();
		System.out.println("sup.field = " + sup.field + ", sup.getField() = "
				+ sup.getField());
		Sub sub = new Sub();
		System.out.println("sub.field = " + sub.field + ", sub.getField() = "
				+ sub.getField() + ", sub.getSuperField() = "
				+ sub.getSuperField());
	}
}
