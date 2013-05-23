package mi.practice.java.base.reusing;
/**
 * 练习9
 * 类在调用构造函数之前, 会先声明变量
 * 子类在调用自己的构造函数之前会先调用父类的构造函数
 * 子类的成员跟父类的成员是没有重载的,
 * 重载只发生在方法之间
 */
class Component1{
	Component1(){
		System.out.println("Component1()");
	}
	
}

class Component2{
	Component2(){
		System.out.println("Component2()");
	}
}

class Component3{
	Component3(){
		System.out.println("Component3()");
	}
}

class Root{
	Root(){
		System.out.println("Root()");
	}
	Component1 c1 = new Component1();
	Component2 c2 = new Component2();
	Component3 c3 = new Component3();
}

class Stem extends Root{
	Stem(){
		System.out.println("Stem()");
	}
	Component1 c1 = new Component1();
	Component2 c2 = new Component2();
	Component3 c3 = new Component3();
}

public class Ex9 {
	@SuppressWarnings("unused")
	public static void main(String[] args){
		System.out.println("new root");
		Root root = new Root();
		System.out.println("new stem");
		Stem stem = new Stem();
	}
}
