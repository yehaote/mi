package mi.practice.java.base.polymorphism.ex;

/**
 * 如果在父类中存在方法间的调用
 * 在子类中覆盖了方法, 那么父类的相关调用也会指向这个覆盖的方法
 */
class Ex10_Demo {
	public String what() {
		return "Demo";
	}

	@Override
	public String toString() {
		return what();
	}
}

class Ex10_Demo1 extends Ex10_Demo {
	@Override
	public String what() {
		return "Demo1";
	}
}

public class Ex10 {
	public static void main(String[] args) {
		Ex10_Demo[] demos = { new Ex10_Demo(), new Ex10_Demo1() };
		for (Ex10_Demo demo : demos) {
			System.out.println(demo);
		}
	}
}
