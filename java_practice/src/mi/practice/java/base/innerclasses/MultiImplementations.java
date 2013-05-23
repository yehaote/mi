package mi.practice.java.base.innerclasses;
/**
 * 当你使用内部类的时候可以获得这么几个特性,
 * 1. 内部类可以有多个实例, 它们各自有自己的信息
 * 2. 一个类可以有多个内部类, 它们各自实现不同的接口, 或者是一个接口的(类)的不同实现 
 * 3. 内部类的创建不跟外部类捆绑在一起
 * 4. 使用内部类, 可以不去混淆"is-a"的对象逻辑结构
 */
class D{}
abstract class E{}
class Z extends D{
	E makeE(){
		return new E() {//匿名类
		};
	}
}
public class MultiImplementations {
	static void takesD(D d){}
	static void takesE(E e){}
	public static void main(String[] args){
		Z z = new Z();
		takesD(z);
		takesE(z.makeE());
	}
}
