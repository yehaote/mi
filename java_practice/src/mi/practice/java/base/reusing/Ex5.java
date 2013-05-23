package mi.practice.java.base.reusing;
/**
 * p245 练习5
 * 父类的构造器调用在子类成员声明初始化之前
 */
class A{
	A(){
		System.out.println("A()");
	}
}

class B{
	B(){
		System.out.println("B()");
	}
}

class C extends A{
	B b= new B();
}

public class Ex5 {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		C c = new C();
	}
}
